package CFT;

import java.util.ArrayList;

public class CFTNode {
	private String name;
	private CFTNode parent;
	private ArrayList<CFTNode> children;
	
	public CFTNode(String name, ArrayList<CFTNode> children) {
		this.name = name;
		if (children != null)
			this.children = children;
		else
			this.children = new ArrayList<CFTNode>();
		for (int i=0; i<this.children.size(); i++) 
			this.children.get(i).parent = this;
	}
	
	public String getName() {return name;}
	public CFTNode getParent() {return parent;}
	public ArrayList<CFTNode> getChildren() {return this.children;}
	public CFTNode getChild(Integer index) {return getChildren().get(index);}
	public CFTNode removeChildAtEnd() {
		CFTNode prev = this;
		CFTNode curr = prev.getChild(0);
		while (curr.getChildren().size() != 0) {
			prev = curr;
			curr = curr.getChild(0);
		}
		prev.getChildren().remove(0);
		return this;
	}
	public void addChild(CFTNode child) {
		child.parent = this;
		children.add(0, child);
	}
	public void addChildAtEnd(CFTNode child) {
		CFTNode curr = this;
		while (curr.getChildren().size() != 0) 
			curr = curr.getChildren().get(0);
		curr.addChild(child);
	}
	protected String getSubTreeString() {
		String ret = "";
		if (this.getChildren().size() == 0)
			return ret;
		for (int i=0; i<this.getChildren().size(); i++)
			ret = ret + "\"" + this.getName() + "\"" + " -> " + "\"" + this.getChild(i).getName() + "\"" + "\n";
		for (int i=0; i<this.getChildren().size(); i++)
			ret = ret + this.getChild(i).getSubTreeString();
		return ret;
	}
	
	protected void collapse() {
		if (this.getName().startsWith("ifnode")) {
			CFTNode cont = getContinuation();
			if (cont != null) {
				cont.parent.getChildren().remove(cont);
				this.addChild(cont);
			}
		}
		for (int i=0; i<this.getChildren().size(); i++)
			this.getChild(i).collapse();
	}
	
	private CFTNode getContinuation() {
		CFTNode last = this.getLast();
		if (last.getName().startsWith("endblock")) {
			last.parent.getChildren().clear();
			return null;
		}
		while (!last.parent.getName().startsWith("endblock"))
			last = last.parent;
		
		return last;
	}
	public CFTNode getLast() {
		if (this.getChildren().size() == 0)
			return this;
		else
			return this.getChild(0).getLast();
	}
	protected Boolean returnScan() {
		if (this.getName().startsWith("return")) {
			return true;
		}
		if (this.getName().startsWith("ifnode")) {
			Boolean b = this.getChild(this.getChildren().size()-2).returnScan() && this.getChild(this.getChildren().size()-1).returnScan();
			/*if (b && this.hasContinuation())
				throw new RuntimeException("has dead code after tautology in if block");
			*/
			if (b)
				return true;
			else if (this.hasContinuation())
				return this.getChild(0).returnScan();
			else 
				return false;
		} else {
			if (this.getChildren().size() != 0)
				return this.getChild(0).returnScan();
		}
		
		return false;
	}
	private Boolean hasContinuation() {
		if (this.getChildren().size() == 3)
			return true;
		else
			return false;
	}
}
