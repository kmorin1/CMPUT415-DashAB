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
      
      //int line = input.getTokenStream().get(input.index()).getLine(); 
      //int chline = input.getTokenStream().get(input.index()).getCharPositionInLine();
      int line = 0;
      int chline = 0;
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
  : ^(RArrow e=expr stream=Identifier)
  {
    Symbol s = currentscope.resolve($stream.text);
    
    if (s == null)
      throw new RuntimeException($stream.text + " is undefined");
    VariableSymbol vs = (VariableSymbol) s;
    
    if ($e.stype.getName().equals("tuple"))
      throw new RuntimeException("cannot send tuples to streams");
    
    String stype = vs.getType(0).getName();
    if (!stype.equals("std_output"))
      throw new RuntimeException($stream.text + " is not an output stream");
  }
  ;

inputstream
  : ^(LArrow var=Identifier stream=Identifier)
  {
    Symbol streamSymbol = currentscope.resolve($stream.text);
    Symbol varSymbol = currentscope.resolve($var.text);
    
    if (streamSymbol == null)
      throw new RuntimeException($stream.text + " is undefined");
    else if (varSymbol == null)
      throw new RuntimeException($var.text + " is undefined");
      
    VariableSymbol streamVS = (VariableSymbol) streamSymbol;
    VariableSymbol varVS = (VariableSymbol) varSymbol;
    
    String streamType = streamVS.getType(0).getName();
    String varType = varVS.getType(0).getName();
    if (!streamType.equals("std_input"))
      throw new RuntimeException($stream.text + " is not an input stream");
    else if (varType.equals("std_input") || varType.equals("std_output") || varType.equals("tuple"))
      throw new RuntimeException("invalid type for input stream to variable " + $var.text);
  }
  ;

declaration
@init {
  ArrayList<Type> specs = new ArrayList<Type>();
  ArrayList<Type> types = new ArrayList<Type>(); 
  VariableSymbol vs = null;
}
@after {
  vs = new VariableSymbol($id.text, types, specs);
  
  currentscope.define(vs);
}
  : ^(DECL (s=specifier {specs.add($s.tsym);})* (t=type {types.add($t.tsym);})* id=Identifier) {
    if (specs.size() != 0) {
        throw new RuntimeException("cannot have specifiers without assigning a variable");
    }
  } -> ^(DECL type* $id)
  | ^(DECL (s=specifier {specs.add($s.tsym);})* (t=type {types.add($t.tsym);})* ^(Assign id=Identifier e=expr)) {
    if (types.size() > 0 && symtab.lookup($e.stype, types.get(0)) == null)
      throw new RuntimeException("assignment type error, expected " + types.get(0).getName() + " but got " + $e.stype.getName());
      
    stream_DECL.reset();
  
    if (new VariableSymbol($id.text, types, specs).isVar()) {
      types.clear();
      types.add($e.stype);
        
    }
      
    for (int i=0; i<types.size(); i++) {
      stream_DECL.add((CommonTree) adaptor.create(Identifier, types.get(i).getName()));
    }
    
  } -> ^(DECL DECL+ ^(Assign $id $e))
  | ^(DECL (s=specifier {specs.add($s.tsym);})* ^(Assign id=Identifier StdInput {types.add((Type) symtab.resolveType("std_input"));})) 
    -> ^(DECL StdInput["std_input"] ^(Assign StdInput))
  | ^(DECL (s=specifier {specs.add($s.tsym);})* ^(Assign id=Identifier StdOutput {types.add((Type) symtab.resolveType("std_output"));}))
    -> ^(DECL StdOutput["std_output"] ^(Assign StdOutput))
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
  //ArrayList<Symbol> params = new ArrayList<Symbol>();
  ArrayList<Type> type = new ArrayList<Type>();
}
@after {
  ProcedureSymbol ps = new ProcedureSymbol($id.text, type, $pl.params);
  symtab.defineProcedure(ps);
  currentscope = currentscope.getEnclosingScope();
}
  : ^(Procedure id=Identifier pl=paramlist ^(Returns type) block) {type.add($type.tsym);}
  | ^(Procedure id=Identifier pl=paramlist block)
  ;
   
function
@init {
  //ArrayList<Symbol> params = new ArrayList<Symbol>();
  ArrayList<Type> type = new ArrayList<Type>();
}
@after {
  FunctionSymbol fs = new FunctionSymbol($id.text, type, $pl.params);
  symtab.defineFunction(fs);
  currentscope = currentscope.getEnclosingScope();
}
  : ^(Function id=Identifier pl=paramlist ^(Returns type) block) {type.add($type.tsym);}
  | ^(Function id=Identifier pl=paramlist ^(Returns type) ^(Assign expr)) {type.add($type.tsym);}
  ;
  
paramlist returns [ArrayList<Symbol> params]
@init {
  ArrayList<Symbol> paramlst = new ArrayList<Symbol>();
  currentscope = new NestedScope("paramscope", currentscope);
}
@after {$params = paramlst;}
  : ^(PARAMLIST (p=parameter {paramlst.add($p.varsym);})*)
  ;
  
