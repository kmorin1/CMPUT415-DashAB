package SymTab;

import java.util.ArrayList;

public class VectorTypeSymbol extends BuiltInTypeSymbol {
	Type vectype;
	Object vecsize;
	public VectorTypeSymbol(String name) {
		this(name, null, null);
	}
	public VectorTypeSymbol(String name, Type vectype) {
		this(name, vectype, null);
	}
	public VectorTypeSymbol(String name, Type vectype, Object vecsize) {
		super(name);
		this.vectype = vectype;
		this.vecsize = vecsize;
	}
	public Type getVectorType() {return vectype;}
	public Object getVectorSize() {return vecsize;}
}
