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
  import java.util.LinkedList;
}
 
@members {
    SymbolTable symtab;
    Scope currentscope;
    int nestedLoop = 0;
    boolean inFunction = false;
    String inputfile;
    public TypeExpand(TreeNodeStream input, SymbolTable symtab, String inputfile) {
        this(input);
        this.symtab = symtab;
        currentscope = symtab.globals;
        ret_type_stack = new LinkedList<Type>();
        ret_type_stack.push(new BuiltInTypeSymbol("N/A"));
        this.inputfile = inputfile;
    }
    private String getErrorHeader() {
      CommonTree tree = (CommonTree) input.LT(1);
      int line = tree.getLine();
      int chline = tree.getCharPositionInLine();
      return "In " + inputfile + ", " + line + ":" + chline + ": ";
  }
  
    private void checkGlobalName(String symbolName) {
      ProcedureSymbol ps = symtab.resolveProcedure(symbolName);
      FunctionSymbol fs = symtab.resolveFunction(symbolName);
      Symbol type = symtab.resolveType(symbolName);
      if ((ps != null && ps.isDefined()) || (fs != null && fs.isDefined()) || type != null) {
        throw new RuntimeException(getErrorHeader() + symbolName + " is defined multiple times in global scope");
      }
    }
    Boolean proc_ret_flag = false;
    LinkedList<Type> ret_type_stack;
}

program
@after {
  ProcedureSymbol ps = symtab.resolveProcedure("main");
  if (ps == null)
    throw new RuntimeException(getErrorHeader() + "Missing main procedure");
}
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
  | Break {
    if (nestedLoop == 0)
      throw new RuntimeException(getErrorHeader() + "break statement not in a loop");
  }
  | Continue {
    if (nestedLoop == 0)
      throw new RuntimeException(getErrorHeader() + "continue statement not in a loop");
  }
  ;
  
outputstream
  : ^(RArrow e=expr stream=Identifier)
  {
    Symbol s = currentscope.resolve($stream.text);
    
    if (s == null)
      throw new RuntimeException(getErrorHeader() + $stream.text + " is undefined");
    VariableSymbol vs = (VariableSymbol) s;
    
    if ($e.stype.getName().equals("tuple"))
      throw new RuntimeException(getErrorHeader() + "cannot send tuples to streams");
    
    String stype = vs.getType().getName();
    if (!stype.equals("std_output"))
      throw new RuntimeException(getErrorHeader() + $stream.text + " is not an output stream");
  }
  ;

inputstream
@init {
	String varType = "";
}
  : ^(LArrow var=Identifier stream=Identifier)
  {
    Symbol streamSymbol = currentscope.resolve($stream.text);
    Symbol varSymbol = currentscope.resolve($var.text);
    
    if (streamSymbol == null)
      throw new RuntimeException(getErrorHeader() + $stream.text + " is undefined");
    else if (varSymbol == null)
      throw new RuntimeException(getErrorHeader() + $var.text + " is undefined");
      
    VariableSymbol streamVS = (VariableSymbol) streamSymbol;
    VariableSymbol varVS = (VariableSymbol) varSymbol;
    
    String streamType = streamVS.getType().getName();
    varType = varVS.getType().getName();
    if (!streamType.equals("std_input"))
      throw new RuntimeException(getErrorHeader() + $stream.text + " is not an input stream");
    else if (varType.equals("std_input") || varType.equals("std_output") || varType.equals("tuple"))
      throw new RuntimeException(getErrorHeader() + "invalid type for input stream to variable " + $var.text);
  } -> ^(LArrow Identifier[varType] $var $stream)
  ;
  
streamstate
  : ^(Stream stream=Identifier)
  {
  	Symbol streamSymbol = currentscope.resolve($stream.text);
  	
  	if (streamSymbol == null)
      throw new RuntimeException(getErrorHeader() + $stream.text + " is undefined");
  
    VariableSymbol streamVS = (VariableSymbol) streamSymbol;
    String streamType = streamVS.getType().getName();
    if (!streamType.equals("std_input"))
      throw new RuntimeException(getErrorHeader() + $stream.text + " is not an input stream");
  }
  ;

