package SymTab;

import java.util.ArrayList;

public class VectorTypeSymbol extends BuiltInTypeSymbol {
	Type vectype;
	Integer vecsize;
	public VectorTypeSymbol(String name) {
		this(name, null, null);
	}
	public VectorTypeSymbol(String name, Type vectype) {
		this(name, vectype, null);
	}
	public VectorTypeSymbol(String name, Type vectype, Integer vecsize) {
		super(name);
		this.type = vectype;
		this.vecsize = vecsize;
	}
	public Type getVectorType() {return vectype;}
	public Integer getVectorSize() {return vecsize;}
}
