
public class RBNode {

	public int key;
	public Interval value;
	public RBNode leftChild;
	public RBNode rightChild;
	public RBNode parent;
	public int color;
	public RBNode left;
	public RBNode right;
	
	public RBNode(int k, Interval iv, RBNode nullNode) {
		leftChild = nullNode;
		rightChild = nullNode;
		parent = nullNode;
		key = k;
		value = iv;
		left = null;
		right = null;
	}
}
