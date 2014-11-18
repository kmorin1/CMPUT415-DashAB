CMPUT415-DashAB
===============

```
Lyle: I'm going to use this file as a central location to share what I'm currently doing,
 what my plan is for the immediate future, and most importantly, what files I am actively editing
 to help avoid merge conflicts. (Plus helps keep my brain from wandering all over the place)
 
 Symbol Table:
	Currently sticking in what features I think will be needed for the project into the symbol table. 
	This probably will be a WIP over the course of the project. 
 Type Handling:
	Currently working on getting the types of all expressions inserted into the symbol table, with proper promoted form
	will add error checking after this works. 
	all typedefs are now removed, with their corresponding built in type inserted instead
Definition checking:
	first pass is in, should work more or less
	
TO-DO:
	-WISHLIST: get tuple type promotion working 
	-do all type checking for expressions, functions etc. (done?) 
	-run tests
		test scope for all blocks (if/loop/function etc)
		test all type combinations for errors. 
		test for correct error reports on implicit and explicit promotion
		WIP
	-type promotion for LLVM (almost done? uses explicit casting)
	-global variables in LLVM (might need to change parsing, and figure out constant folding)
	-calling procedures and functions in LLVM
	-most things tuple in LLVM
	-forward function or procedure declarations in LLVM
	-everything vector/matrix
	
To link an llvm file with our libruntime.c functions (only tested on lab machines so far):
	-use the Makefile to compile libruntime.c into libruntime.a
	-compile an llvm file test.llvm into test.llvm.o by command: llc-3.2 test.llvm -filetype=obj
	-link the .o and the .a by command: clang test.llvm.o libruntime.a -lm
	-run the resulting executable ./a.out
  
Active Files:
  All SymTab
  TypeExpand.g
  TypeTranslate.g
  LLVMTemplater.g

Craaaaazy ideas:
	-get vectors, matrices, and tuples working with all operations, and 'efficiently'
	
	
TO TEST OUR SUBMISSION (on lab machines):
	tar -xzvf DashAB_Part1.tar.gz
	make
	java DashAB_Part1_Test <dash_file> > <output_file>
	llc-3.2 <output_file> -filetype=obj
	clang <output_file>.o libruntime.a -lm
	./a.out
