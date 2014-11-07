package CFT;

public class ControlFlowTree {
	String name;
	CFTNode root;
	
	public ControlFlowTree(String name) {
		this(name, new CFTNode("root", null));
	}
	public ControlFlowTree(String name, CFTNode root) {
		this.name = name;
		this.root = root;
	}
	
	
	
}
