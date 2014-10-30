tree grammar TypeExpand;

options {
  language = Java;
  output = AST;
  tokenVocab = Syntax;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
}
 
@header {
  package dash; 
  import SymTab.*;
}

@members {
    SymbolTable symtab;
    Scope currentscope;
    public TypeExpand(TreeNodeStream input, SymbolTable symtab) {
        this(input);
        this.symtab = symtab;
        currentscope = symtab.globals;
    }
    private String getErrorHeader() {
      int line = input.getTokenStream().get(input.index()).getLine(); 
      int chline = input.getTokenStream().get(input.index()).getCharPositionInLine();
      return getGrammarFileName() + ">" + line + ":" + chline + ": ";
  }
}

program
  : ^(PROGRAM globalStatement*)
  ;
  
globalStatement
  : declaration
  | typedef
  | procedure
  | function
  ;
   
statement
  : assignment 
  | outputstream
  | inputstream
  | ifstatement
  | loopstatement
  | block
  | callStatement
  | returnStatement
  | Break
  | Continue
  ;
  
outputstream
  : ^(RArrow expr)
  ;

inputstream
  : ^(LArrow Identifier)
  ;

declaration
@init {
  ArrayList<Type> specs = new ArrayList<Type>();
  ArrayList<Type> types = new ArrayList<Type>(); 
}
@after {
  VariableSymbol vs = new VariableSymbol($id.text, types, specs);
  currentscope.define(vs);
}
  : ^(DECL (s=specifier {specs.add($s.tsym);})* (t=type {types.add($t.tsym);})* id=Identifier)
  | ^(DECL (s=specifier {specs.add($s.tsym);})* (t=type {types.add($t.tsym);})* ^(Assign id=Identifier expr))
  ;
  
typedef
@after {
  String bitname = $t.tsym.getName();
  String oldname;
  do {
    oldname = bitname;
    bitname = symtab.resolveTDType(bitname).getSourceSymbol().getName();
  } while (!bitname.equals("null"));
  BuiltInTypeSymbol bits = (BuiltInTypeSymbol) symtab.resolveType(oldname);
  if (bits == null)
    throw new RuntimeException(getErrorHeader() + "type " + bits.getName() + " doesn't exist");
  TypeDefSymbol tds = new TypeDefSymbol(bits, $id.text);
  symtab.defineType(tds);
}
  : ^(Typedef t=type id=Identifier)
  ;

block
@init {currentscope = new NestedScope("blockscope", currentscope);}
@after {currentscope = currentscope.getEnclosingScope();}
  : ^(BLOCK declaration* statement*)
  ;
  
procedure
@init {
  ArrayList<Symbol> params = new ArrayList<Symbol>();
  ArrayList<Type> type = new ArrayList<Type>();
}
@after {
  ProcedureSymbol ps = new ProcedureSymbol($id.text, type, params);
  symtab.defineProcedure(ps);
  currentscope = currentscope.getEnclosingScope();
}
  : ^(Procedure id=Identifier paramlist ^(Returns type) block) {type.add($type.tsym);}
  | ^(Procedure id=Identifier paramlist block)
  ;
   
function
@init {
  ArrayList<Symbol> params = new ArrayList<Symbol>();
  ArrayList<Type> type = new ArrayList<Type>();
}
@after {
  FunctionSymbol fs = new FunctionSymbol($id.text, type, params);
  symtab.defineFunction(fs);
  currentscope = currentscope.getEnclosingScope();
}
  : ^(Function id=Identifier paramlist ^(Returns type) block) {type.add($type.tsym);}
  | ^(Function id=Identifier paramlist ^(Returns type) ^(Assign expr)) {type.add($type.tsym);}
  ;
  
paramlist
@init {currentscope = new NestedScope("paramscope", currentscope);}
  : ^(PARAMLIST parameter*)
  ;
  
parameter
@init {ArrayList<Type> type = new ArrayList<Type>();}
@after {
  type.add($t.tsym);
  VariableSymbol vs = new VariableSymbol($id.text, type);
  currentscope.define(vs);
}
  : ^(id=Identifier t=type)
  ;
  
callStatement
  : ^(CALL Identifier ^(ARGLIST expr*))
  ;
  
returnStatement
  : ^(Return expr?)
  ;
  
assignment
  : ^(Assign Identifier expr)
  ;
  
ifstatement
  : ^(If expr slist ^(Else slist))
  | ^(If expr slist)
  ;
  
loopstatement
  : ^(Loop ^(While expr) slist)
  | ^(Loop slist ^(While expr))
  | ^(Loop slist)
  ;
  
slist
  : block
  | statement
  ;
  
type returns [Type tsym]
  : t=Boolean {$tsym = (Type) symtab.resolveType($t.text);}
  | t=Integer {$tsym = (Type) symtab.resolveType($t.text);}
  | t=Matrix {$tsym = (Type) symtab.resolveType($t.text);}
  | t=Interval {$tsym = (Type) symtab.resolveType($t.text);}
  | t=String {$tsym = (Type) symtab.resolveType($t.text);}
  | t=Vector {$tsym = (Type) symtab.resolveType($t.text);}
  | t=Real {$tsym = (Type) symtab.resolveType($t.text);}
  | t=Character {$tsym = (Type) symtab.resolveType($t.text);}
  | tuple {$tsym = $tuple.ts;}
  | t=Identifier {$tsym = (Type) symtab.resolveType($t.text);}
  ;
  
