package SymTab;

import java.util.List;


public class VariableSymbol extends Symbol {
	boolean isconst;
	boolean isvar;
	public VariableSymbol(String name, Type type) { 
		super(name, type); 
		isconst = false;
		isvar = false;
	}
	public VariableSymbol(String name, Type type, Type spec) {
		super(name, type, spec);
		if (spec != null && spec.getName().equals("const"))
			isconst = true;
		else 
			isconst = false;
		if (spec != null && spec.getName().equals("var"))
			isvar = true;
		else 
			isvar = false;
		if (spec == null) {
			isconst = false;
			isvar = false;
		}
	}
	
	public boolean isConst() {return this.isconst;}
	public boolean isVar() {return this.isvar;}
	//public void setConst(boolean con) {this.isconst = con;}
	//public String toString() {return super.getName() + " " + super.getType();}
}
