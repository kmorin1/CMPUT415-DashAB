package SymTab;

import java.util.ArrayList;

public class VectorTypeSymbol extends BuiltInTypeSymbol {
	Type vectype;
	Object typetree;
	Object vecsize;
	public VectorTypeSymbol(String name) {
		this(name, null, null);
	}
	public VectorTypeSymbol(String name, Type vectype, Object typetree) {
		this(name, vectype, typetree, null);
	}
	public VectorTypeSymbol(String name, Type vectype, Object typetree, Object vecsize) {
		super(name);
		this.vectype = vectype;
		this.typetree = typetree;
		this.vecsize = vecsize;
	}
	public Type getVectorType() {return vectype;}
	public Object getTypeTree() {return typetree;}
	public Object getVectorSize() {return vecsize;}
}
