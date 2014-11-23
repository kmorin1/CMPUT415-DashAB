tree grammar LLVMTemplater;

options {
  language = Java;
  output = template;
  tokenVocab = Syntax;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
}
 
@header
{
  package dash; 
  import SymTab.*;
}

@members {
    SymbolTable symtab;
    Scope currentscope;
    String inputfile;
    int counter = 0;
    int tmpcount= 0;
    int scopeCounter = 0;
    int numLoop = 0;
    int scopeNum = 0;
    public LLVMTemplater(TreeNodeStream input, SymbolTable unused) {
        this(input);
        this.symtab = new SymbolTable();
        currentscope = this.symtab.globals;
    }
  
  String IntType = "i32"; String CharType = "i8"; String BoolType = "i1"; String FloatType = "float";
  
  String AddOp = "add"; String SubOp = "sub"; String MulOp = "mul"; String DivOp = "div"; String ModOp = "rem";
  
  String Cmp = "icmp"; String FloatCmp = "fcmp";
  String EqOp = "eq"; String NeOp = "ne";
  String GtOp = "gt"; String LtOp = "lt";
  String GteOp = "ge"; String LteOp = "le";
  String XorOp = "xor"; String OrOp = "or"; String AndOp = "and";

  private String getArithOp(String type, String operation) {
  	if (type.toString().equals(IntType)) {
  	  if (operation.equals(DivOp) || operation.equals(ModOp))
  	    return "s" + operation;
  	  return operation;
  	}
  	else {
  		return "f" + operation;
  	}
  }
  
  private String getCompOp(String type, String operation) {
    if (type.toString().equals(IntType) || type.toString().equals(BoolType)) {
    	if (operation.equals(EqOp) || operation.equals(NeOp))
    		return operation;
      return "s" + operation;
    }
    else {
      return "o" + operation;
    }
  }
  
  private String getComp(String type) {
    if (type.toString().equals(FloatType)) {
      return FloatCmp;
    }
    else {
      return Cmp;
    }
  }
  
  private String getIntFromChar(String input) {
  	String noQuotes = input.replaceAll("'","");
  	if (noQuotes.length() > 1) {
  		char escaped = noQuotes.charAt(1);
  		switch(escaped) {
  			case('n') : //newline
  				return ""+10;
  			case('t') : //tab
  				return ""+9;
  			case('0') : //null
  				return ""+0;
  			case('r') : //carriage return
  				return ""+13;
  			case('\\') : //escaped backslash
  				return ""+92;
  			case('\"') : //escaped double quote
  				return ""+34;
  			case('a') : //bell
  				return ""+7;
  			case('b') : //backspace
  				return ""+8;
  			default :
  				return ""+0;
  			
  		}
  	}
  	else {
  		if (noQuotes.length() == 1 && noQuotes.charAt(0) == '\\')
  			return ""+39; //escaped single quote, the quote was removed
  		else
  			return ""+(int)noQuotes.charAt(0);
  	}
  }
  
  private String getEmptyValue(String type) {
  	if (type.equals(FloatType))
  		return "0.0";
  	else
  		return "0";
  
  }
  
  private String getCastFunc(String from, String to, int tmpNum, int result) {
	  if (to.equals(FloatType)) {
	  	if (from.equals(BoolType)) {
	  	  return "\%." + result + " = uitofp " + from + " \%." + tmpNum + " to " + to;
	  	}
	  	else {
	  		return "\%." + result + " = sitofp " + from + " \%." + tmpNum + " to " + to;
	  	}
	  }
	  else if (to.equals(BoolType)) {
	    return "\%." + result + " = icmp ne " + from + " \%." + tmpNum + ", 0";
	  }
	  else if (to.equals(IntType)) {
	   if (from.equals(FloatType)) {
	   	return "\%." + result + " = fptosi " + from + " \%." + tmpNum + " to " + to;
	   }
	   else {
	   	return "\%." + result + " = zext " + from + " \%." + tmpNum + " to " + to;
	   }
	  }
	  else if (to.equals(CharType)) {
	  	if (from.equals(IntType)) {
	  		return "\%.temp" + result + " = urem " + from + " \%." + tmpNum + ", 256\n" +
	  						"\%." + result + " = trunc " + from + " \%.temp" + result + " to " + to;
	  	}
	  	else {
	  		return "\%." + result + " = zext " + from + " \%." + tmpNum + " to " + to;
	  	}
	  }
    return "";
  }
  
  private String getLLVMvarSymbol(int scopeNum) {
  	if (scopeNum == 0) {
  		return "@";
  	}
  	else {
  		return "\%";
  	}
  	
  }
  
  private int getCurrentScopeNum() {
  	int currentScopeNum = 0;
  	if (currentscope instanceof NestedScope) {
  		NestedScope ns = (NestedScope)currentscope;
  		currentScopeNum = ns.scopeNum;
    }
    else if (currentscope instanceof GlobalScope) {
    	GlobalScope gs = (GlobalScope)currentscope;
    	currentScopeNum = gs.scopeNum;
    }
    return currentScopeNum;
  }
}

