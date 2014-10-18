grammar Syntax;

options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
}

tokens {
  GENERATOR;
  DECL;
  PROGRAM;
  BLOCK;
}

@header
{
  package dash;
}

@lexer::header
{
  package dash;
}

@members
{
  @Override
  protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RuntimeException {
    throw new RuntimeException("Mismatched Token");
  }
  
  @Override
  public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
    String hdr = getErrorHeader(e);
    String msg = getErrorMessage(e, tokenNames);
    throw new RuntimeException(hdr + ":" + msg);
  }
}


program
  : mainblock EOF -> ^(PROGRAM mainblock)
  ;
  
mainblock
  : statement*
  ;
  
statement
  : declaration
  | assignment
  | ifstatement
  | loopstatement
  | block
  ;
  
stream
  : Var Out Equals 'std_output()' SemiColon
  | Var Inp Equals 'std_input()' SemiColon
  ;

outputstream
  : expr RArrow Out SemiColon -> ^(RArrow expr)
  ;

inputstream
  : Identifier LArrow Inp SemiColon -> ^(LArrow Identifier)
  ;

streamstate
  : Stream LParen Inp RParen SemiColon
  ;

declaration
  : specifier* type+ Identifier SemiColon -> ^(DECL specifier* type+ Identifier)
  | specifier* type+ Identifier Assign expr SemiColon -> ^(DECL specifier* type+ ^(Assign Identifier expr))
  ;

block
  : LBrace statement+ RBrace -> ^(BLOCK statement+)
  ;
  
assignment
  : Identifier Assign expr SemiColon -> ^(Assign Identifier expr)
  ;
  
ifstatement
  : If expr slist Else slist -> ^(If expr slist ^(Else slist))
  | If expr slist -> ^(If expr slist)
  ;
  
loopstatement
  : Loop While expr slist -> ^(Loop ^(While expr) slist)
  | Loop slist While expr -> ^(Loop slist ^(While expr))
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
  ;

specifier
  : Const
  | Var
  ;

expr
  : vectorOperation
  | orExpr
  ;
  
orExpr
  : xorExpr ((Bar Bar)^ xorExpr)*
  ;
  
xorExpr
  : andExpr ((Xor | Or)^ andExpr)*
  ;
  
andExpr
  : equExpr ((And)^ equExpr)*
  ;
  
equExpr
  : relExpr ((Equals | NEquals)^ relExpr)*
  ;

relExpr
  : byExpr ((LThan | GThan | LThanE | GThanE)^ byExpr)* 
  ;
  
byExpr
  : addExpr ((By)^ addExpr)*
  ;
  
addExpr
  : mulExpr ((Plus | Minus)^ mulExpr)*
  ;
  
mulExpr
  : powExpr ((Multiply | Divide | Mod)^ powExpr)*
  ;

powExpr
  : unaryExpr ((Exponent)^ unaryExpr)*
  ;
  
unaryExpr
  : Plus rangeExpr
  | Minus rangeExpr
  | Not rangeExpr
  | rangeExpr
  ;
  
rangeExpr
  : indexExpr (Range^ indexExpr)?  
  ;
  
indexExpr
  : atom (index^)?
  ;

index
  : (LBracket expr)=> LBracket expr RBracket
  | LBracket atom RBracket
  ;   
  
atom
  : Number
  | Identifier
  | filter
  | generator
  | LParen expr RParen
  ;

filter
  : Filter LParen Identifier In vector=expr Bar condition=expr RParen -> ^(Filter Identifier $vector $condition)        
  ;
  
generator
  : LBracket Identifier In vector=expr Bar apply=expr RBracket -> ^(GENERATOR Identifier $vector $apply)   
  ;

vectorOperation
  : 'length' LParen Identifier RParen
//  | expr Bar Bar expr
//  | expr Multiply Multiply expr
//  | expr By expr
  ;
  
// DashAB Keywords
In        : 'in';
By        : 'by';
As        : 'as';
Var       : 'var';
Const     : 'const';
Matrix    : 'matrix';
Vector    : 'vector';
Interval  : 'interval';
Integer   : 'integer';
Boolean   : 'boolean';
True      : 'true';
False     : 'false';
Real      : 'real';
String    : 'string';
Procedure : 'procedure';
Function  : 'function';
Returns   : 'returns';
Typedef   : 'typedef';
If        : 'if';
Else      : 'else';
Loop      : 'loop';
While     : 'while';
Break     : 'break';
Continue  : 'continue';
Return    : 'return';
Filter    : 'filter';
Not       : 'not';
And       : 'and';
Or        : 'or';
Xor       : 'xor';
Rows      : 'rows';
Columns   : 'columns';
Length    : 'length';
Out       : 'out';
Inp       : 'inp';
Tuple     : 'tuple';
Stream    : 'stream_state';
Reverse   :  'reverse';

Character : 'character';

// Extra Characters
Comment   : '//';
LBComment : '/*';
RBComment : '*/';
LArrow    : '<-';
RArrow    : '->';
Plus      : '+';
Minus     : '-';
Multiply  : '*';
Divide    : '/';
Mod       : '%';
Exponent  : '^';
Equals    : '==';
NEquals   : '!=';
GThan     : '>';
GThanE    : '>=';
LThan     : '<';
LThanE    : '<=';
LParen    : '(';
RParen    : ')';
LBracket  : '[';
RBracket  : ']';
LBrace    : '{';
RBrace    : '}';
Assign    : '=';
SemiColon : ';';
Range     : '..';
Bar       : '|';

Number 
  : Digit+
  ;

Identifier
  : ('A'..'Z' | 'a'..'z' | '_') ('A'..'Z'| 'a'..'z'| Digit)*
  ;

fragment Digit
  : '0'..'9'
  ;
MULTILINE_COMMENT : '/*' .* '*/' {$channel = HIDDEN;} ;
COMMENT : '//' .* ('\n'|'\r') {$channel = HIDDEN;};
Space
  : (' ' 
  | '\t' 
  | '\n'
  | '\r') {$channel = HIDDEN;}
  ;