declaration
@init {
  BuiltInTypeSymbol spec = null;
  BuiltInTypeSymbol type = null; 
  VariableSymbol vs = null;
}
@after {
  Symbol sym = null;
  if (currentscope instanceof NestedScope) {
    NestedScope ns = (NestedScope)currentscope;
    sym = ns.symbols.get($id.text);
  }
  else if (currentscope instanceof GlobalScope) {
    //checkGlobalName($id.text);
    GlobalScope gs = (GlobalScope)currentscope;
    sym = gs.symbols.get($id.text);
  }
  
  if (sym != null) {
    throw new RuntimeException(getErrorHeader() + "variable " + $id.text + " defined more than once in same scope");
  }  

  vs = new VariableSymbol($id.text, type, spec);
  
  if (!vs.isConst() && currentscope.getScopeName() == "global") {
    throw new RuntimeException(getErrorHeader() + "global variable " + vs.getName() + " must be const");
  }
  
  currentscope.define(vs);
}

  : ^(DECL specifier? (t=type {type = (BuiltInTypeSymbol) $t.tsym;}) id=Identifier) {
    //TO-DO: add vector and matrix type expansion for rewrite rule
  } -> ^(DECL type* $id)
  | ^(DECL (s=specifier {spec = (BuiltInTypeSymbol) $s.tsym;})? (t=type {type = (BuiltInTypeSymbol) $t.tsym;}) ^(Assign id=Identifier e=expr)) {
    
    if (type != null && symtab.lookup($e.stype, type) == null)
      throw new RuntimeException(getErrorHeader() + "assignment type error, expected " + type.getName() + " but got " + $e.stype.getName());
      
    stream_DECL.reset();
    
    if (type == null && ($e.stype.getName() == "null" || $e.stype.getName() == "identity")) {
      throw new RuntimeException(getErrorHeader() + "cannot infer type for variable " + $id.text);
    }
    VariableSymbol temp = new VariableSymbol($id.text, type, spec);
    ArrayList<Type> types = new ArrayList<Type>();
    if (type == null && (temp.isVar() || temp.isConst())) {
      types.add($e.stype);
    } else {
      types.add(type);
      if (type.getName().equals("vector")) {
        VectorTypeSymbol vts = (VectorTypeSymbol) type;
        types.add(vts.getVectorType());
        types.add(new BuiltInTypeSymbol(vts.getVectorSize().toString()));
      }
    }
      
    for (int i=0; i<types.size(); i++) {
      stream_DECL.add((CommonTree) adaptor.create(Identifier, types.get(i).getName()));
      if (types.get(i).getName().equals("tuple")) {
        TupleSymbol ts = (TupleSymbol) types.get(i);
        for (int j=0; j<ts.getFieldNames().size(); j++)
          stream_DECL.add((CommonTree) adaptor.create(Identifier, ts.getFieldNames().get(j).type.getName()));
      } 
    }
    
  } -> ^(DECL ^(DECL DECL*) ^(Assign $id $e))
  | ^(DECL (s=specifier {spec = (BuiltInTypeSymbol) $s.tsym;})* ^(Assign id=Identifier StdInput {type = (BuiltInTypeSymbol) symtab.resolveType("std_input");}))
    -> ^(DECL StdInput["std_input"] ^(Assign $id StdInput))
  | ^(DECL (s=specifier {spec = (BuiltInTypeSymbol) $s.tsym;})* ^(Assign id=Identifier StdOutput {type = (BuiltInTypeSymbol) symtab.resolveType("std_output");}))
    -> ^(DECL StdOutput["std_output"] ^(Assign $id StdOutput))
  ;
  
typedef
@after {

  //checkGlobalName($id.text);

  String bitname = $t.tsym.getName();
  String oldname;
  do {
    oldname = bitname;
    bitname = symtab.resolveTDType(bitname).getSourceSymbol().getName();
  } while (!bitname.equals("null"));
  Symbol s = symtab.resolveType(oldname);
  if (s == null)
    throw new RuntimeException(getErrorHeader() + "type " + s.getName() + " doesn't exist");
  
  BuiltInTypeSymbol bits = (BuiltInTypeSymbol) s;
  TypeDefSymbol tds = new TypeDefSymbol(bits, $id.text);
  symtab.defineType(tds);
}
  : ^(Typedef t=type id=Identifier)
  ;
  catch [NullPointerException npe] {
    throw new RuntimeException(getErrorHeader() + "cannot typedef a non-existant type");
  }

