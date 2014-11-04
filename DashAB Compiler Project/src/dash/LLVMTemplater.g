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
    public LLVMTemplater(TreeNodeStream input, SymbolTable symtab) {
        this(input);
        this.symtab = symtab;
        currentscope = symtab.globals;
    }
  
  String IntType = "i32"; String CharType = "i8"; String BoolType = "i1"; String FloatType = "float";
  
  String AddOp = "add"; String SubOp = "sub"; String MulOp = "mul"; String DivOp = "div";
  
  String Cmp = "cmp";
  String EqOp = "eq"; String NeOp = "ne";
  String GtOp = "gt"; String LtOp = "lt";
  String GteOp = "ge"; String LteOp = "le";
  String XorOp = "xor"; String OrOp = "or"; String AndOp = "and";

  private String getArithOp(StringTemplate type, String operation) {
  	if (type.toString().equals(IntType)) {
  	  if (operation.equals(DivOp))
  	    return "s" + operation;
  	  return operation;
  	}
  	else {
  		return "f" + operation;
  	}
  }
  
  private String getCompOp(StringTemplate type, String operation) {
    if (type.toString().equals(IntType)) {
      return operation;
    }
    else {
      return "o" + operation;
    }
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
  | Break
  | Continue
  ;
   
outputstream
  : ^(RArrow expr stream=Identifier)
  ;

inputstream
  : ^(LArrow var=Identifier stream=Identifier)
  ;

declaration
  : ^(DECL type* Identifier)
  | ^(DECL type* ^(Assign Identifier expr)) -> outputAssi(varName={$Identifier}, varType={$type.st}, expr={$expr.st}, tmpNum={counter})
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
  : ^(Assign Identifier expr) -> outputAssi(varname={$Identifier}, varType={$expr.type}, expr={$expr.st}, tmpNum={counter++})
  ;
  
ifstatement
  : ^(If expr ifBody=slist ^(Else elseBody=slist)) -> ifElseStatement(condition={$expr.st}, body={$ifBody.st}, elseBody={$elseBody.st}, tmpNum={counter++})
  | ^(If expr slist) -> ifStatement(condition={$expr.st}, body={$slist.st}, tmpNum={counter++})
  ;
  
loopstatement
  : ^(Loop ^(While expr) slist) -> whileLoop(condition={$expr.st}, body={$slist.st}, tmpNum={counter++})
  | ^(Loop slist ^(While expr)) -> doWhileLoop(condition={$expr.st}, body={$slist.st}, tmpNum={counter++})
  | ^(Loop slist) -> infLoop(body={$slist.st}, tmpNum={counter++})
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
  
expr returns [String type]
  : ^(Plus type a=expr b=expr) ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st, AddOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(Minus type a=expr b=expr) ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st, SubOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(Multiply type a=expr b=expr) ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st, MulOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(Divide type a=expr b=expr) ->  arithmetic(expr1={$a.st}, expr2={$b.st}, operator={getArithOp($type.st, DivOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(Exponent type a=expr b=expr)
  | ^(Equals type a=expr b=expr) -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($type.st, EqOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(NEquals type a=expr b=expr) -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($type.st, NeOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(GThan type a=expr b=expr) -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($type.st, GtOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(LThan type a=expr b=expr) -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($type.st, LtOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(GThanE type a=expr b=expr) -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($type.st, GteOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(LThanE type a=expr b=expr) -> compare(expr1={$a.st}, expr2={$b.st}, comparison={Cmp}, operator={getCompOp($type.st, LteOp)}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(Or type a=expr b=expr) -> arithmetic(expr1={$a.st}, expr2={$b.st}, operator={OrOp}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(Xor type a=expr b=expr) -> arithmetic(expr1={$a.st}, expr2={$b.st}, operator={XorOp}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(And type a=expr b=expr) -> arithmetic(expr1={$a.st}, expr2={$b.st}, operator={AndOp}, type={$type.st}, tmpNum1={counter}, tmpNum2={counter-1}, result={++counter})
  | ^(Not type a=expr)
  | ^(By type a=expr b=expr)
  | ^(CALL Identifier ^(ARGLIST expr*))
  | ^(As type expr)
  | type Identifier -> load_var(tmpNum={++counter}, var={$Identifier}, varType={$type.st})
  | type Number -> load_num(tmpNum={++counter}, value={$Number}, varType={$type.st})
  | type FPNumber -> load_num(tmpNum={++counter}, value={$FPNumber}, varType={$type.st})
  | type True
  | type False
  | type Null
  | type Identity
  | type Char
  | ^(TUPLEEX type expr+)
  | type ^(Dot Identifier Number)
  | ^(NEG expr)
  | ^(POS expr)
  ;
  
