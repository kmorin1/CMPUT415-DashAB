tree grammar BackEndTemplate;

options {
  language = Java;
  output = template;
  tokenVocab = BackEnd;
  ASTLabelType = CommonTree;
}

@members {
  int countLabel = 0; int countLoop = 0; int progLine = 0;
  int opcounter = 1; int tempcounter = 0;
}
//declara
//  : ^(PROGRAM (x+=declaration)* (y+=nullblock)*) -> program(a={$x})
//  ;
//  
//nullblock
//  : ^(LINE assignment)
// // | ^(LINE print)
//  //| ^(LINE ifstatement)
//  //| ^(LINE loop)
//  ;
// 


walk
  : ^(PROGRAM (x+=declaration)* (y+=block)*) -> program2(a={$x},b={$y})
  ;
  
declaration
  : ^(DECL INTEGER x=ID y+=expr)  -> declareInt(a={$y}, id={$x}, op={opcounter})
  ;
  
storeVariable
  : ^(DECL INTEGER x=ID y+=expr) -> storeVar(a={$y},id={$x},op={opcounter})
  ;

block
  : ^(LINE ^(PRINT x+=expr)) {tempcounter++;} -> printStatement(a={$x}, tc={tempcounter},op={opcounter})
  | ^(LINE ^(IF x+=expr y+=block*)) {countLabel = countLabel + 2; tempcounter++;} -> bne(tc={tempcounter},op={opcounter},exp={$x},lines={$y},label={countLabel-1},nextlabel={countLabel})
  | ^(LINE ^(LOOP x+=expr y+=block*)) {countLoop++;} -> looper(exp={$x},lines={$y},label={countLoop})
  | ^(LINE ^(ASSIGN '=' t=ID y+=expr)) -> storeVar(a={$y},id={$t},op={opcounter})
  ;
  
assignment
  : ^(ASSIGN '=' x=ID y+=expr) -> storeVar(a={$y},id={$x},op={opcounter})
  ;
  
print
  : ^(PRINT x+=expr) {tempcounter++;} -> printStatement(a={$x}, tc={tempcounter},op={opcounter})
  ;
  
ifstatement
  : ^(IF expr block*) 
  ;
  
loop
  : ^(LOOP expr block*)
  ;
    
expr 
  : ^('==' x=expr y=expr) {opcounter++; tempcounter++;} -> eql(a={$x.st},b={$y.st},op1={opcounter-2},op2={opcounter-1},tc={tempcounter},dest={opcounter})
  | ^('!=' x=expr y=expr) {opcounter++; tempcounter++;} -> neq(a={$x.st},b={$y.st},op1={opcounter-2},op2={opcounter-1},tc={tempcounter},dest={opcounter})
  | ^('>' x=expr y=expr) {opcounter++; tempcounter++;} -> sgt(a={$x.st},b={$y.st},op1={opcounter-2},op2={opcounter-1},tc={tempcounter},dest={opcounter})
  | ^('<' x=expr y=expr) {opcounter++; tempcounter++;} -> slt(a={$x.st},b={$y.st},op1={opcounter-2},op2={opcounter-1},tc={tempcounter},dest={opcounter})
  | ^('+' x=expr y=expr) {opcounter++;} -> add(a={$x.st},b={$y.st},op1={opcounter-2},op2={opcounter-1},dest={opcounter})
  | ^('-' x=expr y=expr) {opcounter++;} -> sub(a={$x.st},b={$y.st},op1={opcounter-2},op2={opcounter-1},dest={opcounter})
  | ^('*' x=expr y=expr) {opcounter++;} -> mult(a={$x.st},b={$y.st},op1={opcounter-2},op2={opcounter-1},dest={opcounter})
  | ^('/' x=expr y=expr) {opcounter++;} -> div(a={$x.st},b={$y.st},op1={opcounter-2},op2={opcounter-1},dest={opcounter})
  | INT {opcounter++; tempcounter++;} -> int(v={$INT.text},oc={opcounter},tc={tempcounter})
  | ID {opcounter++;} -> var(id={$ID.text}, oc={opcounter})
  ;