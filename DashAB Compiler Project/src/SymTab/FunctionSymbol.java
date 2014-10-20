package SymTab;

import java.util.ArrayList;
import java.util.List;


public class FunctionSymbol extends Symbol {
	List<Symbol> params;
	public FunctionSymbol(String name, List<Type> type) {
		super(name, type);
	}
	public FunctionSymbol(String name, List<Type> type, List<Symbol> params) {
		super(name, type);
		if (params != null)
			this.params = params;
		else
			params = new ArrayList<Symbol>();
	}
	
	public List<Symbol> getParamList() {return this.params;}
}
