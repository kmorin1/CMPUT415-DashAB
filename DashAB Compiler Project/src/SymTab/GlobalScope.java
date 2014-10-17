package SymTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GlobalScope implements Scope {
	Map<String, Symbol> symbols = new HashMap<String, Symbol>();
    protected ArrayList<Scope> children = new ArrayList<Scope>();
	
    // Satisfy Scope interface
    public void addScopeChild(Scope scope) {children.add(scope);}
    public String getScopeName() { return "global"; }
    public Scope getEnclosingScope() { return null; }
    public void define(Symbol sym) { symbols.put(sym.name, sym); }
    public Symbol resolve(String name) { return symbols.get(name); }
    
    
}