program
  : ^(PROGRAM g+=globalStatement*) -> llvmProgram(globalStatements={$g})
  ;
   
globalStatement
  : declaration -> return(a={$declaration.st})
  | procedure -> return(a={$procedure.st})
  | function -> return(a={$function.st})
  ;
    
statement
  : assignment -> return(a={$assignment.st})
  | outputstream -> return(a={$outputstream.st})
  | inputstream -> return(a={$inputstream.st})
  | ifstatement -> return(a={$ifstatement.st})
  | loopstatement -> return(a={$loopstatement.st})
  | block -> return(a={$block.st})
  | callStatement -> return(a={$callStatement.st})
  | returnStatement -> return(a={$returnStatement.st})
  | Break -> break(loopNum={numLoop})
  | Continue -> continue(loopNum={numLoop})
  ;
   
outputstream
  : ^(RArrow expr stream=Identifier) -> print(expr={$expr.st}, type={$expr.stype}, result={counter})
  ;

inputstream
  : ^(LArrow type var=Identifier stream=Identifier) -> input(varName={$var}, varType={$type.st})
  ;
  
streamstate
	: ^(Stream stream=Identifier) -> streamState(tmpNum={++counter})
	;

declaration
@init {
	VariableSymbol vs = null;
	int currentScopeNum = getCurrentScopeNum();
}
@after {
	// Variable Symbol won't have type information for now
	Type spec;
	if ($s.text == null) {
  	spec = new BuiltInTypeSymbol("var");
  }
  else {
  	spec = new BuiltInTypeSymbol($s.text);
  }
  
  vs = new VariableSymbol($id.text, null, spec);
  vs.scopeNum = currentScopeNum;
  
  currentscope.define(vs);
}
  : ^(DECL s=specifier? type* id=Identifier) -> outputEmptyDecl(sym={getLLVMvarSymbol(currentScopeNum)}, varName={$Identifier}, scopeNum={currentScopeNum}, varType={$type.st}, value={getEmptyValue($type.st.toString())})
  | ^(DECL s=specifier? type* ^(Assign id=Identifier expr)) -> outputDecl(sym={getLLVMvarSymbol(currentScopeNum)}, varName={$Identifier}, scopeNum={currentScopeNum}, varType={$type.st}, expr={$expr.st}, tmpNum={counter})
  | ^(DECL s=specifier StdInput ^(Assign id=Identifier StdInput))
  | ^(DECL s=specifier StdOutput ^(Assign id=Identifier StdOutput))
  ;

block
@init {
	scopeNum++;
	currentscope = new NestedScope("blockscope", currentscope, scopeNum);
}
@after {currentscope = currentscope.getEnclosingScope();}
  : ^(BLOCK d+=declaration* s+=statement*) -> returnTwo(a={$d}, b={$s})
  ;
  
procedure
@after {
	currentscope = currentscope.getEnclosingScope();
}
  : ^(Procedure Identifier paramlist ^(Returns type) block) -> declareProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$block.st}, retType={$type.st}, retNum={counter++})
  | ^(Procedure Identifier paramlist block) -> declareVoidProc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$block.st})
  ;
  
function
@after {
	// Function Symbol won't have type information with it
	FunctionSymbol fs = new FunctionSymbol($id.text, null);
	symtab.defineFunction(fs);
	currentscope = currentscope.getEnclosingScope();
}
  : ^(Function id=Identifier paramlist ^(Returns type) block) -> declareProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$block.st}, retType={$type.st}, retNum={counter++})
  | ^(Function id=Identifier paramlist ^(Returns type) ^(Assign expr)) -> declareProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$expr.st}, retType={$type.st}, retNum={counter++})
  ;
  
paramlist
@init {
	scopeNum++;
	currentscope = new NestedScope("paramscope", currentscope, scopeNum);
}
  : ^(PARAMLIST p+=parameter*) -> paramsep(params={$p})
  ;
  
