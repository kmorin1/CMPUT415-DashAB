package SymTab;

import java.util.List;


public class VariableSymbol extends Symbol {
	boolean isconst;
	public VariableSymbol(String name, List<Type> type) { 
		super(name, type); 
		isconst = false;
	}
	public VariableSymbol(String name, List<Type> type, List<Type> specs) {
		super(name, type, specs);
		
	}
	
	public boolean isConst() {return this.isconst;}
	//public void setConst(boolean con) {this.isconst = con;}
	//public String toString() {return super.getName() + " " + super.getType();}
}
