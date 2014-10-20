tree grammar TypeTranslate;

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
  : ^(PROGRAM statement*)
  ;
  
statement
  : declaration
  | typedef
  | outputstream
  | inputstream
  | assignment
  | ifstatement
  | loopstatement
  | block
  | procedure
  | function
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
  : ^(BLOCK statement+)
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
  : id=Identifier {$id.text.equals("Boolean")}? -> Boolean["boolean"]
  | id=Identifier {$id.text.equals("integer")}? -> Integer["integer"]
  | id=Identifier {$id.text.equals("matrix")}? -> Matrix["matrix"]
  | id=Identifier {$id.text.equals("interval")}? -> Interval["interval"]
  | id=Identifier {$id.text.equals("string")}? -> String["string"]
  | id=Identifier {$id.text.equals("vector")}? -> Vector["vector"]
  | id=Identifier {$id.text.equals("real")}? -> Real["real"]
  | id=Identifier {$id.text.equals("character")}? -> Character["character"]
 // | id=Identifier {$id.test.equals("tuple");} -> ^(Tuple $type+)
  | Identifier
  | Boolean
  | Integer
  | Matrix
  | Interval
  | String
  | Vector
  | Real
  | Character
  | tuple
  ;
  
tuple
  : ^(Tuple type+)
  ;

specifier
  : Const
  | Var
  ;
  
expr
  : ^(Plus type expr expr)
  | ^(Minus type expr expr)
  | ^(Multiply type expr expr)
  | ^(Divide type expr expr)
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
  | ^(By type expr expr)
  | ^(CALL Identifier ^(ARGLIST expr*))
  | type Identifier
  | type Number
  | type FPNumber
  ;
  
