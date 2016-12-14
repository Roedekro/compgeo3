import java.util.ArrayList;


public class SegmentTreeNode {

	public SegmentTreeNode leftChild;
	public SegmentTreeNode rightChild;
	public ArrayList<Interval> entries;
	public int a;
	public int b;
	
	public SegmentTreeNode(int left, int right) {
		a = left;
		b = right;
		leftChild = null;
		rightChild = null;
		entries = new ArrayList<Interval>();
	}
	
	public void insert(Interval interval) {
		// If the nodes interval is inside the interval of the Interval
		// add the Interval to the node.
		
		System.out.println("Node a="+a+" b="+b);
		System.out.println("Interval a="+interval.a +" b="+interval.b);
		
		if(interval.a <= a && interval.b >= b){
			entries.add(interval);
			System.out.println("Placed interval "+interval.a+","+interval.b+" in Node "
					+ a+","+b);
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
	
	public ArrayList<Interval> report(int x) {
		
		ArrayList<Interval> ret = new ArrayList<Interval>();
		if(x >= a && x <= b) {
			ret.addAll(entries);
			if(leftChild != null && (x >= leftChild.a && x <= leftChild.b)) {
				ret.addAll(leftChild.report(x));
			}
			else if(rightChild != null && x >= rightChild.a && x <= rightChild.b) {
				ret.addAll(rightChild.report(x));
			}
		}
		return ret;
	}
}