block
@init {currentscope = new NestedScope("blockscope", currentscope); }
@after {currentscope = currentscope.getEnclosingScope();}
  : ^(BLOCK declaration* statement*)
  ;
  
procedure
@init {
  //ArrayList<Symbol> params = new ArrayList<Symbol>();
  BuiltInTypeSymbol type = null;
  Boolean def = false;
}
@after {
  //checkGlobalName($id.text);
  
  ProcedureSymbol ps = new ProcedureSymbol($id.text, type, $pl.params);
  ProcedureSymbol check = symtab.resolveProcedure(ps.getName());
  if (check != null && check.isDefined())
    throw new RuntimeException(getErrorHeader() + "multiple defined procedures");
  if (check != null && !check.isDefined()) {
    if (check.getParamList().size() != ps.getParamList().size())
      throw new RuntimeException(getErrorHeader() + "mismatch in number of operators between prototype and definition");
    for (int i=0; i<ps.getParamList().size(); i++) {
      if (symtab.lookup(ps.getParamList().get(i).getType(), check.getParamList().get(i).getType()) == null &&
        symtab.lookup(check.getParamList().get(i).getType(), ps.getParamList().get(i).getType()) == null)
        throw new RuntimeException(getErrorHeader() + "type mismatch between prototype and definition");
    }
  }
  if (def)
    ps.setDefined();
  symtab.defineProcedure(ps);
  currentscope = currentscope.getEnclosingScope();
  ret_type_stack.pop();
}
  : ^(Procedure id=Identifier pl=paramlist ^(Returns type 
    {ret_type_stack.push($type.tsym);}) (block {def = true;})?) {type = (BuiltInTypeSymbol) $type.tsym;}
  | ^(Procedure id=Identifier pl=paramlist {ret_type_stack.push(new BuiltInTypeSymbol("void"));} 
    (block {def = true;})?)
  ;
   
function
@init {
  //ArrayList<Symbol> params = new ArrayList<Symbol>();
  BuiltInTypeSymbol type = null;
  //currentscope = new NestedScope("funcscope", currentscope);
  Boolean def = false;
}
@after {
  //checkGlobalName($id.text);
  for(Symbol param : $pl.params) {
  	if (param instanceof VariableSymbol) {
  		VariableSymbol vs = (VariableSymbol)param;
  		if (vs.isVar()) {
  			throw new RuntimeException("function " + $id.text + " needs only const parameters");
  		}
  	}
  }
  FunctionSymbol fs = new FunctionSymbol($id.text, type, $pl.params);
  FunctionSymbol check = symtab.resolveFunction(fs.getName());
  if (check != null && check.isDefined())
    throw new RuntimeException(getErrorHeader() + "multiple defined function");
  if (check != null && !check.isDefined()) {
    if (check.getParamList().size() != fs.getParamList().size())
      throw new RuntimeException(getErrorHeader() + "mismatch in number of operators between prototype and definition");
    for (int i=0; i<fs.getParamList().size(); i++) {
      if (symtab.getBuiltInSymbol(check.getParamList().get(i).getType().getName()).getName().equals(
        symtab.getBuiltInSymbol(fs.getParamList().get(i).getType().getName()).getName()))
        throw new RuntimeException(getErrorHeader() + "type mismatch between prototype and definition");
    }
  }
  if (def)
    fs.setDefined();
  symtab.defineFunction(fs);
  currentscope = currentscope.getEnclosingScope();
  ret_type_stack.pop();
}
  : ^(Function id=Identifier pl=paramlist ^(Returns type {ret_type_stack.push($type.tsym);}) {inFunction = true;} (
    block {def = true;})? {inFunction = false;}) {type = (BuiltInTypeSymbol) $type.tsym;}
  | ^(Function id=Identifier pl=paramlist ^(Returns type {ret_type_stack.push(new BuiltInTypeSymbol("N/A"));})
   {inFunction = true; def = true;} ^(Assign expr {
    if (symtab.lookup($expr.stype, $type.tsym) == null)
      throw new RuntimeException(getErrorHeader() + "type mismatch on function return");
  }
  ) {inFunction = false;}) {type = (BuiltInTypeSymbol) $type.tsym;}
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
@init {BuiltInTypeSymbol type = null;}
@after {
  type = (BuiltInTypeSymbol) $t.tsym;
  Type spec;
  if ($s.text == null) {
  	spec = new BuiltInTypeSymbol("const");
  }
  else {
  	spec = new BuiltInTypeSymbol($s.text);
  }
  
  VariableSymbol vs = new VariableSymbol($id.text, type, spec);
  currentscope.define(vs);
  $varsym = vs;
}
  : ^(id=Identifier s=specifier? t=type)
  ;
  
