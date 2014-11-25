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
  PARAMLIST;
  CALL;
  ARGLIST;
  TUPLEEX;
  POS;
  NEG;
  VCONST;
  ROW;
  COLUMN;
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
  : globalStatement*
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
  | Break SemiColon!
  | Continue SemiColon!
  ;
  
streamDecl
  : specifier Identifier Assign (StdOutput|StdInput) LParen RParen SemiColon
  -> ^(DECL specifier ^(Assign Identifier StdOutput? StdInput?))
  ;

outputstream
  : expr RArrow stream=Identifier SemiColon -> ^(RArrow expr $stream)
  ;

inputstream
  : var=Identifier LArrow stream=Identifier SemiColon -> ^(LArrow $var $stream)
  ;

streamstate
  : Stream LParen Identifier RParen -> ^(Stream Identifier)
  ;

declaration
  : specifier? type+ Identifier SemiColon -> ^(DECL specifier? type+ Identifier)
  | specifier type* Identifier SemiColon -> ^(DECL specifier type* Identifier)
  | specifier? type+ Identifier Assign expr SemiColon -> ^(DECL specifier? type+ ^(Assign Identifier expr))
  | specifier type* Identifier Assign expr SemiColon -> ^(DECL specifier type* ^(Assign Identifier expr))
  | specifier? type? 'vector'? Identifier LBracket size RBracket SemiColon 
    -> {$type.text.equals("vector")}? ^(DECL specifier? ^(Vector size))
    -> ^(DECL specifier? ^(Vector type size))
  | specifier? type? 'vector'? Identifier LBracket size RBracket Assign a=expr SemiColon
    -> {$type.text.equals("vector")}? ^(DECL specifier? ^(Vector size) ^(Assign Identifier $a))
    -> ^(DECL specifier? ^(Vector type size) ^(Assign Identifier $a))
  | specifier? type? 'matrix'? Identifier LBracket rowsize=size Comma columnsize=size RBracket SemiColon
    -> {$type.text.equals("matrix")}? ^(DECL specifier? ^(Matrix $rowsize $columnsize)) 
    -> ^(DECL specifier? ^(Matrix type $rowsize $columnsize))  
  | specifier? type? 'matrix'? Identifier LBracket rowsize=size Comma columnsize=size RBracket  Assign a=expr SemiColon
    -> {$type.text.equals("matrix")}? ^(DECL specifier? ^(Matrix $rowsize $columnsize) ^(Assign Identifier $a))
    -> ^(DECL specifier? ^(Matrix type $rowsize $columnsize) ^(Assign Identifier $a))
  | streamDecl
  ;
  
size
  : '*'
  | expr
  ;
  
typedef
  : Typedef type Identifier SemiColon -> ^(Typedef type Identifier)
  ;

block
  : LBrace declaration* statement* RBrace -> ^(BLOCK declaration* statement*)
  ;
  
procedure
  : Procedure Identifier LParen paramlist RParen (Returns type)? (SemiColon | block)
  -> ^(Procedure Identifier paramlist ^(Returns type)? block?)
  ;
  
function
  : Function Identifier LParen paramlist RParen Returns type ((Assign expr)? SemiColon | block)
  -> ^(Function Identifier paramlist ^(Returns type) ^(Assign expr)? block?)
  ;
  
paramlist
  : parameter? (Comma parameter)* -> ^(PARAMLIST parameter*)
  ;
  
parameter
  : specifier? type Identifier^
  ;
  
callStatement
  : Call Identifier LParen expr? (Comma expr)* RParen SemiColon -> ^(CALL Identifier ^(ARGLIST expr*))
  ;
  
returnStatement
  : Return^ expr? SemiColon!
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
  | Loop slist (While expr)? -> ^(Loop slist ^(While expr)?)
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
  | Identifier
  ;
  
tuple
  : Tuple^ LParen! type Identifier? (Comma! type Identifier?)+ RParen!
  ;

specifier
  : Const
  | Var
  ;

expr
  : concatExpr
  ;
  
concatExpr
  : (x=xorExpr -> $x) ((Concat) y=concatExpr -> ^(Concat $x $y))*
  ;
  
xorExpr
  : andExpr ((Xor | Or)^ andExpr)*
  ;
  
andExpr
  : equExpr (And^ equExpr)*
  ;
  
equExpr
  : relExpr ((Equals | NEquals)^ relExpr)*
  ;

