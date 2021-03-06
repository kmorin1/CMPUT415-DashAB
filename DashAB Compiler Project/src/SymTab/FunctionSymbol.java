package SymTab;

import java.util.ArrayList;
import java.util.List;


public class FunctionSymbol extends Symbol {
	ArrayList<Symbol> params;
	Boolean isdefined = false;
	public FunctionSymbol(String name, Type type) {
		super(name, type);
	}
	public FunctionSymbol(String name, Type type, List<Symbol> params) {
		super(name, type);
		if (params != null)
			this.params = (ArrayList<Symbol>) params;
		else
			params = new ArrayList<Symbol>();
	}
	
	public ArrayList<Symbol> getParamList() {return this.params;}
	public Boolean isDefined() {return isdefined;}
	public void setDefined() {isdefined = true;}
}
