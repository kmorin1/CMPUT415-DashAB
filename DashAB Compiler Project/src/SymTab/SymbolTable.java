package SymTab;

/***
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
***/
import java.util.*;
public class SymbolTable {
	
    public Scope globals;
    Map<String, Symbol> resnames;
    Map<String, BuiltInTypeSymbol> types;
    Map<String, BuiltInTypeSymbol> specs;
    Map<String, FunctionSymbol> funcsyms;
	Map<String, ProcedureSymbol> procsyms;
    
    public SymbolTable() { 
    	this.globals = new GlobalScope();
    	this.resnames = new HashMap<String, Symbol>();
    	this.types = new HashMap<String, BuiltInTypeSymbol>();
    	this.funcsyms = new HashMap<String, FunctionSymbol>();
    	this.procsyms = new HashMap<String, ProcedureSymbol>();
    	this.specs = new HashMap<String, BuiltInTypeSymbol>();
    	initTypeSystem(); 
    }
    protected void initTypeSystem() {
    	defineType(new BuiltInTypeSymbol("boolean"));
    	defineType(new BuiltInTypeSymbol("character"));
        defineType(new BuiltInTypeSymbol("integer"));
        defineType(new BuiltInTypeSymbol("real"));
        defineType(new BuiltInTypeSymbol("tuple"));
        defineType(new BuiltInTypeSymbol("vector"));
        defineType(new BuiltInTypeSymbol("matrix"));
        defineType(new BuiltInTypeSymbol("interval"));
        defineType(new BuiltInTypeSymbol("string"));
        defineRes("if");
        defineRes("function");
        defineRes("procedure");
        defineRes("main");
        defineRes("else");
        defineRes("loop");
        defineRes("typedef");
        defineRes("continue");
        defineRes("break");
        defineRes("return");
        defineRes("in");
        defineRes("by");
        defineRes("as");
        defineRes("while");
        //defineRes("matrix");
        //defineRes("vector");
        //defineRes("interval");
        defineRes("true");
        defineRes("false");
        //defineRes("string");
        defineRes("returns");
        defineRes("filter");
        defineRes("not");
        defineRes("and");
        defineRes("or");
        defineRes("xor");
        defineRes("rows");
        defineRes("columns");
        defineRes("length");
        defineRes("out");
        defineRes("inp");
        defineRes("stream_state");
        defineRes("reverse");
        
        defineRes("var");
        defineRes("const");
    }
    Boolean[][] promotelookup = {
			{false, true, true, null, true},
			{true, false, true, null, true},
			{null, null, false, null, null},
			{null, null, null, false, null},
			{true, true, true, null, false}
	};
    public Boolean lookup(String st1, String st2) {
    	Integer i1 = null;
    	Integer i2 = null;
    	switch (st1) {
    	case "boolean":
    		i1 = 0;
    		break;
    	case "integer":
    		i1 = 1;
    		break;
    	case "real":
    		i1 = 2;
    		break;
    	case "interval":
    		i1 = 3;
    		break;
    	case "character":
    		i1 = 4;
    		break;
    	}
    	switch (st2) {
    	case "boolean":
    		i2 = 0;
    		break;
    	case "integer":
    		i2 = 1;
    		break;
    	case "real":
    		i2 = 2;
    		break;
    	case "interval":
    		i2 = 3;
    		break;
    	case "character":
    		i2 = 4;
    		break;
    	}
    	
    	return promotelookup[i1][i2];
    }
	
    private void defineRes(String sym) {resnames.put(sym, new Symbol(sym));}
    public String getScopeName() { return "global"; }
    public Scope getEnclosingScope() { return null; }
    public void define(Symbol sym) { globals.define(sym); }
    public void defineType(TypeDefSymbol sym) {
    	types.put(sym.getName(), sym);
    	resnames.put(sym.getName(), sym);
    }
    protected void defineType(BuiltInTypeSymbol sym) {
    	types.put(sym.getName(), sym); 
    	resnames.put(sym.getName(), sym);
    }
    public void defineFunction(FunctionSymbol sym) {
    	funcsyms.put(sym.getName(), sym);
    }
    public void defineProcedure(ProcedureSymbol sym) {
    	procsyms.put(sym.getName(), sym);
    }
    public Symbol resolve(String name) { return globals.resolve(name); }
    public BuiltInTypeSymbol resolveSpec(String name) {return specs.get(name);}
    public BuiltInTypeSymbol resolveType(String name) {return types.get(name); }
    public FunctionSymbol resolveFunction(String name) {return funcsyms.get(name); }
    public ProcedureSymbol resolveProcedure(String name) {return procsyms.get(name); }
    
}