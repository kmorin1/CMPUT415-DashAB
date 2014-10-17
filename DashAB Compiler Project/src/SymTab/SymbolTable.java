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
    
    public SymbolTable() { 
    	this.globals = new GlobalScope();
    	this.resnames = new HashMap<String, Symbol>();
    	this.types = new HashMap<String, BuiltInTypeSymbol>();
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
	
    private void defineRes(String sym) {resnames.put(sym, new Symbol(sym));}
    public String getScopeName() { return "global"; }
    public Scope getEnclosingScope() { return null; }
    public void define(Symbol sym) { globals.define(sym); }
    protected void defineType(BuiltInTypeSymbol sym) {
    	types.put(sym.getName(), sym); 
    	resnames.put(sym.getName(), sym);
    }
    public Symbol resolve(String name) { return globals.resolve(name); }
    public BuiltInTypeSymbol resolveType(String name) {return types.get(name); }
    public void defineType(TypeDefSymbol sym) {
    	types.put(sym.getName(), sym);
    	resnames.put(sym.getName(), sym);
    }
    
    
}