callStatement
@init {
  ArrayList<Type> argtypes = new ArrayList<Type>();
}
  : ^(CALL id=Identifier ^(ARGLIST (e=expr {argtypes.add($e.stype);})*)) {
    ProcedureSymbol ps = symtab.resolveProcedure($id.text);
    FunctionSymbol fs = symtab.resolveFunction($id.text);
    if (fs != null) {
      throw new RuntimeException(getErrorHeader() + "functions cannot be used as a statement");
    }
    if (ps == null) {
      throw new RuntimeException(getErrorHeader() + $id.text + " is undefined procedure");
    }
    else {
      if (inFunction) {
        throw new RuntimeException(getErrorHeader() + ps.getName() + ": calling procedure inside a function");
      }
    
    
      ArrayList<Symbol> argsyms = ps.getParamList();
      if (argsyms.size() != argtypes.size())
        throw new RuntimeException(getErrorHeader() + ps.getName() + ": number of arguments doesn't match");
      
      for (int i=0; i<argsyms.size(); i++) {
        VariableSymbol vs = (VariableSymbol) argsyms.get(i);
        if (!vs.getType().getName().equals(argtypes.get(i).getName()))
          throw new RuntimeException(getErrorHeader() + "type mismatch, expected " +  vs.getType().getName() + " but got " + argtypes.get(i).getName());
      }
    }
  }
  ;
  
returnStatement
@init {
  boolean hasexpr = false;
  
}
@after {
  if (ret_type_stack.peek().getName().equals("N/A"))
    throw new RuntimeException(getErrorHeader() + "invalid location for return statement");
  if (!hasexpr && !ret_type_stack.peek().getName().equals("void"))
    throw new RuntimeException(getErrorHeader() + "type mismatch on return statement");
}
  : ^(Return (e=expr {
    if (symtab.lookup($e.stype, ret_type_stack.peek()) == null)
      throw new RuntimeException(getErrorHeader() + "type mismatch on return statement");
    hasexpr = true;
  })?)
  ;
  
assignment
  : ^(Assign var=Identifier e=expr)
  {
    Symbol varSymbol = currentscope.resolve($var.text);
    
    if (varSymbol == null)
      throw new RuntimeException(getErrorHeader() + $var.text + " is undefined");
      
    VariableSymbol varVS = (VariableSymbol) varSymbol;
    if (varVS.isConst())
      throw new RuntimeException(getErrorHeader() + "Cannot reassign a variable to a const");
    
    String varType = varVS.getType().getName();
    
    if (varType.equals("std_input") || varType.equals("std_output"))
      throw new RuntimeException(getErrorHeader() + "Cannot assign to stream " + $var.text);
      
    if (symtab.lookup($e.stype, varVS.getType()) == null)
      throw new RuntimeException(getErrorHeader() + "assignment type error, expected " + varType + " but got " + $e.stype.getName());
    
  }
  ;
  
ifstatement
  : ^(If e=expr slist ^(Else slist)) {
    if (!$e.stype.getName().equals("boolean"))
      throw new RuntimeException(getErrorHeader() + "conditional statement requires boolean, but got " + $e.stype.getName());
  }
  | ^(If e=expr slist) {
    if (!$e.stype.getName().equals("boolean"))
      throw new RuntimeException(getErrorHeader() + "conditional statement requires boolean, but got " + $e.stype.getName());
  }
  ;
  