relExpr
  : byExpr ((LThan | GThan | LThanE | GThanE)^ byExpr)* 
  ;
  
byExpr
  : addExpr (By^ addExpr)*
  ;
  
addExpr
  : mulExpr ((Plus | Minus)^ mulExpr)*
  ;
  
mulExpr
  : powExpr ((Multiply | Divide | Mod)^ powExpr)*
  ;

powExpr
  : (unaryExpr -> unaryExpr) ((Exponent) y=unaryExpr -> ^(Exponent $powExpr $y))*
  ;
  
unaryExpr
  : Plus x=unaryExpr -> ^(POS $x)
  | Minus x=unaryExpr -> ^(NEG $x)
  | Not x=unaryExpr -> ^(Not $x)
  | rangeExpr
  ;
  
rangeExpr
  : indexExpr (Range^ indexExpr)? 
  ;
  
indexExpr
  : atom (index)?
  ;

index
  : (LBracket expr)=> LBracket! expr RBracket!
  | LBracket! atom RBracket!
  ;   
  
atom
  : Number
  | FPNumber
  | True
  | False
  | Null
  | Identity
  | Char
  | Identifier Dot^ (Identifier|Number)
  | LParen (a=expr -> expr) (Comma b=expr -> ^(TUPLEEX $a $b))+ RParen
  | streamstate
  | Identifier LParen expr? (Comma expr)* RParen -> ^(CALL Identifier ^(ARGLIST expr*))
  | Identifier
  | filter
  | generator
  | LParen expr RParen -> expr
  | As LThan type GThan LParen expr RParen -> ^(As type expr)
  | vectorconst -> ^(VCONST vectorconst)
  | 'length' LParen Identifier RParen
  ;
  
vectorconst
  : LBracket! expr (Comma! expr)* RBracket!
  ;

filter
  : Filter LParen Identifier In vector=expr Bar condition=expr (Comma condition2+=expr)* RParen 
    -> ^(Filter Identifier $vector $condition $condition2*)        
  ;
  
generator
  : LBracket Identifier In vector=expr Bar apply=expr RBracket -> ^(GENERATOR Identifier $vector $apply)
  | LBracket id1=Identifier In e1=expr Comma id2=Identifier In e2=expr Bar apply=expr RBracket 
    -> ^(GENERATOR ^(ROW $id1 $e1) ^(COLUMN $id2 $e2) $apply)    
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
Tuple     : 'tuple';
Stream    : 'stream_state';
Reverse   : 'reverse';
Call      : 'call';
StdInput  : 'std_input';
StdOutput : 'std_output';
Null      : 'null';
Identity  : 'identity';
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
Comma     : ',';
Range     : '..';
Concat    : '||';
Bar       : '|';

fragment Dot       
  : '.'
  ;
fragment Digit
  : '0'..'9'
  ;
fragment Exp
  : ('e' | 'E') '_'* Digit+ '_'*
  | ('e' | 'E') '_'* (Minus|Plus) '_'* Digit+ '_'*
  ;

Identifier
  : ('A'..'Z' | 'a'..'z' | '_') ('A'..'Z'| 'a'..'z'| '_' | Digit)*
  ;

Number 
  : Digit (Digit|'_')*
  ;
  
FPNumber
  : (Digit|'_')* 
      ( (Range)=>      {$type=Number;}
      | (Dot (Digit|'_'|Exp)+)=> Dot (Digit|'_')* Exp? 
// {input.LA(1) == '.' && input.LA(2) != '.'}? => Dot (Digit|'_')* Exp?       
      | Exp
      )
  ;

Char
  : '\'' ('A'..'Z'|'a'..'z'|'0'..'9'|' '|'!'|'#'|'$'|'%'|'&'|'('|')'|
          '*'|'+'|','|'-'|'.'|'/'|':'|';'|'<'|'='|'>'|'?'|'@'|'['|']'|
          '^'|'_'|'`'|'{'|'|'|'}'|'~') '\''
  | '\'\\' ('a'|'b'|'n'|'r'|'t'|'\\'|'\''|'\"'|'0') '\''
  ;
  
MULTILINE_COMMENT : '/*' .* '*/' {$channel = HIDDEN;} ;
COMMENT : '//' .* ('\n'|'\r') {$channel = HIDDEN;};

Space
  : (' ' 
  | '\t' 
  | '\n'
  | '\r') {$channel = HIDDEN;}
  ;
