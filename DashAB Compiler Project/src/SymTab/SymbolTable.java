package SymTab;

/***
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
***/
import java.util.*;
public class SymbolTable {
	
    public Scope globals;
    Map<String, Symbol> resnames;
    Map<String, BuiltInTypeSymbol> types;
    Map<String, TypeDefSymbol> tdtypes;
    Map<String, BuiltInTypeSymbol> specs;
    Map<String, FunctionSymbol> funcsyms;
	Map<String, ProcedureSymbol> procsyms;
    
    public SymbolTable() { 
    	this.globals = new GlobalScope();
    	this.resnames = new HashMap<String, Symbol>();
    	this.types = new HashMap<String, BuiltInTypeSymbol>();
    	this.funcsyms = new HashMap<String, FunctionSymbol>();
    	this.procsyms = new HashMap<String, ProcedureSymbol>();
    	this.specs = new HashMap<String, BuiltInTypeSymbol>();
    	this.tdtypes = new HashMap<String, TypeDefSymbol>();
    	initTypeSystem(); 
    }
    protected void initTypeSystem() {
    	defineType(new BuiltInTypeSymbol("boolean"));
    	defineType(new BuiltInTypeSymbol("character"));
        defineType(new BuiltInTypeSymbol("integer"));
        defineType(new BuiltInTypeSymbol("real"));
        defineType(new BuiltInTypeSymbol("tuple"));
        defineType(new BuiltInTypeSymbol("vector"));
        defineType(new BuiltInTypeSymbol("matrix"));
        defineType(new BuiltInTypeSymbol("interval"));
        defineType(new BuiltInTypeSymbol("string"));
        defineType(new BuiltInTypeSymbol("std_input"));
        defineType(new BuiltInTypeSymbol("std_output"));
        defineType(new BuiltInTypeSymbol("null"));
        defineType(new BuiltInTypeSymbol("identity"));
        defineRes("if");
        defineRes("function");
        defineRes("procedure");
        defineRes("main");
        defineRes("else");
        defineRes("loop");
        defineRes("typedef");
        defineRes("continue");
        defineRes("break");
        defineRes("return");
        defineRes("in");
        defineRes("by");
        defineRes("as");
        defineRes("while");
        //defineRes("matrix");
        //defineRes("vector");
        //defineRes("interval");
        defineRes("true");
        defineRes("false");
        //defineRes("string");
        defineRes("returns");
        defineRes("filter");
        defineRes("not");
        defineRes("and");
        defineRes("or");
        defineRes("xor");
        defineRes("rows");
        defineRes("columns");
        defineRes("length");
        defineRes("out");
        defineRes("inp");
        defineRes("stream_state");
        defineRes("reverse");
        
        ArrayList<Symbol> params = new ArrayList<Symbol>();
        params.add(new VariableSymbol("v", new VectorTypeSymbol("vector")));
        FunctionSymbol fs = new FunctionSymbol("length", new BuiltInTypeSymbol("integer"), params);
        fs.setDefined();
        defineFunction(fs);
        
        defineSpec(new BuiltInTypeSymbol("var"));
        defineSpec(new BuiltInTypeSymbol("const"));
        defineRes("var");
        defineRes("const");
    }
    Boolean[][] promotelookup = {
			{false, null, null, null, null, null},
			{null, false, true, null, null, null},
			{null, null, false, null, null, null},
			{null, null, null, false, null, null},
			{null, null, null, null, false, null},
			{null, null, null, null, null, false}
	};
    Boolean[][] expromotelookup = {
			{false, true, true, null, true, null},
			{true, false, true, null, true, null},
			{null, true, false, null, null, null},
			{null, null, null, false, null, null},
			{true, true, true, null, false, null},
			{null, null, null, null, null, false}
	};
    public BuiltInTypeSymbol getBuiltInSymbol(String name) {
        String bitname = name;
        String oldname;
        do {
          oldname = bitname;
          bitname = resolveTDType(bitname).getSourceSymbol().getName();
        } while (!bitname.equals("null"));
        return (BuiltInTypeSymbol) resolveType(oldname);
      }
    public Boolean lookup(Type tst1, Type tst2) {
    	Integer i1 = null;
    	Integer i2 = null;
    	String st1 = "";
    	String st2 = "";
    	try {
    		st1 = getBuiltInSymbol(tst1.getName()).getName();
    		st2 = getBuiltInSymbol(tst2.getName()).getName();
    	} catch (NullPointerException npe) {
    		throw new RuntimeException("undefined type error, use typedef to define user types");
    	}
    	if (st1.equals("boolean")) {
    	    i1 = 0;
    	}
    	else if (st1.equals("integer")) {
    		i1 = 1;
    	}
    	else if (st1.equals("real")) {
    		i1 = 2;
    	}
    	else if (st1.equals("interval")) {
    		i1 = 3;
    	}
    	else if (st1.equals("character")) {
    		i1 = 4;
    	}
    	else if (st1.equals("tuple")) {
    		i1 = 5;
    	}
    	
    	if (st2.equals("boolean")) {
            i2 = 0;
        }
    	else if (st2.equals("integer")) {
    		i2 = 1;
    	}
    	else if (st2.equals("real")) {
    		i2 = 2;
    	}
    	else if (st2.equals("interval")) {
    		i2 = 3;
    	}
    	else if (st2.equals("character")) {
    		i2 = 4;
    	}
    	else if (st2.equals("tuple")) {
    		i2 = 5;
    	}
    	
    	//System.out.println(tst1.getName() + " " + tst2.getName());
    	return promotelookup[i1][i2];
    	
    }
	public Boolean exLookup(Type tst1, Type tst2) {
		Integer i1 = null;
    	Integer i2 = null;
    	String st1 = getBuiltInSymbol(tst1.getName()).getName();
    	String st2 = getBuiltInSymbol(tst2.getName()).getName();
    	if (st1.equals("boolean")) {
            i1 = 0;
        }
        else if (st1.equals("integer")) {
                i1 = 1;
        }
        else if (st1.equals("real")) {
                i1 = 2;
        }
        else if (st1.equals("interval")) {
                i1 = 3;
        }
        else if (st1.equals("character")) {
                i1 = 4;
        }

    	if (st2.equals("boolean")) {
            i2 = 0;
        }
        else if (st2.equals("integer")) {
                i2 = 1;
        }
        else if (st2.equals("real")) {
                i2 = 2;
        }
        else if (st2.equals("interval")) {
                i2 = 3;
        }
        else if (st2.equals("character")) {
                i2 = 4;
        }
        else if (st2.equals("tuple")) {
                i2 = 5;
        }
    	
    	return expromotelookup[i2][i1];
	}
    
