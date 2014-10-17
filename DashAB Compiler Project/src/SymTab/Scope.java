package SymTab;

import java.util.ArrayList;

public interface Scope {
	public void addScopeChild(Scope scope);
	public String getScopeName(); // do I have a name?
	public Scope getEnclosingScope(); // am I nested in another?
	public void define(Symbol sym); // define sym in this scope
	public Symbol resolve(String name); // look up name in scope
}