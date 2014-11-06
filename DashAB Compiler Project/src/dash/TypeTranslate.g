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

declaration
  : ^(DECL specifier* type* Identifier) -> ^(DECL type* Identifier)
  | ^(DECL specifier* type* ^(Assign Identifier expr)) -> ^(DECL type* ^(Assign Identifier expr))
  | ^(DECL specifier* StdInput ^(Assign Identifier StdInput)) -> ^(DECL StdInput ^(Assign Identifier StdInput))
  | ^(DECL specifier* StdOutput ^(Assign Identifier StdOutput)) -> ^(DECL StdOutput ^(Assign Identifier StdOutput))
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
  : ^(Identifier specifier? type) -> ^(Identifier type)
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
  : id=Identifier 
  {
    $id.text.equals("boolean") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("boolean")
  }? -> Boolean["boolean"]
  | id=Identifier {
    $id.text.equals("integer") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("integer")
  }? -> Integer["integer"]
  | id=Identifier {
    $id.text.equals("matrix") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("matrix")
  }? -> Matrix["matrix"]
  | id=Identifier {
    $id.text.equals("interval") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("interval")
  }? -> Interval["interval"]
  | id=Identifier {
    $id.text.equals("string") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("string")
  }? -> String["string"]
  | id=Identifier {
    $id.text.equals("vector") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("vector")
  }? -> Vector["vector"]
  | id=Identifier {
    $id.text.equals("real") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("real")
  }? -> Real["real"]
  | id=Identifier {
    $id.text.equals("character") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("character")
  }? -> Character["character"]
  | id=Identifier {
    $id.text.equals("null") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("null")
  }? -> Null["null"]
  | id=Identifier {
    $id.text.equals("identity") ||
    symtab.resolveTDType($id.text).getSourceSymbol().getName().equals("identity")
  }? -> Identity["identity"]
  | ^(id=Identifier type+) {$id.text.equals("tuple")}? -> ^(Tuple["tuple"] type+)
  | Identifier
  | Boolean
  | Integer
  | Matrix
  | Interval
  | String
  | Vector
  | Real
  | Character
  | StdInput
  | StdOutput
  | Null
  | Identity
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
  | ^(As a=type expr)
  | type Identifier
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
  | type streamstate
  ;
  