    private void defineRes(String sym) {resnames.put(sym, new Symbol(sym));}
    public String getScopeName() { return "global"; }
    public Scope getEnclosingScope() { return null; }
    public void define(Symbol sym) { globals.define(sym); }
    public void defineType(TypeDefSymbol sym) {
    	types.put(sym.getName(), sym);
    	tdtypes.put(sym.getName(), sym);
    	resnames.put(sym.getName(), sym);
    }
    protected void defineType(BuiltInTypeSymbol sym) {
    	types.put(sym.getName(), sym); 
    	resnames.put(sym.getName(), sym);
    }
    protected void defineSpec(BuiltInTypeSymbol sym) {
    	specs.put(sym.getName(), sym);
    }
    public void defineFunction(FunctionSymbol sym) {
    	funcsyms.put(sym.getName(), sym);
    }
    public void defineProcedure(ProcedureSymbol sym) {
    	procsyms.put(sym.getName(), sym);
    }
    public Symbol resolve(String name) { return globals.resolve(name); }
    public BuiltInTypeSymbol resolveSpec(String name) {return specs.get(name);}
    public Symbol resolveType(String name) {return types.get(name); }
    public TypeDefSymbol resolveTDType(String name) {
    	TypeDefSymbol tds = tdtypes.get(name); 
    	if (tds != null)
    		return tds;
    	return new TypeDefSymbol(new BuiltInTypeSymbol("null"), "null");
    }
    public FunctionSymbol resolveFunction(String name) {return funcsyms.get(name); }
    public ProcedureSymbol resolveProcedure(String name) {return procsyms.get(name); }
    
    public void allDefined() {
    	for (FunctionSymbol fs : funcsyms.values())
    		if (!fs.isDefined())
    			throw new RuntimeException("Function " + fs.getName() + " was declared but never defined");
    	for (ProcedureSymbol ps : procsyms.values())
    		if (!ps.isDefined())
    			throw new RuntimeException("Procedure " + ps.getName() + " was declared but never defined");

    }
}