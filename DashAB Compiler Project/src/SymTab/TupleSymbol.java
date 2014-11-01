package SymTab;

import java.util.ArrayList;
import java.util.List;

public class TupleSymbol extends BuiltInTypeSymbol{
	ArrayList<FieldPair> fieldnames;
	public TupleSymbol(String name, List<FieldPair> fieldnames) {
		super(name);
		this.fieldnames = (ArrayList<FieldPair>) fieldnames;
	}
	public ArrayList<FieldPair> getFieldNames() {return fieldnames;}
}
