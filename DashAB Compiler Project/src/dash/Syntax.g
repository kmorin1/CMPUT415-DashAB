grammar Syntax;

options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
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
  : mainblock EOF -> mainblock
  ;
  
mainblock
  : 
  ;
  
comment
  : LBComment .* RBComment
  | Comment .* Newline
  ;
  
stream
  : Var Out Equals 'std_output()' SemiColon
  | Var Inp Equals 'std_input()' SemiColon
  ;

outputstream
  : expr RArrow Out SemiColon
  ;

inputstream
  : Identifier LArrow Inp SemiColon
  ;

streamstate
  : Stream LParen Inp RParen SemiColon
  ;

declaration
  : specifier* type Identifier SemiColon
  | specifier* type Identifier Equals expr SemiColon
  ;

type
  : Boolean
  | Integer
  ;

specifier
  : Const
  ;

expr
  :
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

// Extra Characters
Comment   : '//';
LBComment : '/*';
RBComment : '*/';
LArrow    : '<-';
RArrow    : '->';
Add       : '+';
Subtract  : '-';
Multiply  : '*';
Divide    : '/';
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
  
Space
  : (' ' 
  | '\t' 
  | '\r') {$channel = HIDDEN;}
  ;
  
Newline
  : '\n' 
  ;
