package SymTab;

import java.util.List;


/***
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
***/
public class Symbol { // A generic programming language symbol
    String name;      // All symbols at least have a name
    List<Type> types;
    List<Type> specs;
    public Symbol(String name) { this.name = name; }
    public Symbol(String name, List<Type> types) {this(name); this.types = types; }
    public Symbol(String name, List<Type> types, List<Type> specs) {
    	this(name); 
    	this.types = types;
    	this.specs = specs;
    }
    
    public String getName() { return name; }
    //public String getTypeName() { return type.toString(); }
    public List getTypes() { return types; }
    public List getSpecs() { return specs; }
    public Type getSpec(int index) {return specs.get(index);}
    public Type getType(int index) {return (Type) types.get(index);}
    
}