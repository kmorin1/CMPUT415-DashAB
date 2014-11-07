package CFT;

import java.util.ArrayList;

public class CFTNode {
	private String name;
	private ArrayList<CFTNode> children;
	
	public CFTNode(String name, ArrayList<CFTNode> children) {
		this.name = name;
		if (children != null)
			this.children = children;
		else
			this.children = new ArrayList<CFTNode>();
	}
	
	public ArrayList<CFTNode> getChildren() {return this.children;}
	public void addChild(CFTNode child) {children.add(child);}
	public void addChildAtEnd(CFTNode child) {
		CFTNode curr = this;
		while (curr.getChildren().size() != 0) 
			curr = curr.getChildren().get(0);
		curr.addChild(child);
	}
}
