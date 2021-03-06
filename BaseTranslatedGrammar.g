tree grammar BaseTranslatedGrammar;

options {
  language = Java;
  output = AST;
  tokenVocab = Syntax;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
}
 
@header
{
  package dash; 
}

@members {
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
  : ^(RArrow expr Identifier)
  ;

inputstream
  : ^(LArrow Identifier Identifier)
  ;

declaration
  : ^(DECL type* Identifier)
  | ^(DECL type* ^(Assign Identifier expr))
  | ^(DECL ^(Assign Identifier StdInput))
  | ^(DECL ^(Assign Identifier StdOutput))
  ;
  
block
  : ^(BLOCK declaration* statement*)
  ;
  
procedure
  : ^(Procedure Identifier paramlist ^(Returns type) block)
  | ^(Procedure Identifier paramlist block)
  ;
  
function
  : ^(Function Identifier paramlist ^(Returns type) block)
  | ^(Function Identifier paramlist ^(Returns type) ^(Assign expr))
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
  | declaration
  ;
  
type
  : Boolean
  | Integer
  | Matrix
  | Interval
  | String
  | Vector
  | Real
  | Character
  | tuple
  | StdInput
  | StdOutput
  | Null
  | Identity
  ;
  
tuple
  : ^(Tuple type+)
  ;
  
expr
  : ^(Plus type expr expr)
  | ^(Minus type expr expr)
  | ^(Multiply type expr expr)
  | ^(Divide type expr expr)
  | ^(Mod type expr expr)
  | ^(Exponent type expr expr)
  | ^(Equals type expr expr)
  | ^(NEquals type expr expr)
  | ^(GThan type expr expr)
  | ^(LThan type expr expr)
  | ^(GThanE type expr expr)
  | ^(LThanE type expr expr)
  | ^(Or type expr expr)
  | ^(Xor type expr expr)
  | ^(And type expr expr)
  | ^(Not type expr)
  | ^(By type expr expr)
  | ^(CALL type Identifier ^(ARGLIST expr*))
  | ^(As type expr)
  | type Identifier
  | type Number
  | type FPNumber
  | type True
  | type False
  | type Null
  | type Identity
  | type Char
  | ^(TUPLEEX expr+)
  | type ^(Dot Identifier Number)
  | ^(NEG expr)
  | ^(POS expr)
  ;
  