parameter returns [VariableSymbol varsym]
@init {ArrayList<Type> type = new ArrayList<Type>();}
@after {
  type.add($t.tsym);
  ArrayList<Type> spec = new ArrayList<Type>();
  spec.add(new BuiltInTypeSymbol("const"));
  VariableSymbol vs = new VariableSymbol($id.text, type, spec);
  currentscope.define(vs);
  $varsym = vs;
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
  : ^(Assign var=Identifier e=expr)
  {
    Symbol varSymbol = currentscope.resolve($var.text);
    
    if (varSymbol == null)
      throw new RuntimeException($var.text + " is undefined");
      
    VariableSymbol varVS = (VariableSymbol) varSymbol;
    if (varVS.isConst())
      throw new RuntimeException("Cannot reassign a variable to a const");
    
    String varType = varVS.getType(0).getName();
    
    if (varType.equals("std_input") || varType.equals("std_output"))
      throw new RuntimeException("Cannot assign to stream " + $var.text);
      
    if (symtab.lookup($e.stype, varVS.getType(0)) == null)
      throw new RuntimeException("assignment type error, expected " + varType + " but got " + $e.stype.getName());
    
  }
  ;
  
ifstatement
  : ^(If e=expr slist ^(Else slist)) {
    if (!$e.stype.getName().equals("boolean"))
      throw new RuntimeException("conditional statement requires boolean, but got " + $e.stype.getName());
  }
  | ^(If e=expr slist) {
    if (!$e.stype.getName().equals("boolean"))
      throw new RuntimeException("conditional statement requires boolean, but got " + $e.stype.getName());
  }
  ;
  
loopstatement
  : ^(Loop ^(While e=expr) slist) {
    if (!$e.stype.getName().equals("boolean"))
      throw new RuntimeException("conditional statement requires boolean, but got " + $e.stype.getName());
  }
  | ^(Loop slist ^(While e=expr)) {
    if (!$e.stype.getName().equals("boolean"))
      throw new RuntimeException("conditional statement requires boolean, but got " + $e.stype.getName());
  }
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
  
expr returns [Type stype]
@init {
  Integer index = -1; 
  TupleSymbol ts = null; 
  ArrayList<Type> argtypes = new ArrayList<Type>();
  String errorhead = getErrorHeader();
}
  : ^(Plus a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Plus Identifier[$stype.getName()] expr expr)
  | ^(Minus a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Minus Identifier[$stype.getName()] expr expr)
  | ^(Multiply a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Multiply Identifier[$stype.getName()] expr expr)
  | ^(Divide a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Divide Identifier[$stype.getName()] expr expr)
  | ^(Exponent a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Exponent Identifier[$stype.getName()] expr expr)
  | ^(Equals a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Equals Identifier[$stype.getName()] expr expr)
  | ^(NEquals a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(NEquals Identifier[$stype.getName()] expr expr)
  | ^(GThan a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(GThan Identifier[$stype.getName()] expr expr)
  | ^(LThan a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(LThan Identifier[$stype.getName()] expr expr)
  | ^(GThanE a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(GThanE Identifier[$stype.getName()] expr expr)
  | ^(LThanE a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(LThanE Identifier[$stype.getName()] expr expr)
  | ^(Or a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Or Identifier[$stype.getName()] expr expr)
  | ^(Xor a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Xor Identifier[$stype.getName()] expr expr)
  | ^(And a=expr b=expr) {
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(And Identifier[$stype.getName()] expr expr)
  | ^(Not e=expr) {
    if ($e.stype.getName().equals("boolean"))
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Not Identifier[$stype.getName()] expr)
  | ^(By a=expr b=expr) {$stype = $a.stype;} -> ^(By Identifier[$stype.getName()] expr expr)
  | ^(CALL id=Identifier ^(ARGLIST (e=expr {argtypes.add($e.stype);})*)) {
    ProcedureSymbol ps = symtab.resolveProcedure($id.text);
    FunctionSymbol fs = symtab.resolveFunction($id.text);
    if (ps == null && fs == null)
      throw new RuntimeException(getErrorHeader() + $id.text + " is undefined function or procedure");
    if (ps != null && fs == null) {
      $stype = ps.getType(0);
      ArrayList<Symbol> argsyms = ps.getParamList();
      if (argsyms.size() != argtypes.size())
        throw new RuntimeException(errorhead + ps.getName() + ": number of arguments doesn't match");
      for (int i=0; i<argsyms.size(); i++) {
        VariableSymbol vs = (VariableSymbol) argsyms.get(i);
        if (!vs.getType(0).getName().equals(argtypes.get(i).getName()))
          throw new RuntimeException(errorhead + "type mismatch, expected " +  vs.getType(0).getName() + " but got " + argtypes.get(i).getName());
      }
    } else if (fs != null && ps == null) {
      $stype = fs.getType(0);
      ArrayList<Symbol> argsyms = fs.getParamList();
      if (argsyms.size() != argtypes.size())
        throw new RuntimeException(errorhead + ps.getName() + ": number of arguments doesn't match");
      for (int i=0; i<argsyms.size(); i++) {
        VariableSymbol vs = (VariableSymbol) argsyms.get(i);
        if (!vs.getType(0).getName().equals(argtypes.get(i).getName()))
          throw new RuntimeException(errorhead + "type mismatch, expected " +  vs.getType(0).getName() + " but got " + argtypes.get(i).getName());
      }
    } else 
      throw new RuntimeException(getErrorHeader() + "Multiple defined error");
    argtypes.clear();
  } -> ^(CALL Identifier[$stype.getName()] Identifier[$id.text] ^(ARGLIST expr*))
  | id=Identifier {
    Symbol s = currentscope.resolve($id.text);
    if (s == null)
      throw new RuntimeException(getErrorHeader() + $id.text + " is undefined");
    VariableSymbol vs = (VariableSymbol) s;
    
    $stype = vs.getType(0);
    if ($stype.getName().equals("std_input") || $stype.getName().equals("std_output"))
      throw new RuntimeException("stream " + $id.text + " cannot occur in an expression");
  } {!currentscope.resolve($id.text).getType(0).getName().equals("tuple")}?
    -> Identifier[$stype.getName()] Identifier[$id.text] 
  | id=Identifier {
    Symbol s = currentscope.resolve($id.text);
    if (s == null)
      throw new RuntimeException(getErrorHeader() + $id.text + " is undefined");
    VariableSymbol vs = (VariableSymbol) s;
    $stype = vs.getType(0);
    if ($stype.getName().equals("std_input") || $stype.getName().equals("std_output"))
      throw new RuntimeException("stream " + $id.text + " cannot occur in an expression");
    ts = (TupleSymbol) vs.getType(0);
    //index = 0;
    stream_Identifier.reset();
    for (int i=0; i<ts.getFieldNames().size(); i++) {
      stream_Identifier.add((CommonTree) adaptor.create(Identifier, ts.getFieldNames().get(i).type.getName()));
    }
    stream_Identifier.nextNode();
  } {currentscope.resolve($id.text).getType(0).getName().equals("tuple")}?
    -> ^(Identifier[$stype.getName()] (Identifier)+) Identifier[$id.text] 
  | ^(As type e=expr) {
    Boolean exlu = symtab.exLookup($type.tsym, $e.stype);
    if (exlu == null)
      throw new RuntimeException(errorhead + " expression cannot be cast to " + $type.tsym.getName());
    $stype = $type.tsym;
  }
  | Number {$stype = new BuiltInTypeSymbol("integer");} -> Identifier["integer"] Number
  | FPNumber {$stype = new BuiltInTypeSymbol("real");} -> Identifier["real"] FPNumber
  | True {$stype = new BuiltInTypeSymbol("boolean");} -> Identifier["boolean"] True
  | False {$stype = new BuiltInTypeSymbol("boolean");} -> Identifier["boolean"] False
  | ^(TUPLEEX (e=expr {
    stream_TUPLEEX.reset();
    stream_TUPLEEX.add((CommonTree) adaptor.create(Identifier, $e.stype.getName()));
  })+) {$stype = new BuiltInTypeSymbol("tuple");} -> ^(TUPLEEX ^(Identifier[$stype.getName()] TUPLEEX+) expr+)
  | ^(Dot id=Identifier {
    Symbol s = currentscope.resolve($id.text);
    if (s == null)
      throw new RuntimeException(getErrorHeader() + $id.text + " is undefined");
    VariableSymbol vs = (VariableSymbol) s;
    ts = (TupleSymbol) vs.getType(0);
    index = -1; } 
    (eid=Identifier {
    for (Integer i=0; i<ts.getFieldNames().size(); i++) {
      if ($eid.text.equals(ts.getFieldNames().get(i).name)) {
        index = i+1; 
        $stype = ts.getFieldNames().get(i).type;
        break;
      }
    }
    if (index.equals(-1))
      throw new RuntimeException(getErrorHeader() + $eid.text + " is undefined");
  } -> Identifier[$stype.getName()] ^(Dot $id Number[index.toString()]) | n=Number {
    String num = $n.text;
    index = index.parseInt(num);
    $stype = ts.getFieldNames().get(index-1).type;
  } -> Identifier[$stype.getName()] ^(Dot $id $n))) 
  | ^(NEG e=expr) {
    if (!$e.stype.getName().equals("integer") || !$e.stype.getName().equals("real")) 
      throw new RuntimeException(errorhead + " type error");
    else
      $stype = $e.stype;
  }
  | ^(POS e=expr) {
    if (!$e.stype.getName().equals("integer") || !$e.stype.getName().equals("real")) 
      throw new RuntimeException(errorhead + " type error");
    else
      $stype = $e.stype;
  }
  ;
  
