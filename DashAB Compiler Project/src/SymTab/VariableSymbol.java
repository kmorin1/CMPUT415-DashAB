package SymTab;

public class VariableSymbol extends Symbol {
	boolean isconst;
	public VariableSymbol(String name, Type type) { 
		super(name, type); 
		isconst = false;
	}
	public VariableSymbol(String name, Type type, boolean isconst) {
		super(name, type);
		this.isconst = isconst;
	}
	
	public boolean isConst() {return this.isconst;}
	//public void setConst(boolean con) {this.isconst = con;}
	public String toString() {return super.getName() + " " + super.getType();}
}
