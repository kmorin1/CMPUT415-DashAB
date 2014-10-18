package SymTab;

import java.util.ArrayList;
import java.util.List;

public class ProcedureSymbol extends Symbol{
	List<Symbol> params;
	public ProcedureSymbol(String name, Type type) {
		super(name, type);
	}
	public ProcedureSymbol(String name, Type type, List<Symbol> params) {
		super(name, type);
		if (params != null)
			this.params = params;
		else
			params = new ArrayList<Symbol>();
	}
	
	public List<Symbol> getParamList() {return this.params;}
}
