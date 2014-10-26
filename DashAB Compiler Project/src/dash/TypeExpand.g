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
  : ^(Typedef type Identifier)
  ;

block
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
  FunctionSymbol ps = new FunctionSymbol($id.text, type, params);
  symtab.defineFunction(ps);
}
  : ^(Function id=Identifier paramlist ^(Returns type) block) {type.add($type.tsym);}
  | ^(Function id=Identifier paramlist ^(Returns type) ^(Assign expr)) {type.add($type.tsym);}
  ;
  
paramlist
  : ^(PARAMLIST parameter*)
  ;
  
parameter
  : ^(Identifier type)
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
  | tuple {$tsym = (Type) symtab.resolveType("tuple");}
  | t=Identifier {$tsym = (Type) symtab.resolveType($t.text);}
  ;
  
tuple
  : ^(Tuple type+)
  ;

specifier returns [Type tsym]
  : t=Const {$tsym = (Type) symtab.resolveSpec($t.text);}
  | t=Var {$tsym = (Type) symtab.resolveSpec($t.text);}
  ;
  
expr returns [String stype]
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
    if (ps != null)
      $stype = ps.getType(0).getName();
    if (fs != null)
      $stype = fs.getType(0).getName();
    else 
      throw new RuntimeException("Multiple defined error");
  } -> ^(CALL Identifier[$stype] Identifier[$id.text] ^(ARGLIST expr*))
  | id=Identifier {
    Symbol s = currentscope.resolve($id.text);
    VariableSymbol vs = (VariableSymbol) s;
    $stype = vs.getType(0).getName();
  } -> Identifier[$stype] Identifier[$id.text]
  | ^(As type expr) {$stype = $type.tsym.getName();}
  | Number {$stype = "integer";} -> Identifier["integer"] Number
  | FPNumber {$stype = "real";} -> Identifier["real"] FPNumber
  ;
  
