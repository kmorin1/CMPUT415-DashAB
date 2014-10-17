package SymTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NestedScope implements Scope{
	String name;
	Map<String, Symbol> symbols = new HashMap<String, Symbol>();
	Scope enclosingscope;
	protected ArrayList<Scope> children = new ArrayList<Scope>();
	
	public NestedScope(String name, Scope enclosingscope) {
		this.enclosingscope = enclosingscope;
		this.name = name;
		enclosingscope.addScopeChild(this);
	}
	
	public void addScopeChild(Scope scope) {children.add(scope);}
	public String getScopeName() { return this.name; }
    public Scope getEnclosingScope() { return enclosingscope; }
    public void define(Symbol sym) { symbols.put(sym.name, sym); }
    public Symbol resolve(String name) {
    	Symbol s = symbols.get(name);
    	Scope currscope = this;
    	while (s == null && currscope.getEnclosingScope() != null) {
    		currscope = currscope.getEnclosingScope();
    		s = currscope.resolve(name);
    	}
    	return s; 
    }
}
