package SymTab;

public class TypeDefSymbol extends BuiltInTypeSymbol {
	BuiltInTypeSymbol sym;
	public TypeDefSymbol(BuiltInTypeSymbol bit, String name) {
		super(name);
		this.sym = bit;
	}
	public BuiltInTypeSymbol getSourceSymbol() {
		return sym;
	}
}
