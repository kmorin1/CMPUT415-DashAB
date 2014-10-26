tree grammar BaseTreeGrammar;

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
  : ^(DECL specifier* type* Identifier)
  | ^(DECL specifier* type* ^(Assign Identifier expr))
  ;
  
typedef
  : ^(Typedef type Identifier)
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
  | Identifier
  ;
  
tuple
  : ^(Tuple type+)
  ;

specifier
  : Const
  | Var
  ;
  
expr
  : ^(Plus expr expr)
  | ^(Minus expr expr)
  | ^(Multiply expr expr)
  | ^(Divide expr expr)
  | ^(Exponent expr expr)
  | ^(Equals expr expr)
  | ^(NEquals expr expr)
  | ^(GThan expr expr)
  | ^(LThan expr expr)
  | ^(GThanE expr expr)
  | ^(LThanE expr expr)
  | ^(Or expr expr)
  | ^(Xor expr expr)
  | ^(And expr expr)
  | ^(By expr expr)
  | ^(CALL Identifier ^(ARGLIST expr*))
  | Identifier
  | Number
  | FPNumber
  ;
  
