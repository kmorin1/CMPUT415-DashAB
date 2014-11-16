package CFT;

public class ControlFlowTree {
	String name;
	CFTNode root;
	Boolean hasreturns;
	
	public ControlFlowTree(String name) {
		this(name, new CFTNode("root", null));
	}
	public ControlFlowTree(String name, CFTNode root) {
		this(name, root, true);
	}
	public ControlFlowTree(String name, CFTNode root, boolean hasreturns) {
		this.name = name;
		this.root = root;
		this.hasreturns = hasreturns;
		this.root.collapse();
	}
	
	public CFTNode getRoot() {return root;}
	
	public void returnScan() {
		try {
			if (!root.returnScan() && hasreturns)
				throw new RuntimeException("requires a return statement on all execution paths");
		} catch (RuntimeException re) {
			throw new RuntimeException(name + " " + re.getMessage());
		}
	}
	
	public String toString() {
		String digraph = "digraph {\n" +
				"ordering=out;\n" +
				"ranksep=.4;\n" +
				"bgcolor=\"lightgrey\"; node [shape=box, fixedsize=false, fontsize=12, fontname=\"Helvetica-bold\", fontcolor=\"blue\"" +
				"\nwidth=.25, height=.25, color=\"black\", fillcolor=\"white\", style=\"filled, solid, bold\"];\n" +
				"edge [arrowsize=.5, color=\"black\", style=\"bold\"]\n";
	
		digraph = digraph + root.getSubTreeString();
		return digraph + "}";
	}
	
}
