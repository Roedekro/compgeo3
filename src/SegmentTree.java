import java.util.ArrayList;
import java.util.Collections;


public class SegmentTree {

	public SegmentTreeNode root;
	
	public SegmentTree(ArrayList<Interval> intervals) {
		
		Collections.sort(intervals);
		ArrayList<SegmentTreeNode> level = new ArrayList<SegmentTreeNode>();
		
		/*for(int i = 0; i < intervals.size(); i++) {
			level.add(new SegmentTreeNode(intervals.get(i).a, intervals.get(i).b));
		}*/
		
		
		/*for(int i = 0; i < intervals.size(); i = i+2) {
			int a = intervals.get(i).a;
			int b = intervals.get(i).b;
			if(i+1 < intervals.size()) {
				b = intervals.get(i+1).b;
			}
			
			SegmentTreeNode node = new SegmentTreeNode(a,b);
			level.add(node);
		}*/
		
		ArrayList<Integer> integers = new ArrayList<Integer>();
		for(int i = 0; i < intervals.size(); i++) {
			Interval interval = intervals.get(i);
			integers.add(interval.a);
			integers.add(interval.b);
		}
		Collections.sort(integers);
		
		/*for(int i = 0; i < integers.size(); i=i+2) {
			level.add(new SegmentTreeNode(integers.get(i),integers.get(i+1)));
			System.out.println("Added leaf: "+integers.get(i)+","+integers.get(i+1));
		}*/
		
		for(int i = 0; i < integers.size(); i++) {
			level.add(new SegmentTreeNode(integers.get(i),integers.get(i)));
			//System.out.println("Added leaf: "+integers.get(i)+","+integers.get(i));
		}
		
		root = recursiveBuildLevel(level).get(0);
		
		for(int i = 0; i < intervals.size(); i++) {
			root.insert(intervals.get(i));
		}
	}
	
	// Recursively build the segment tree in O(n) time.
	// O(n log n) if we count the sorting above.
	public ArrayList<SegmentTreeNode> recursiveBuildLevel(ArrayList<SegmentTreeNode> intervals) {
		
		ArrayList<SegmentTreeNode> level = new ArrayList<SegmentTreeNode>();
		ArrayList<Integer> integers = new ArrayList<Integer>();
		
		for(int i = 0; i < intervals.size(); i = i+2) {
			int a = intervals.get(i).a;
			int b = intervals.get(i).b;
			if(i+1 < intervals.size()) {
				if(b <= intervals.get(i+1).b) {
					b = intervals.get(i+1).b;
				}
			}
			
			SegmentTreeNode node = new SegmentTreeNode(a,b);
			node.leftChild = intervals.get(i);
			if(i+1 < intervals.size()) {
				node.rightChild = intervals.get(i+1);
			}
			level.add(node);
			//System.out.println("Added Node: "+node.a+","+node.b);
		}
	
		
		if(level.size() == 1) {
			return level;
		}
		else {
			return recursiveBuildLevel(level);
		}
	}
	
	public ArrayList<Interval> report(int x, int y1, int y2) {
		return root.report(x,y1,y2);
	}
}
