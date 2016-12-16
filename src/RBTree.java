
public class RBTree {

	public RBNode root;
	public RBNode nullNode;
	public int id;
	
	
	public RBTree() {
		id = 0;
		nullNode = new RBNode(-1, null,null);
		nullNode.leftChild = nullNode;
		nullNode.rightChild = nullNode;
		nullNode.parent = nullNode;
		nullNode.color = 1; // Black
		root = nullNode;
	}
	
	public void insert(RBNode z) {
		
		RBNode y = nullNode;
		RBNode x = root;
		// Find place to insert z
		while(x != nullNode) {
			y = x;
			if(z.key < x.key) {
				x = x.leftChild;
			}
			else {
				x = x.rightChild;
			}
		}
		z.parent = y;
		if(y == nullNode) {
			root = z;
		}
		else if(z.key < y.key) {
			y.leftChild = z;
		}
		else {
			y.rightChild = z;
		}
		z.color = 0; // Red
		insertFixup(z);
	}
	
	public void insertFixup(RBNode z) {
		
		//System.out.println("Fixup: "+z.key);
		
		while (z.parent.color == 0) {
			//System.out.println("Fixup: z="+z.key);
			//System.out.println("Parent: "+z.parent.key);
			if(z.parent == z.parent.parent.leftChild) {
				RBNode y = z.parent.parent.rightChild;
				if(y.color == 0) {
					z.parent.color = 1;
					y.color = 1;
					z.parent.parent.color = 0;
					z = z.parent.parent;
				}
				else {
					if(z == z.parent.rightChild) {
						z = z.parent;
						rotateLeft(z);
					}
					z.parent.color = 1;
					z.parent.parent.color = 0;
					rotateRight(z.parent.parent);
				}
			}
			else {
				RBNode y = z.parent.parent.leftChild;
				if(y.color == 0) {
					z.parent.color = 1;
					y.color = 1;
					z.parent.parent.color = 0;
					z = z.parent.parent;
				}
				else {
					if(z == z.parent.leftChild) {
						z = z.parent;
						rotateRight(z);
					}
					z.parent.color = 1;
					rotateLeft(z.parent.parent);

				}
			}
		}
		root.color = 1;
	}
	
	public void rotateLeft(RBNode x) {
		
		RBNode y = x.rightChild;
		x.rightChild = y.leftChild;
		if(y.leftChild != nullNode) {
			y.leftChild.parent = x;
		}
		y.parent = x.parent;
		if(x.parent == nullNode) {
			root = y;
		}
		else if(x == x.parent.leftChild) {
			x.parent.leftChild = y;
		}
		else {
			x.parent.rightChild = y;
		}
		y.leftChild = x;
		x.parent = y;
	}
	
	public void rotateRight(RBNode x) {
		
		RBNode y = x.leftChild;
		x.leftChild = y.rightChild;
		if(x.rightChild != nullNode) {
			x.rightChild.parent = y;
		}
		y.parent = x.parent;
		if(x.parent == nullNode) {
			root = y;
		}
		else if(x.parent.leftChild == x) {
			x.parent.leftChild = y;
		}
		else {
			x.parent.rightChild = y;
		}
		y.rightChild = x;
		x.parent = y;
	}
	
	// Returns node with key = y, y's predecessor, or null
	// in that order of preference.
	public RBNode predecessorOrEqual(int y) {
		
		RBNode ret = null;
		// Find y or parent to y.
		RBNode node = find(y);
		if(node.key <= y) {
			return node; // Found y or parent that was the predecessor to y
		}
		else {
			
			//System.out.println("Found larger parent "+node.key);
			
			// We found a parent greater than y.
			// If parent has a left child, then the predecessor
			// lies to the outer right in that subtree.
			if(node.leftChild != nullNode) {
				//System.out.println("Searching Left Subtree");
				node = node.leftChild;
				while(node.rightChild != nullNode) {
					node = node.rightChild;
				}
				return node;
			}
			else {
				
				// Predecessor lies on the path to the root
				// As long as we are the left child go upwards.
				ret = node.parent;
				while(ret != nullNode && 
						(ret.leftChild != nullNode && node == ret.leftChild) ){
					node = ret;
					ret = ret.parent;
				}				
			}
		}
		
		// We now either have the predecessor, or a node with a key
		// larger than y. Which would be the root.
		// Return predecessor or null.
		if(ret != null && ret != nullNode && ret.key < y) {
			return ret;
		}
		else {
			return null;
		}
	}
	
	// Finds y or parent
	public RBNode find(int y) {
		
		//System.out.println("Finding: "+y);
		RBNode node = root;
		while(node.key != y) {
			//System.out.println("Node: "+node.key);
			if(y < node.key) {
				// Check left
				if(node.leftChild != nullNode) {
					node = node.leftChild;
				}
				else {
					return node;
				}
			}
			else {
				if(node.rightChild != nullNode) {
					node = node.rightChild;
				}
				else {
					return node;
				}
			}
		}
		return node;
	}
	
	
}
