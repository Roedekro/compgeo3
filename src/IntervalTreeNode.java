import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class IntervalTreeNode implements Serializable {

	IntervalTreeNode leftChild;
	IntervalTreeNode rightChild;
	//ArrayList<Interval> left;
	//ArrayList<Interval> right;
	PrioritySearchTreeMin left;
	PrioritySearchTreeMax right;
	int mid;
	
	public IntervalTreeNode(ArrayList<Interval> intervals) {
		
		ArrayList<Integer> endpoints = new ArrayList<Integer>();
		for(int i = 0; i < intervals.size(); i++) {
			Interval interval = intervals.get(i);
			endpoints.add(interval.a);
			endpoints.add(interval.b);
		}
		
		// Find the median of the endpoints in O(n) time using selection.
		int goal = endpoints.size() / 2;
		if(endpoints.size() % 2 != 0) {
			goal++;
		}
		mid = medianSelection(endpoints, goal);
		
		ArrayList<Interval> toLeftChild = new ArrayList<Interval>();
		ArrayList<Interval> toRightChild = new ArrayList<Interval>();
		ArrayList<Interval> toThisNode = new ArrayList<Interval>();
		
		for(int i = 0; i < intervals.size(); i++) {
			Interval interval = intervals.get(i);
			if(interval.a <= mid && mid <= interval.b) {
				toThisNode.add(interval);
			}
			else if( interval.b < mid) {
				toLeftChild.add(interval);
			}
			else {
				toRightChild.add(interval);
			}
		}
		
		/*// Sort by left endpoint
		Collections.sort(toThisNode);
		left = toThisNode;
		right = new ArrayList<Interval>();
		for(int i = 0; i < left.size(); i++) {
			right.add(left.get(i));
		}
		// Sort by right endpoint
		Collections.sort(right,new IntervalReverseComparator());*/
		
		if(toThisNode.size() > 0) {
			//System.out.println("Placing "+toThisNode.size()+" elements in node "+mid);
			left = new PrioritySearchTreeMin(toThisNode);
			right = new PrioritySearchTreeMax(toThisNode);
			//System.out.println("Node--------------------Node");
		}
		
		
		
		
		
		if(toLeftChild.size() > 0) {
			leftChild = new IntervalTreeNode(toLeftChild);
		}
		if(toRightChild.size() > 0) {
			rightChild = new IntervalTreeNode(toRightChild);
		}
	}
	
	public int medianSelection(ArrayList<Integer> list, int goal) {
		
		if(list.size() == 1) {
			return list.get(0);
		}
		else if(list.size() <= 64) {
			// List is short enough to just sort, using any sort
			Collections.sort(list);
			return list.get(goal-1);
		}
		else {
			
			// Find the median of medians that ensures a 30/70 to 70/30 split
			int median = medianOfMedians(list);
			ArrayList<Integer> left = new ArrayList<Integer>();
			ArrayList<Integer> right = new ArrayList<Integer>();
			for(int i = 0; i < list.size(); i++) {
				int x = list.get(i);
				if(x <= median) {
					left.add(x);
				}
				else {
					right.add(x);
				}
			}
			
			if(left.size() == goal) {
				return median;
			}
			else {
				if(goal < left.size()) {
					//System.out.println(left.size());
					return medianSelection(left,goal);
				}
				else {
					return medianSelection(right,goal-left.size());
				}
			}
		}
	}
	
	public int medianOfMedians(ArrayList<Integer> list) {
		
		if(list.size() <= 64) {
			Collections.sort(list);
			int i = list.size() / 2;
			if(list.size() % 2 != 0) {
				i = i+1;
			}
			return(list.get(i-1));
		}
		else {
			ArrayList<Integer> medians = new ArrayList<Integer>();
			ArrayList<Integer> temp = new ArrayList<Integer>();
			
			int j = 0;
			for(int i = 0; i < list.size(); i++) {
				temp.add(list.get(i));
				j++;
				if(j == 5) {
					Collections.sort(temp);
					medians.add(temp.get(2));
					temp = new ArrayList<Integer>();
					j = 0;
				}
				else if(i == list.size()-1) {
					Collections.sort(temp);
					int x = temp.size() / 2;
					if(temp.size() % 2 != 0) {
						x++;
					}
					medians.add(temp.get(x-1));
				}
			}
			
			// Recurse
			return medianOfMedians(medians);
		}
	}
	
	public ArrayList<Interval> query (int x, int y1, int y2) {
		ArrayList<Interval> ret = new ArrayList<Interval>();
		
		if(x < mid) {
			// Lefttree
			
			if(left != null) {
				ret.addAll(left.query(x,y1,y2));
			}
			
			if(leftChild != null) {
				ret.addAll(leftChild.query(x, y1, y2));
			}
		}
		else {
			// Righttree
			
			if(right != null) {
				//System.out.println("Query Right/min in "+mid);
				//ArrayList<Interval> temp = right.query(x,y1,y2);
				//System.out.println("Node "+mid+" size "+temp.size());
				//ret.addAll(temp);
				ret.addAll(right.query(x,y1,y2));
				//System.out.println("Query Done in "+mid);
			}
			
			if(rightChild != null) {
				ret.addAll(rightChild.query(x, y1, y2));
			}
		}
		
		return ret;
	}
}
