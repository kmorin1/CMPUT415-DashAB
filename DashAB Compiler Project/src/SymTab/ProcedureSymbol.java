package SymTab;

import java.util.ArrayList;
import java.util.List;

public class ProcedureSymbol extends Symbol{
	ArrayList<Symbol> params;
public ProcedureSymbol(String name, List<Type> type) {
		super(name, type);
	}
	public ProcedureSymbol(String name, List<Type> type, List<Symbol> params) {
		super(name, type);
		if (params != null)
			this.params = (ArrayList<Symbol>) params;
		else
			params = new ArrayList<Symbol>();
	}
	
	public ArrayList<Symbol> getParamList() {return this.params;}
}
