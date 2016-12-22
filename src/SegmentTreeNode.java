import java.io.Serializable;
import java.util.ArrayList;


public class SegmentTreeNode implements Serializable {

	public SegmentTreeNode leftChild;
	public SegmentTreeNode rightChild;
	public RBTree entries;
	public int a;
	public int b;
	int counter;
	public RBNode leftmostInList;
	public int smallestYStored;
	public int largestYStored;
	
	public SegmentTreeNode(int left, int right) {
		a = left;
		b = right;
		leftChild = null;
		rightChild = null;
		entries = new RBTree();
		counter = 0;
	}
	
	public void insert(Interval interval) {
		// If the nodes interval is inside the interval of the Interval
		// add the Interval to the node.
		
		//System.out.println("Node a="+a+" b="+b);
		//System.out.println("Interval a="+interval.a +" b="+interval.b);
		
		if(interval.a <= a && interval.b >= b){
			counter++;
			RBNode newNode = new RBNode(interval.y, interval, entries.nullNode);
			// Find where to insert it in the linked list
			if(counter > 1) {
				//System.out.println("Finding predecessor to "+interval.y);
				RBNode node = entries.predecessorOrEqual(interval.y);
				if(node == null) {
					//System.out.println("Didnt find a predecessor");
					//System.out.println("Placing "+newNode.key+" to the left of "+leftmostInList.key);
					// New leftmost
					leftmostInList.left = newNode;
					newNode.right = leftmostInList;
					leftmostInList = newNode;
				}
				else {
					//System.out.println("Found predecessor "+node.key);
					newNode.left = node;
					RBNode right = node.right;
					node.right = newNode;
					if(right != null) {
						right.left = newNode;
						newNode.right = right;
					}
				}
			}
			else {
				leftmostInList = newNode;
			}
			//System.out.println("Placed interval "+interval.a+","+interval.b+","+interval.y+" in Node "
					//+ a+","+b);
			entries.insert(newNode);
			if(newNode.key > largestYStored) {
				largestYStored = newNode.key;
			}
		}
		else {
			
			// Check to see if the Interval has a non-empty
			// intersection with the left child
			if(leftChild != null) {
				if((interval.a <= leftChild.a && interval.b >= leftChild.b) ||
						(interval.a >= leftChild.a && interval.b <= leftChild.b) ||
						(interval.a >= leftChild.a && interval.a <= leftChild.b) ||
						(interval.b >= leftChild.a && interval.b <= leftChild.b)) {
					leftChild.insert(interval);
				}
			}
			
		
			
			// Check to see if the Interval has a non-empty
			// intersection with the right child
			
			if(rightChild != null) {
				if((interval.a <= rightChild.a && interval.b >= rightChild.b) ||
						(interval.a >= rightChild.a && interval.b <= rightChild.b) ||
						(interval.a >= rightChild.a && interval.a <= rightChild.b) ||
						(interval.b >= rightChild.a && interval.b <= rightChild.b)) {
					rightChild.insert(interval);	
				}
			}
		}	
	}
	
	public ArrayList<Interval> query(int x, int y1, int y2) {
		
		ArrayList<Interval> ret = new ArrayList<Interval>();
		if(x >= a && x <= b) {
			if(counter > 0 && y1 <= largestYStored) {
				//System.out.println("Reporting in node: "+a+","+b);
				RBNode node = entries.predecessorOrEqual(y1);
				if(node == null) {
					//System.out.println("Found Node: NULL");
					node = leftmostInList;
				} else {
					//System.out.println("Found Node: "+node.key);
				}
				if(node.key >= y1 && node.key <= y2) {
					ret.add(node.value);
					//System.out.println("Adding interval: "+node.value.id);
				}
				node = node.right;
				while(node != null && node.key <= y2) {
					//System.out.println("Adding interval: "+node.value.id);
					ret.add(node.value);
					node = node.right;
				}
			}
			
			if(leftChild != null && (x >= leftChild.a && x <= leftChild.b)) {
				ret.addAll(leftChild.query(x,y1,y2));
			}
			else if(rightChild != null && x >= rightChild.a && x <= rightChild.b) {
				ret.addAll(rightChild.query(x,y1,y2));
			}
		}
		return ret;
	}
}
