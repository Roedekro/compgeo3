import java.util.ArrayList;


public class IntervalTree {

	IntervalTreeNode root;
	
	public IntervalTree(ArrayList<Interval> intervals) {
		
		// We could sort intervals now, but we sort them in the node
		root = new IntervalTreeNode(intervals);
	}
	
	public ArrayList<Interval> query (int x, int y1, int y2) {
		return root.query(x,y1,y2);		
	}
	
	
	
	/*public ArrayList<Interval> query(int x) {
		
		ArrayList<Interval> ret = new ArrayList<Interval>();
		IntervalTreeNode node = root;
		while(node != null) {
			
			// If not leaf
			
			if(x < node.mid) {
				int i = 0;
				boolean c = true;
				while(c && i < node.left.size()) {
					Interval interval = node.left.get(i);
					if(interval.a <= x && x <= interval.b) {
						ret.add(interval);
						i++;
					}
					else {
						c = false;
					}
				}
				node = node.leftChild;
			}
			else {
				int i = 0;
				boolean c = true;
				while(c && i < node.right.size()) {
					Interval interval = node.right.get(i);
					if(interval.a <= x && x <= interval.b) {
						ret.add(interval);
						i++;
					}
					else {
						c = false;
					}
				}
				node = node.rightChild;
			}
		}
		
		return ret;
	}*/
}