tuple returns [TupleSymbol ts]
@init {ArrayList<FieldPair> fieldnames = new ArrayList<FieldPair>();}
@after {$ts = new TupleSymbol("tuple", fieldnames);}
  : ^(Tuple (t=type {fieldnames.add(new FieldPair("null", $t.tsym));}
    (id=Identifier {fieldnames.get(fieldnames.size()-1).name = $id.text;} )?)+)
  ;

specifier returns [Type tsym]
  : t=Const {$tsym = (Type) symtab.resolveSpec($t.text);}
  | t=Var {$tsym = (Type) symtab.resolveSpec($t.text);}
  ;
  
expr returns [String stype]
@init {Integer index = -1; TupleSymbol ts = null;}
  : ^(Plus a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException("type promotion error");
    
  } -> ^(Plus Identifier[$stype] expr expr)
  | ^(Minus a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException("type promotion error");
    
  } -> ^(Minus Identifier[$stype] expr expr)
  | ^(Multiply a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException("type promotion error");
    
  } -> ^(Multiply Identifier[$stype] expr expr)
  | ^(Divide a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException("type promotion error");
    
  } -> ^(Divide Identifier[$stype] expr expr)
  | ^(Exponent a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException("type promotion error");
    
  } -> ^(Exponent Identifier[$stype] expr expr)
  | ^(Equals a=expr b=expr) {$stype = $a.stype;} -> ^(Equals Identifier[$stype] expr expr)
  | ^(NEquals a=expr b=expr) {$stype = $a.stype;} -> ^(NEquals Identifier[$stype] expr expr)
  | ^(GThan a=expr b=expr) {$stype = $a.stype;} -> ^(GThan Identifier[$stype] expr expr)
  | ^(LThan a=expr b=expr) {$stype = $a.stype;} -> ^(LThan Identifier[$stype] expr expr)
  | ^(GThanE a=expr b=expr) {$stype = $a.stype;} -> ^(GThanE Identifier[$stype] expr expr)
  | ^(LThanE a=expr b=expr) {$stype = $a.stype;} -> ^(LThanE Identifier[$stype] expr expr)
  | ^(Or a=expr b=expr) {$stype = $a.stype;} -> ^(Or Identifier[$stype] expr expr)
  | ^(Xor a=expr b=expr) {$stype = $a.stype;} -> ^(Xor Identifier[$stype] expr expr)
  | ^(And a=expr b=expr) {$stype = $a.stype;} -> ^(And Identifier[$stype] expr expr)
  | ^(By a=expr b=expr) {$stype = $a.stype;} -> ^(By Identifier[$stype] expr expr)
  | ^(CALL id=Identifier ^(ARGLIST expr*)) {
    ProcedureSymbol ps = symtab.resolveProcedure($id.text);
    FunctionSymbol fs = symtab.resolveFunction($id.text);
    if (ps == null && fs == null)
      throw new RuntimeException(getErrorHeader() + $id.text + " is undefined function or procedure");
    if (ps != null && fs == null)
      $stype = ps.getType(0).getName();
    if (fs != null && ps == null)
      $stype = fs.getType(0).getName();
    else 
      throw new RuntimeException(getErrorHeader() + "Multiple defined error");
  } -> ^(CALL Identifier[$stype] Identifier[$id.text] ^(ARGLIST expr*))
  | id=Identifier {
    Symbol s = currentscope.resolve($id.text);
    if (s == null)
      throw new RuntimeException(getErrorHeader() + $id.text + " is undefined");
    VariableSymbol vs = (VariableSymbol) s;
    $stype = vs.getType(0).getName();
  } -> Identifier[$stype] Identifier[$id.text]
  | ^(As type expr) {$stype = $type.tsym.getName();}
  | Number {$stype = "integer";} -> Identifier["integer"] Number
  | FPNumber {$stype = "real";} -> Identifier["real"] FPNumber
  | True {$stype = "boolean";} -> Identifier["boolean"] True
  | False {$stype = "boolean";} -> Identifier["boolean"] False
  | ^(TUPLEEX expr+) {$stype = "tuple";}
  | ^(Dot id=Identifier {
    Symbol s = currentscope.resolve($id.text);
    if (s == null)
      throw new RuntimeException(getErrorHeader() + $id.text + " is undefined");
    VariableSymbol vs = (VariableSymbol) s;
    ts = (TupleSymbol) vs.getType(0);} 
    (eid=Identifier {
    for (Integer i=0; i<ts.getFieldNames().size(); i++) {
      if ($eid.text.equals(ts.getFieldNames().get(i).name)) {
        index = i+1; 
        $stype = ts.getFieldNames().get(i).type.getName();
        break;
      }
    }
    if (index.equals(-1))
      throw new RuntimeException(getErrorHeader() + $eid.text + " is undefined");
  } -> ^(Dot $id Number[index.toString()]) | n=Number {
    String num = $n.text;
    index = index.parseInt(num);
    $stype = ts.getFieldNames().get(index-1).type.getName();
  } -> ^(Dot $id $n))) 
  ;
  
