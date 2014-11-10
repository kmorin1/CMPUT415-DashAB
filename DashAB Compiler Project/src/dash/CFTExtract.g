tree grammar CFTExtract;

options {
  language = Java;
  tokenVocab = Syntax;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
}
 
@header
{
  package dash; 
  import java.util.ArrayList;
  import CFT.*;
}

@members {
  ArrayList<ControlFlowTree> cfts;
  //CFTNode currnode;
  public CFTExtract(TreeNodeStream input, ArrayList<ControlFlowTree> cfts) {
    this(input);
    this.cfts = cfts;
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
   
statement returns [CFTNode cftn]
  : assignment {$cftn = new CFTNode("generic", null);}
  | outputstream {$cftn = new CFTNode("generic", null);}
  | inputstream {$cftn = new CFTNode("generic", null);}
  | ifstatement {$cftn = $ifstatement.cftn;}
  | loopstatement {$cftn = $loopstatement.cftn;}
  | block {$cftn = $block.cftn;}
  | callStatement {$cftn = new CFTNode("generic", null);}
  | returnStatement {$cftn = new CFTNode("return", null);}
  | Break {$cftn = new CFTNode("generic", null);}
  | Continue {$cftn = new CFTNode("generic", null);}
  ;
  
outputstream
  : ^(RArrow expr Identifier)
  ;

inputstream
  : ^(LArrow Identifier Identifier)
  ;

declaration
  : ^(DECL specifier* type* Identifier)
  | ^(DECL specifier* type* ^(Assign Identifier expr))
  | ^(DECL specifier* ^(Assign Identifier StdInput))
  | ^(DECL specifier* ^(Assign Identifier StdOutput))
  ;
  
typedef
  : ^(Typedef type Identifier)
  ;

block returns [CFTNode cftn]
@init {
  CFTNode subroot = new CFTNode("subroot", null);
}
@after {
  $cftn = subroot;
}
  : ^(BLOCK declaration* (s=statement {subroot.addChildAtEnd($s.cftn);})*)
  ;
  
procedure
  : ^(Procedure id=Identifier paramlist ^(Returns type)
    block) {cfts.add(new ControlFlowTree($id.text, $block.cftn));}
  | ^(Procedure id=Identifier paramlist 
    block) {cfts.add(new ControlFlowTree($id.text, $block.cftn));}
  | ^(Procedure Identifier paramlist ^(Returns type))
  | ^(Procedure Identifier paramlist)
  ;
  
function
  : ^(Function id=Identifier paramlist ^(Returns type) 
    block) {cfts.add(new ControlFlowTree($id.text, $block.cftn));}
  | ^(Function Identifier paramlist ^(Returns type) ^(Assign expr))
  | ^(Function Identifier paramlist ^(Returns type))
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
  
ifstatement returns [CFTNode cftn]
@init {
  CFTNode ifnode = new CFTNode("ifnode", null);
}
  : ^(If expr s1=slist {ifnode.addChild($s1.cftn);} ^(Else s2=slist {ifnode.addChild($s2.cftn); $cftn = ifnode;})) 
  | ^(If expr slist) {$cftn = new CFTNode("generic", null);}
  ;
  
loopstatement returns [CFTNode cftn]
  : ^(Loop ^(While expr) slist) {$cftn = new CFTNode("generic", null);}
  | ^(Loop slist ^(While expr)) {$cftn = $slist.cftn;}
  | ^(Loop slist) {$cftn = $slist.cftn;}
  ;
  
slist returns [CFTNode cftn]
  : block {$cftn = $block.cftn;}
  | statement {$cftn = $statement.cftn;}
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
  | ^(Not expr)
  | ^(By expr expr)
  | ^(CALL Identifier ^(ARGLIST expr*))
  | Identifier
  | Number
  | FPNumber
  | True
  | False
  | Null
  | Identity
  | Char
  | ^(TUPLEEX expr)
  | ^(Dot Identifier)
  ;
  
