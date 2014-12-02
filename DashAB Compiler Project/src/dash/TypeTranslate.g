tree grammar TypeTranslate;

options {
  language = Java;
  output = AST;
  tokenVocab = Syntax;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
}

tokens {
  VOID;
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
    public TypeTranslate(TreeNodeStream input, SymbolTable symtab, String inputfile) {
        this(input);
        this.symtab = symtab;
        currentscope = symtab.globals;
        this.inputfile = inputfile;
    }
    private String getErrorHeader() {
      CommonTree tree = (CommonTree) input.LT(1);
      int line = tree.getLine();
      int chline = tree.getCharPositionInLine();
      return getGrammarFileName() + "> In " + inputfile + ", " + line + ":" + chline + ": ";
  }
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
  : ^(RArrow expr stream=Identifier)
  ;

inputstream
  : ^(LArrow varType=type var=Identifier stream=Identifier)
  ;
  
streamstate
  : ^(Stream stream=Identifier)
  ;
  
length
	: ^(Length expr)
	;
 
declaration
  : ^(DECL specifier? type? Identifier) -> ^(DECL specifier? type? Identifier)
  | ^(DECL specifier? type? ^(Assign Identifier expr)) -> ^(DECL specifier? type? ^(Assign Identifier expr))
  | ^(DECL specifier StdInput ^(Assign Identifier StdInput)) -> ^(DECL specifier StdInput ^(Assign Identifier StdInput))
  | ^(DECL specifier StdOutput ^(Assign Identifier StdOutput)) -> ^(DECL specifier StdOutput ^(Assign Identifier StdOutput))
  ;
   
typedef
  : ^(Typedef type Identifier) ->
  ;

block
  : ^(BLOCK declaration* statement*)
  ;
  
procedure
  : ^(Procedure Identifier paramlist ^(Returns type) block?)
  | ^(Procedure Identifier paramlist block?)
  ;
  
function
  : ^(Function Identifier paramlist ^(Returns type) block?)
  | ^(Function Identifier paramlist ^(Returns type) ^(Assign expr))
  ;
  
paramlist
  : ^(PARAMLIST parameter*)
  ;
  
parameter
  : ^(Identifier specifier? type) -> ^(Identifier specifier? type)
  ;
  
callStatement
  : ^(CALL type Identifier ^(ARGLIST expr*))
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
  
type returns [String tsym, String scalarType]
  : id=Identifier 
  {
    $id.text.equals("boolean") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("boolean")
  }? {$tsym = "boolean";} -> Boolean["boolean"]
  | id=Identifier {
    $id.text.equals("integer") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("integer")
  }? {$tsym = "integer";}-> Integer["integer"]
  | id=Identifier {
    $id.text.equals("matrix") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("matrix")
  }? {$tsym = "matrix";} -> Matrix["matrix"]
  | id=Identifier {
    $id.text.equals("interval") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("interval")
  }? {$tsym = "interval";} -> Interval["interval"]
  | id=Identifier {
    $id.text.equals("string") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("string")
  }? {$tsym = "string";} -> String["string"]
  | id=Identifier {
    $id.text.equals("vector") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("vector")
  }? {$tsym = "vector";} -> Vector["vector"]
  | id=Identifier {
    $id.text.equals("real") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("real")
  }? {$tsym = "real";} -> Real["real"]
  | id=Identifier {
    $id.text.equals("character") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("character")
  }? {$tsym = "character";} -> Character["character"]
  | id=Identifier {
    $id.text.equals("void") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("void")
  }? {$tsym = "void";} -> VOID["void"]
  | id=Identifier {
    $id.text.equals("null") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("null")
  }? {$tsym = "null";} -> Null["null"]
  | id=Identifier {
    $id.text.equals("identity") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("identity")
  }? {$tsym = "identity";} -> Identity["identity"]
  | ^(id=Identifier type+) {$id.text.equals("tuple")}? {$tsym = "tuple";} -> ^(Tuple["tuple"] type+)
  | Identifier
  | Boolean {$tsym = "boolean";}
  | Integer {$tsym = "integer";}
  | Matrix {$tsym = "matrix";}
  | ^(Interval scalar=type) {$tsym = "interval"; $scalarType = $scalar.tsym;}
  | String {$tsym = "string";}
  | ^(Vector scalar=type? size?) {$tsym = "vector"; $scalarType = $scalar.tsym;}
  | Real {$tsym = "real";}
  | Character {$tsym = "character";}
  | StdInput
  | StdOutput
  | Null {$tsym = "null";}
  | Identity {$tsym = "identity";}
  | tuple {$tsym = "tuple";}
  ;
  
size
  : '*'
  | expr
  ;
  
tuple
  : ^(Tuple type+)
  ;

specifier
  : Const
  | Var
  ;
  
expr returns [String stype, String scalarType]
  : ^(Plus type {$stype = $type.tsym;} a=expr b=expr)     -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(Plus type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(Plus type $a ^(As Real $b))
                                   -> ^(Plus type $a $b)
  | ^(Minus type {$stype = $type.tsym;} a=expr b=expr)    -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(Minus type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(Minus type $a ^(As Real $b))
                                   -> ^(Minus type $a $b) 
  | ^(Multiply type {$stype = $type.tsym;} a=expr b=expr) -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(Multiply type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(Multiply type $a ^(As Real $b))
                                   -> ^(Multiply type $a $b)
  | ^(Divide type {$stype = $type.tsym;} a=expr b=expr)   -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(Divide type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(Divide type $a ^(As Real $b))
                                   -> ^(Divide type $a $b)
  | ^(Mod type {$stype = $type.tsym;} a=expr b=expr)      -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(Mod type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(Mod type $a ^(As Real $b))
                                   -> ^(Mod type $a $b) 
  | ^(Exponent type {$stype = $type.tsym;} a=expr b=expr) -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(Exponent type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(Exponent type $a ^(As Real $b))
                                   -> ^(Exponent type $a $b)
  | ^(Product type expr expr)
  | ^(Concat type expr expr)
  | ^(Equals type {$stype = $type.tsym;} a=expr b=expr)   -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(Equals type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(Equals type $a ^(As Real $b))
                                   -> ^(Equals type $a $b)
  | ^(NEquals type {$stype = $type.tsym;} a=expr b=expr)  -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(NEquals type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(NEquals type $a ^(As Real $b))
                                   -> ^(NEquals type $a $b)
  | ^(GThan type {$stype = $type.tsym;} a=expr b=expr)    -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(GThan type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(GThan type $a ^(As Real $b))
                                   -> ^(GThan type $a $b) 
  | ^(LThan type {$stype = $type.tsym;} a=expr b=expr)    -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(LThan type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(LThan type $a ^(As Real $b))
                                   -> ^(LThan type $a $b) 
  | ^(GThanE type {$stype = $type.tsym;} a=expr b=expr)   -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(GThanE type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(GThanE type $a ^(As Real $b))
                                   -> ^(GThanE type $a $b)
  | ^(LThanE type {$stype = $type.tsym;} a=expr b=expr)   -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(LThanE type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(LThanE type $a ^(As Real $b))
                                   -> ^(LThanE type $a $b) 
  | ^(Or type {$stype = $type.tsym;} a=expr b=expr)       -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(Or type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(Or type $a ^(As Real $b))
                                   -> ^(Or type $a $b)
  | ^(Xor type {$stype = $type.tsym;} a=expr b=expr)      -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(Xor type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(Xor type $a ^(As Real $b))
                                   -> ^(Xor type $a $b) 
  | ^(And type {$stype = $type.tsym;} a=expr b=expr)      -> {$a.stype.equals("integer") && $b.stype.equals("real")}? ^(And type ^(As Real $a) $b)
                                   -> {$a.stype.equals("real") && $b.stype.equals("integer")}? ^(And type $a ^(As Real $b))
                                   -> ^(And type $a $b)
  | ^(Not type {$stype = $type.tsym;} expr)
  | ^(By type {$stype = $type.tsym;} expr expr)
  | ^(CALL type {$stype = $type.tsym;} Identifier ^(ARGLIST expr*))
  | ^(As type {$stype = $type.tsym;} expr)
  | type {$stype = $type.tsym;} Identifier
  | type {$stype = $type.tsym;} Number
  | type {$stype = $type.tsym;} FPNumber
  | type {$stype = $type.tsym;} True 
  | type {$stype = $type.tsym;} False
  | type {$stype = $type.tsym;} Null
  | type {$stype = $type.tsym;} Identity
  | type {$stype = $type.tsym;} Char
  | ^(TUPLEEX type {$stype = $type.tsym;} expr+)
  | type {$stype = $type.tsym;} ^(Dot Identifier Number)
  | ^(NEG a=expr) {$stype = $a.stype;}
  | ^(POS a=expr) {$stype = $a.stype;}
  | type {$stype = $type.tsym;} streamstate
  | type {$stype = $type.tsym;} length
  | ^(VCONST type expr+) {$stype = "vector"; $scalarType = $type.scalarType;}
  | ^(Range type expr expr) {$stype = "interval"; $scalarType = $type.scalarType;}
  | ^(Filter Identifier expr expr) 
  | ^(GENERATOR Identifier expr expr)
  | ^(GENERATOR ^(ROW Identifer expr) ^(COLUMN Identifier expr) expr)    
  ;
  
