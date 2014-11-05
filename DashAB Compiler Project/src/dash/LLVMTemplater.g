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
    int scopeCounter = 0;
    int numLoop = 0;
    public LLVMTemplater(TreeNodeStream input, SymbolTable symtab) {
        this(input);
        this.symtab = symtab;
        currentscope = symtab.globals;
    }
  
  String IntType = "i32"; String CharType = "i8"; String BoolType = "i1"; String FloatType = "float";
  
  String AddOp = "add"; String SubOp = "sub"; String MulOp = "mul"; String DivOp = "div";
  
  String Cmp = "icmp";
  String EqOp = "eq"; String NeOp = "ne";
  String GtOp = "gt"; String LtOp = "lt";
  String GteOp = "ge"; String LteOp = "le";
  String XorOp = "xor"; String OrOp = "or"; String AndOp = "and";

  private String getArithOp(String type, String operation) {
  	if (type.toString().equals(IntType)) {
  	  if (operation.equals(DivOp))
  	    return "s" + operation;
  	  return operation;
  	}
  	else {
  		return "f" + operation;
  	}
  }
  
  private String getCompOp(String type, String operation) {
    if (type.toString().equals(IntType)) {
    	if (operation.equals("eq") || operation.equals("ne"))
    		return operation;
      return "s" + operation;
    }
    else {
      return "o" + operation;
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

declaration
  : ^(DECL type* Identifier) -> outputEmptyDecl(varName={$Identifier}, varType={$type.st}, value={getEmptyValue($type.st.toString())})
  | ^(DECL type* ^(Assign Identifier expr)) -> outputDecl(varName={$Identifier}, varType={$type.st}, expr={$expr.st}, tmpNum={counter})
  | ^(DECL StdInput ^(Assign Identifier StdInput))
  | ^(DECL StdOutput ^(Assign Identifier StdOutput))
  ;

block
  : ^(BLOCK d+=declaration* s+=statement*) -> returnTwo(a={$d}, b={$s})
  ;
  
procedure
  : ^(Procedure Identifier paramlist ^(Returns type) block) -> declareProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$block.st}, retType={$type.st}, retNum={counter++})
  | ^(Procedure Identifier paramlist block) -> declareVoidProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$block.st})
  ;
  
function
  : ^(Function Identifier paramlist ^(Returns type) block) -> declareProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$block.st}, retType={$type.st}, retNum={counter++})
  | ^(Function Identifier paramlist ^(Returns type) ^(Assign expr)) -> declareProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$expr.st}, retType={$type.st}, retNum={counter++})
  ;
  
paramlist
  : ^(PARAMLIST p+=parameter*) -> paramsep(params={$p})
  ;
  
parameter
  : ^(Identifier type) -> param(name={$Identifier}, type={$type.st})
  ;
  
callStatement
  : ^(CALL Identifier ^(ARGLIST e+=expr*)) -> callProc(procName={$Identifier}, args={$e})
  ;
  
returnStatement
  : ^(Return expr) -> return(a={$expr.st})
  | Return
  ;
  
assignment
  : ^(Assign Identifier expr) -> outputAssi(varName={$Identifier}, varType={$expr.stype}, expr={$expr.st}, tmpNum={counter++})
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
  
expr returns [String stype]
  : ^(Plus type a=expr b=expr) {$stype = $type.st.toString();} ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st.toString(), AddOp)}, type={$type.st}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(Minus type a=expr b=expr) {$stype = $type.st.toString();} ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st.toString(), SubOp)}, type={$type.st}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(Multiply type a=expr b=expr) {$stype = $type.st.toString();} ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st.toString(), MulOp)}, type={$type.st}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(Divide type a=expr b=expr) {$stype = $type.st.toString();} ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st.toString(), DivOp)}, type={$type.st}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(Exponent type a=expr b=expr) {$stype = $type.st.toString();}
  | ^(Equals type a=expr b=expr) {$stype = $type.st.toString();} -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($a.stype, EqOp)}, type={$a.stype}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(NEquals type a=expr b=expr) {$stype = $type.st.toString();} -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($a.stype, NeOp)}, type={$a.stype}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(GThan type a=expr b=expr) {$stype = $type.st.toString();} -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($a.stype, GtOp)}, type={$a.stype}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(LThan type a=expr b=expr) {$stype = $type.st.toString();} -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($a.stype, LtOp)}, type={$a.stype}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(GThanE type a=expr b=expr) {$stype = $type.st.toString();} -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($a.stype, GteOp)}, type={$a.stype}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(LThanE type a=expr b=expr) {$stype = $type.st.toString();} -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($a.stype, LteOp)}, type={$a.stype}, tmpNum1={counter-1}, tmpNum2={counter}, result={++counter})
  | ^(Or type a=expr b=expr) {$stype = $type.st.toString();} -> arithmetic(expr1={$a.st}, expr2={$b.st}, operator={OrOp}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(Xor type a=expr b=expr) {$stype = $type.st.toString();} -> arithmetic(expr1={$a.st}, expr2={$b.st}, operator={XorOp}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(And type a=expr b=expr) {$stype = $type.st.toString();} -> arithmetic(expr1={$a.st}, expr2={$b.st}, operator={AndOp}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(Not type a=expr) {$stype = $type.st.toString();}
  | ^(By type a=expr b=expr) {$stype = $type.st.toString();}
  | ^(CALL Identifier ^(ARGLIST expr*))
  | ^(As type expr) {$stype = $type.st.toString();}
  | type Identifier {$stype = $type.st.toString();} -> load_var(tmpNum={++counter}, var={$Identifier}, varType={$type.st})
  | type Number {$stype = $type.st.toString();} -> load_num(tmpNum={++counter}, value={$Number}, varType={$type.st})
  | type FPNumber {$stype = $type.st.toString();} -> load_num(tmpNum={++counter}, value={$FPNumber}, varType={$type.st})
  | type True {$stype = $type.st.toString();} -> load_bool(tmpNum={++counter}, value={"true"}, varType={$type.st})
  | type False {$stype = $type.st.toString();} -> load_bool(tmpNum={++counter}, value={"false"}, varType={$type.st})
  | type Null {$stype = $type.st.toString();}
  | type Identity {$stype = $type.st.toString();} 
  | type Char {$stype = $type.st.toString();} -> load_char(tmpNum={++counter}, value={getIntFromChar($Char.text)}, varType={$type.st})
  | ^(TUPLEEX type expr+)
  | type ^(Dot Identifier Number) {$stype = $type.st.toString();}
  | ^(NEG a=expr) {$stype = $a.stype;} -> negative(tmpNum={counter}, expr={$a.st}, result={++counter}, type={$a.stype}, operator={getArithOp($a.stype, SubOp)})
  | ^(POS a=expr) {$stype = $a.stype;} -> return(a={$a.st})
  ;
  