parameter
@init {
	VariableSymbol vs = null;
	int currentScopeNum = getCurrentScopeNum();
}
@after {
	Type spec;
	if ($s.text == null) {
  	spec = new BuiltInTypeSymbol("const");
  }
  else {
  	spec = new BuiltInTypeSymbol($s.text);
  }
  
	// Variable Symbol won't have type information for now
  vs = new VariableSymbol($id.text, null, spec);
  vs.scopeNum = currentScopeNum;
  
  currentscope.define(vs);
}
  : ^(id=Identifier s=specifier? type) -> param(name={$Identifier}, type={$type.st}, scopeNum={currentScopeNum})
  ;
  
callStatement
  : ^(CALL Identifier ^(ARGLIST e+=expr*)) -> callProc(procName={$Identifier}, args={$e})
  ;
  
returnStatement
  : ^(Return expr) -> returnStat(expr={$expr.st}, tmpNum={counter}, type={$expr.stype})
  | Return -> returnVoid()
  ;
  
assignment
@init {
	VariableSymbol vs = null;
}
  : ^(Assign Identifier
  {
  	vs = (VariableSymbol) currentscope.resolve($Identifier.text);
  	if (currentscope instanceof NestedScope) {
  		NestedScope ns = (NestedScope)currentscope;
  		vs.scopeNum = ns.scopeNum;
  		}
  		else if (currentscope instanceof GlobalScope) {
  		GlobalScope gs = (GlobalScope)currentscope;
  		vs.scopeNum = gs.scopeNum;
  		}
  }
  expr) -> outputAssi(sym={getLLVMvarSymbol(vs.scopeNum)}, varName={$Identifier}, varType={$expr.stype}, scopeNum = {vs.scopeNum}, expr={$expr.st}, tmpNum={counter++})
  ;
  
ifstatement
@init {
	int result = 0;
}
  : ^(If expr {result = counter;} ifBody=slist ^(Else elseBody=slist)) -> ifElseStatement(condition={$expr.st}, body={$ifBody.st}, elseBody={$elseBody.st}, tmpNum={result})
  | ^(If expr {result = counter;} slist) -> ifStatement(condition={$expr.st}, body={$slist.st}, tmpNum={result})
  ;
  
loopstatement
@init {
	int result = 0;
	int currentLoop = 0;
}
  : ^(Loop ^(While expr) {result = counter; currentLoop = ++numLoop;} slist) -> whileLoop(condition={$expr.st}, body={$slist.st}, tmpNum={result}, loopNum={currentLoop})
  | ^(Loop {currentLoop = ++numLoop;} slist ^(While expr) {result = counter;}) -> doWhileLoop(condition={$expr.st}, body={$slist.st}, tmpNum={result}, loopNum={currentLoop})
  | ^(Loop {currentLoop = ++numLoop;} slist) -> infLoop(body={$slist.st}, tmpNum={counter}, loopNum={currentLoop})
  ;
  
slist
  : block -> return(a={$block.st})
  | statement -> return(a={$statement.st})
  | declaration -> return(a={$declaration.st})
  ;
  
type
  : Identifier -> return(a={"ID"})
  | Boolean -> return(a={BoolType})
  | Integer -> return(a={IntType})
  | Matrix
  | Interval
  | String
  | Vector
  | Real -> return(a={FloatType})
  | Character -> return(a={CharType})
  | StdInput
  | StdOutput
  | Null -> return(a={"Null"})
  | Identity -> return(a={"Identity"})
  | tuple
  ;
  
tuple
  : ^(Tuple type+)
  ;
  
specifier
  : Const
  | Var
  ;
  
