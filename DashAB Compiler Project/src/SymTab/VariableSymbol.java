package SymTab;

import java.util.List;


public class VariableSymbol extends Symbol {
        boolean isconst;
        boolean isvar;
        public int scopeNum;
        public VariableSymbol(String name, List<Type> type) { 
                super(name, type); 
                isconst = false;
                isvar = false;
                scopeNum = 0;
        }
        public VariableSymbol(String name, List<Type> type, List<Type> specs) {
                super(name, type, specs);
                isconst = false;
                isvar = false;
                scopeNum = 0;
                for (int i=0; i<specs.size(); i++) {
                        //System.out.println(specs.get(i));
                        if (specs.get(i).getName().equals("const")) 
                                isconst = true;
                        if (specs.get(i).getName().equals("var"))
                                isvar = true;
                }
        }
        
        public boolean isConst() {return this.isconst;}
        public boolean isVar() {return this.isvar;}
        //public void setConst(boolean con) {this.isconst = con;}
        //public String toString() {return super.getName() + " " + super.getType();}
}
