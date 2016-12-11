import java.util.ArrayList;
import java.util.Collections;


public class SegmentTree {

	public SegmentTreeNode root;
	
	public SegmentTree(ArrayList<Interval> intervals) {
		
		Collections.sort(intervals);
		ArrayList<SegmentTreeNode> level = new ArrayList<SegmentTreeNode>();
		
		for(int i = 0; i < intervals.size(); i++) {
			level.add(new SegmentTreeNode(intervals.get(i).a, intervals.get(i).b));
		}
		
		
		/*for(int i = 0; i < intervals.size(); i = i+2) {
			int a = intervals.get(i).a;
			int b = intervals.get(i).b;
			if(i+1 < intervals.size()) {
				b = intervals.get(i+1).b;
			}
			
			SegmentTreeNode node = new SegmentTreeNode(a,b);
			level.add(node);
		}*/
		
		root = recursiveBuildLevel(level).get(0);
	}
	
	// Recursively build the segment tree in O(n) time.
	// O(n log n) if we count the sorting above.
	public ArrayList<SegmentTreeNode> recursiveBuildLevel(ArrayList<SegmentTreeNode> intervals) {
		
		ArrayList<SegmentTreeNode> level = new ArrayList<SegmentTreeNode>();
		
		for(int i = 0; i < intervals.size(); i = i+2) {
			int a = intervals.get(i).a;
			int b = intervals.get(i).b;
			if(i+1 < intervals.size()) {
				b = intervals.get(i+1).b;
			}
			
			SegmentTreeNode node = new SegmentTreeNode(a,b);
			node.leftChild = intervals.get(i);
			if(i+1 < intervals.size()) {
				node.rightChild = intervals.get(i+1);
			}
			level.add(node);
		}
		if(level.size() == 1) {
			return level;
		}
		else {
			return recursiveBuildLevel(level);
		}
	}
	
	public ArrayList<Interval> report(int x) {
		return root.report(x);
	}
}
