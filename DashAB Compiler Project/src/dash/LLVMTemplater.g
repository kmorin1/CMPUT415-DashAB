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
  
  private String getAdd(StringTemplate type) {
  	if (type.equals("i32")) {
  		return "add";
  	}
  	else {
  		return "fadd";
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
  | ^(DECL type* ^(Assign Identifier expr)) -> outputAssi(varName={$Identifier}, varType={$type.st}, expr={$expr.st}, tmpNum={counter++})
  | ^(DECL StdInput ^(Assign Identifier StdInput))
  | ^(DECL StdOutput ^(Assign Identifier StdOutput))
  ;

block
  : ^(BLOCK d+=declaration* s+=statement*) -> returnTwo(a={$d}, b={$s})
  ;
  
procedure
  : ^(Procedure Identifier paramlist ^(Returns type) block) -> declareProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$block.st}, retType={$type.st}, retNum={counter++})
  | ^(Procedure Identifier paramlist block) -> declareVoidProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$block.st}, retType={"void"})
  ;
  
function
  : ^(Function Identifier paramlist ^(Returns type) block) -> declareProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$block.st}, retType={$type.st}, retNum={counter++})
  | ^(Function Identifier paramlist ^(Returns type) ^(Assign expr)) -> declareProcOrFunc(procName={$Identifier}, procVars={$paramlist.st}, procBody={$expr.st}, retType={$type.st}, retNum={counter++})
  ;
  
paramlist
  : ^(PARAMLIST p+=parameter*) -> return(a={$p})
  ;
  
parameter
  : ^(Identifier type) -> param(name={$Identifier}, type={$type.st})
  ;
  
callStatement
  : ^(CALL Identifier ^(ARGLIST e+=expr*)) -> callProc(procName={$Identifier}, args={$e})
  ;
  
returnStatement
  : ^(Return expr?) -> returnStat(val={$expr.st})
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
  | Boolean -> return(a={"i1"})
  | Integer -> return(a={"i32"})
  | Matrix
  | Interval
  | String
  | Vector
  | Real -> return(a={"float"})
  | Character -> return(a={"i8"})
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
  : ^(Plus type a=expr b=expr) ->  add(func={getAdd($type.st)}, expr1={$a.st}, expr2={$b.st}, tmpNum1={counter++}, tmpNum2={counter++}, result={counter++})
  | ^(Minus type a=expr b=expr)
  | ^(Multiply type a=expr b=expr)
  | ^(Divide type a=expr b=expr)
  | ^(Exponent type a=expr b=expr)
  | ^(Equals type a=expr b=expr)
  | ^(NEquals type a=expr b=expr)
  | ^(GThan type a=expr b=expr)
  | ^(LThan type a=expr b=expr)
  | ^(GThanE type a=expr b=expr)
  | ^(LThanE type a=expr b=expr)
  | ^(Or type a=expr b=expr)
  | ^(Xor type a=expr b=expr)
  | ^(And type a=expr b=expr)
  | ^(Not type a=expr)
  | ^(By type a=expr expr)
  | ^(CALL Identifier ^(ARGLIST expr*))
  | ^(As type expr)
  | type Identifier -> load_var(tmpNum={counter++}, var={$Identifier}, varType={$type.st})
  | type Number
  | type FPNumber
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
  