loopstatement
@after {
  nestedLoop--;
}
  : ^(Loop {nestedLoop++;} ^(While e=expr) slist) {
    if (!$e.stype.getName().equals("boolean"))
      throw new RuntimeException(getErrorHeader() + "conditional statement requires boolean, but got " + $e.stype.getName());
  }
  | ^(Loop {nestedLoop++;} slist ^(While e=expr)) {
    if (!$e.stype.getName().equals("boolean"))
      throw new RuntimeException(getErrorHeader() + "conditional statement requires boolean, but got " + $e.stype.getName());
  }
  | ^(Loop {nestedLoop++;} slist)
  ;
  
slist
  : block
  | statement
  | declaration
  ;
  
  //TO-DO: change vector and matrix to return their own type objects
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
  ArrayList<FieldPair> tuplepairs = new ArrayList<FieldPair>();
  ArrayList<Type> argtypes = new ArrayList<Type>();
  String errorhead = getErrorHeader();
}
  : ^(Plus a=expr b=expr) {
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
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
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
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
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
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
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Divide Identifier[$stype.getName()] expr expr)
  | ^(Mod a=expr b=expr) {
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null)
      $stype = $b.stype;
    else if (lub != null)
      $stype = $a.stype;
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Mod Identifier[$stype.getName()] expr expr)
  | ^(Exponent a=expr b=expr) {
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
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
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(GThan Identifier[$stype.getName()] expr expr)
  | ^(LThan a=expr b=expr) {
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(LThan Identifier[$stype.getName()] expr expr)
  | ^(GThanE a=expr b=expr) {
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(GThanE Identifier[$stype.getName()] expr expr)
  | ^(LThanE a=expr b=expr) {
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(LThanE Identifier[$stype.getName()] expr expr)
  | ^(Or a=expr b=expr) {
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Or Identifier[$stype.getName()] expr expr)
  | ^(Xor a=expr b=expr) {
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
    Boolean lua = symtab.lookup($a.stype, $b.stype);
    Boolean lub = symtab.lookup($b.stype, $a.stype);
      
    if (lua != null || lub != null)
      $stype = new BuiltInTypeSymbol("boolean");
    else 
      throw new RuntimeException(errorhead + " type promotion error");
    
  } -> ^(Xor Identifier[$stype.getName()] expr expr)
  | ^(And a=expr b=expr) {
    if ($a.stype.getName().equals("tuple") || $b.stype.getName().equals("tuple"))
      throw new RuntimeException(errorhead + "can't perform this operation on a tuple");
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
    proc_ret_flag = true;
    ProcedureSymbol ps = symtab.resolveProcedure($id.text);
    FunctionSymbol fs = symtab.resolveFunction($id.text);
    if (ps == null && fs == null)
      throw new RuntimeException(errorhead + $id.text + " is undefined function or procedure");
    if (ps != null && fs == null) {
      if (inFunction) {
        throw new RuntimeException(errorhead + ps.getName() + ": calling a procedure inside a function");
      }
        
      $stype = ps.getType();
      ArrayList<Symbol> argsyms = ps.getParamList();
      if (argsyms.size() != argtypes.size())
        throw new RuntimeException(errorhead + ps.getName() + ": number of arguments doesn't match");
      for (int i=0; i<argsyms.size(); i++) {
        VariableSymbol vs = (VariableSymbol) argsyms.get(i);
        
        if (symtab.lookup(argtypes.get(i), vs.getType()) == null)
          throw new RuntimeException(errorhead + "type mismatch, expected " +  vs.getType().getName() + " but got " + argtypes.get(i).getName());
      }
    } else if (fs != null && ps == null) {
      $stype = fs.getType();
      ArrayList<Symbol> argsyms = fs.getParamList();
      if (argsyms.size() != argtypes.size())
        throw new RuntimeException(errorhead + fs.getName() + ": number of arguments doesn't match");
      for (int i=0; i<argsyms.size(); i++) {
        VariableSymbol vs = (VariableSymbol) argsyms.get(i);
        if (symtab.lookup(argtypes.get(i), vs.getType()) == null)
          throw new RuntimeException(errorhead + "type mismatch, expected " +  vs.getType().getName() + " but got " + argtypes.get(i).getName());
      }
    } else 
      throw new RuntimeException(errorhead + "Multiple defined error");
    argtypes.clear();
  } -> ^(CALL Identifier[$stype.getName()] Identifier[$id.text] ^(ARGLIST expr*))
  | id=Identifier {
    Symbol s = currentscope.resolve($id.text);
    if (s == null)
      throw new RuntimeException(errorhead+ $id.text + " is undefined");
    VariableSymbol vs = (VariableSymbol) s;
    $stype = symtab.getBuiltInSymbol(vs.getType().getName());
    if ($stype.getName().equals("std_input") || $stype.getName().equals("std_output"))
      throw new RuntimeException(errorhead + "stream " + $id.text + " cannot occur in an expression");
    stream_Identifier.reset();
    //System.out.println($stype.getName());
    if (symtab.getBuiltInSymbol(vs.getType().getName()).equals("tuple")) {
      ts = (TupleSymbol) vs.getType();
      
      for (int i=0; i<ts.getFieldNames().size(); i++) {
        stream_Identifier.add((CommonTree) adaptor.create(Identifier, ts.getFieldNames().get(i).type.getName()));
      }
    }
    stream_Identifier.nextNode();
  } 
    -> ^(Identifier[$stype.getName()] (Identifier)*) Identifier[$id.text] 
  | ^(As t=type e=expr) {
    Boolean exlu = symtab.exLookup($type.tsym, $e.stype);
    if (exlu == null)
      throw new RuntimeException(errorhead + " expression cannot be cast to " + $type.tsym.getName());
    $stype = $type.tsym;
    if ($t.tsym.getName().equals($e.stype.getName())) {
    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"rule e",e!=null?e.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    
                    {
                        adaptor.addChild(root_0, stream_e.nextTree());

                    }

                    retval.tree = root_0;}
     }
  } 
  | Number {$stype = new BuiltInTypeSymbol("integer");} -> Identifier["integer"] Number[$Number.text.replaceAll("_","")]
  | FPNumber {$stype = new BuiltInTypeSymbol("real");} -> Identifier["real"] FPNumber[$FPNumber.text.replaceAll("_","")]
  | True {$stype = new BuiltInTypeSymbol("boolean");} -> Identifier["boolean"] True
  | False {$stype = new BuiltInTypeSymbol("boolean");} -> Identifier["boolean"] False
  | Null {$stype = new BuiltInTypeSymbol("null");} -> Identifier["null"] Null
  | Identity {$stype = new BuiltInTypeSymbol("identity");} -> Identifier["identity"] Identity
  | Char {$stype = new BuiltInTypeSymbol("character");} -> Identifier["character"] Char
  | ^(TUPLEEX {tuplepairs = new ArrayList<FieldPair>();} (e=expr {
    tuplepairs.add(new FieldPair("null", $e.stype));
    stream_TUPLEEX.reset();
    stream_TUPLEEX.add((CommonTree) adaptor.create(Identifier, $e.stype.getName()));
  })+) {$stype = new TupleSymbol("tuple", tuplepairs);} -> ^(TUPLEEX ^(Identifier[$stype.getName()] TUPLEEX+) expr+)
  | ^(Dot id=Identifier {
    Symbol s = currentscope.resolve($id.text);
    if (s == null)
      throw new RuntimeException(errorhead+ $id.text + " is undefined");
    VariableSymbol vs = (VariableSymbol) s;
    if (!vs.getType().getName().equals("tuple"))
      throw new RuntimeException(errorhead + ". operator must be used on a tuple");
    ts = (TupleSymbol) vs.getType();
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
      throw new RuntimeException(errorhead+ $eid.text + " is undefined");
  } -> Identifier[$stype.getName()] ^(Dot $id Number[index.toString()]) | n=Number {
    String num = $n.text;
    index = index.parseInt(num);
    $stype = ts.getFieldNames().get(index-1).type;
  } -> Identifier[$stype.getName()] ^(Dot $id $n))) 
  | ^(NEG e=expr) {
    if (!$e.stype.getName().equals("integer") && !$e.stype.getName().equals("real")) 
      throw new RuntimeException(errorhead + " type error");
    else
      $stype = $e.stype;
  }
  | ^(POS e=expr) {
    if (!$e.stype.getName().equals("integer") && !$e.stype.getName().equals("real")) 
      throw new RuntimeException(errorhead + " type error");
    else
      $stype = $e.stype;
  }
  | streamstate { $stype = new BuiltInTypeSymbol("integer");} -> Identifier[$stype.getName()] streamstate
  ;
  