expr returns [String stype, String resultVar]
@init {
	int tmpNum1 = 0;
	int tmpNum2 = 0;
	List<String> varNums = new ArrayList<String>();
	List<String> expressions = new ArrayList<String>();
	List<String> varTypes = new ArrayList<String>();
	VariableSymbol vs = null;
}
@after {
	$resultVar = ""+counter;
}
  : ^(Plus type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st.toString(), AddOp)}, type={$type.st}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(Minus type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString(); } 
    ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st.toString(), SubOp)}, type={$type.st}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(Multiply type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st.toString(), MulOp)}, type={$type.st}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(Divide type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st.toString(), DivOp)}, type={$type.st}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(Mod type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st.toString(), ModOp)}, type={$type.st}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(Exponent type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();}
    ->  exponent(expr1={$a.st}, expr2={$b.st}, type={$type.st}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(Equals type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    -> compare(expr1={$a.st}, expr2={$b.st}, comparison={getComp($a.stype)}, operator={getCompOp($a.stype, EqOp)}, type={$a.stype}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(NEquals type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    -> compare(expr1={$a.st}, expr2={$b.st}, comparison={getComp($a.stype)}, operator={getCompOp($a.stype, NeOp)}, type={$a.stype}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(GThan type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    -> compare(expr1={$a.st}, expr2={$b.st}, comparison={getComp($a.stype)}, operator={getCompOp($a.stype, GtOp)}, type={$a.stype}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(LThan type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    -> compare(expr1={$a.st}, expr2={$b.st}, comparison={getComp($a.stype)}, operator={getCompOp($a.stype, LtOp)}, type={$a.stype}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(GThanE type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    -> compare(expr1={$a.st}, expr2={$b.st}, comparison={getComp($a.stype)}, operator={getCompOp($a.stype, GteOp)}, type={$a.stype}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(LThanE type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    -> compare(expr1={$a.st}, expr2={$b.st}, comparison={getComp($a.stype)}, operator={getCompOp($a.stype, LteOp)}, type={$a.stype}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(Or type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    -> arithmetic(expr1={$a.st}, expr2={$b.st}, operator={OrOp}, type={$type.st}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(Xor type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    -> arithmetic(expr1={$a.st}, expr2={$b.st}, operator={XorOp}, type={$type.st}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(And type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();} 
    -> arithmetic(expr1={$a.st}, expr2={$b.st}, operator={AndOp}, type={$type.st}, tmpNum1={tmpNum1}, tmpNum2={tmpNum2}, result={++counter})
  | ^(Not type a=expr {tmpNum1 = counter;}) {$stype = $type.st.toString();} -> not(expr={$a.st}, type={$type.st}, tmpNum={tmpNum1}, result={++counter})
  | ^(By type a=expr {tmpNum1 = counter;} b=expr {tmpNum2 = counter;}) {$stype = $type.st.toString();}
  | ^(CALL type id=Identifier ^(ARGLIST (e=expr {varNums.add($e.resultVar); expressions.add($e.st.toString()); varTypes.add($e.stype);})*))
    -> {symtab.resolveFunction($id.text) != null}? callFunc(funcName={$Identifier}, retType={$type.st}, exprs={expressions}, varNames={varNums}, paramScope={getCurrentScopeNum()}, varTypes={varTypes}, result={++counter})
    -> callFunc()
  
  | ^(As type a=expr {tmpNum1 = counter;}) {$stype = $type.st.toString();} -> cast(func={getCastFunc($a.stype, $stype, tmpNum1, ++counter)}, expr={$a.st})
  | type Identifier
  {
  	$stype = $type.st.toString();
  	vs = (VariableSymbol) currentscope.resolve($Identifier.text);
  }
    -> {vs.isConst()}? load_const(tmpNum={++counter}, sym={getLLVMvarSymbol(vs.scopeNum)}, var={$Identifier}, scopeNum={vs.scopeNum}, varType={$type.st})
    -> load_var(tmpNum={++counter}, sym={getLLVMvarSymbol(vs.scopeNum)}, var={$Identifier}, scopeNum={vs.scopeNum}, varType={$type.st})
  | type Number {$stype = $type.st.toString();} -> load_num(tmpNum={++counter}, value={$Number}, varType={$type.st})
  | type FPNumber {$stype = $type.st.toString();} -> load_num(tmpNum={++counter}, value={"0x"+Long.toHexString((Double.doubleToLongBits(Float.parseFloat($FPNumber.toString()))))}, varType={$type.st})
  | type True {$stype = $type.st.toString();} -> load_bool(tmpNum={++counter}, value={"true"}, varType={$type.st})
  | type False {$stype = $type.st.toString();} -> load_bool(tmpNum={++counter}, value={"false"}, varType={$type.st})
  | type Null {$stype = $type.st.toString();}
  | type Identity {$stype = $type.st.toString();} 
  | type Char {$stype = $type.st.toString();} -> load_char(tmpNum={++counter}, value={getIntFromChar($Char.text)}, varType={$type.st})
  | ^(TUPLEEX type expr+)
  | type ^(Dot Identifier Number) {$stype = $type.st.toString();}
  | ^(NEG a=expr {tmpNum1 = counter;}) {$stype = $a.stype;} -> negative(tmpNum={tmpNum1}, expr={$a.st}, zero={getEmptyValue($a.stype)}, result={++counter}, type={$a.stype}, operator={getArithOp($a.stype, SubOp)})
  | ^(POS a=expr {tmpNum1 = counter;}) {$stype = $a.stype;} -> return(a={$a.st})
  | type streamstate {$stype = $type.st.toString();} -> return(a={$streamstate.st})
  ;
  
