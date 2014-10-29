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
	-poke a little more at implicit type promotion
	to make sure in line with spec. 
	-get tuple type promotion and translation working 
	-do all type checking for expressions, functions etc. 
	-run tests
		test scope for all blocks (if/loop/function etc)
		test all type combinations for errors. 
		test for correct error reports on implicit and explicit promotion
		WIP
  
Active Files:
  All SymTab
  TypeExpand.g
  TypeTranslate.g
