package SymTab;

public class VariableSymbol extends Symbol {
	public VariableSymbol(String name, Type type) { 
		super(name, type); 
	}
	
	
	public String toString() {return super.getName() + " " + super.getType();}
}
