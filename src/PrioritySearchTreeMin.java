import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class PrioritySearchTreeMin implements Serializable {
	
	Interval pmin;
	int ymedian;
	PrioritySearchTreeMin left;
	PrioritySearchTreeMin right;

	public PrioritySearchTreeMin(ArrayList<Interval> intervals) {
		
		
		pmin = null;
		// Find pmin
		for(int i = 0; i < intervals.size(); i++) {
			if(pmin == null || intervals.get(i).a < pmin.a) {
				pmin = intervals.get(i);
			}
		}
		
		//System.out.println("Found pmin="+pmin.a+","+pmin.b+","+pmin.y);
		
		// Make array of y-coordinates
		ArrayList<Integer> ycoordinates = new ArrayList<Integer>();
		for(int i = 0; i < intervals.size(); i++) {
			if(intervals.get(i).id != pmin.id) {
				ycoordinates.add(intervals.get(i).y);
				//System.out.println("Added "+ intervals.get(i).y);
			}
		}
		if(ycoordinates.size() > 0) {
			int goal = ycoordinates.size()/2;
			if(ycoordinates.size() % 2 != 0) {
				goal++;
			}
			ymedian = medianSelection(ycoordinates, goal);
			
			//System.out.println("Ymedian="+ymedian);
			
			ArrayList<Interval> toLeft = new ArrayList<Interval>();
			ArrayList<Interval> toRight = new ArrayList<Interval>();
			for(int i = 0; i < intervals.size(); i++) {
				Interval interval = intervals.get(i);
				if(interval.id == pmin.id) {
					// Nothing
				}
				else if(interval.y < ymedian) {
					toLeft.add(interval);
				}
				else {
					toRight.add(interval);
				}
			}
			//System.out.println(toLeft.size()+","+toRight.size());
			if(toLeft.size() > 0) {
				left = new PrioritySearchTreeMin(toLeft);
			}
			if(toRight.size() > 0) {
				right = new PrioritySearchTreeMin(toRight);
			}
		}
		else {
			ymedian = pmin.y;
		}
		//System.out.println("MinTree "+pmin.b+" with ymedian="+ymedian);
		
		
	}
	
	public ArrayList<Interval> query(int x, int y1, int y2) {
		//System.out.println("Min: Active Node is "+pmin.a+","+pmin.b+","+pmin.y+" with median="+ymedian);
		ArrayList<Interval> ret = new ArrayList<Interval>();
		if(pmin.a <= x && x <= pmin.b && pmin.y >= y1 && pmin.y <= y2) {
			ret.add(pmin);
			//System.out.println("Min: Added "+pmin.a+","+pmin.b+","+pmin.y);
		}
		
		if(pmin.a > x) {
			return ret;
		}
		
		// Find out if this is vsplit or not
		boolean split = false;
		/*if(!(y1 < ymedian && y2 < ymedian) || !(y1 >= ymedian && y2 >= ymedian)) {
			split = true;
		}*/
		if(y1 <= ymedian && ymedian <= y2) {
			split = true;
		}
		
		//System.out.println("isSplit="+split);
		//System.out.println("Min: Finished in Node "+pmin.a+","+pmin.b+","+pmin.y+" with median="+ymedian);
		
		if(split) {
			//System.out.println("Found Split");
			if(left != null) {
				ret.addAll(left.queryAfterSplit(x, y1,true));
			}
			if(right != null) {
				ret.addAll(right.queryAfterSplit(x, y2,false));
			}
		}
		else {
			if(y1 < ymedian) {
				if(left != null) {
					ret.addAll(left.query(x, y1, y2));
				}
			}
			else {
				if(right != null) {
					ret.addAll(right.query(x, y1, y2));
				}
			}
		}
		
		return ret;
	}
	
	public ArrayList<Interval> queryAfterSplit(int x, int y, boolean leftY) {
		//System.out.println("1");
		//System.out.println("Min: QueryAfterSplit in Node "+pmin.a+","+pmin.b+","+pmin.y+" with median="+ymedian);
		//System.out.println("x="+x+" y="+y+" leftY="+leftY);
		ArrayList<Interval> ret = new ArrayList<Interval>();
		boolean add = false;
		if(leftY && pmin.y >= y) {
			add = true;
		}
		if(!leftY && pmin.y <= y) {
			add = true;
		}
		
		if(pmin.a <= x && x <= pmin.b && add) {
			ret.add(pmin);
			//System.out.println("Added Min "+pmin.id);
			//System.out.println(pmin.y+","+y+","+leftY+","+add);
		}
		
		if(pmin.a > x) {
			return ret;
		}
		
		if(leftY) {
			//System.out.println("2");
			if(y < ymedian) {
				if(left != null) {
					ret.addAll(left.queryAfterSplit(x, y, leftY));
				}
				if(right != null) {
					//System.out.println("A");
					ret.addAll(right.reportInSubTree(x));
				}
			}
			else {
				if(right != null) {
					ret.addAll(right.queryAfterSplit(x, y, leftY));
				}
			}	
		} 
		else {
			//System.out.println("3");
			if(y >= ymedian) {
				if(right != null) {
					ret.addAll(right.queryAfterSplit(x, y, leftY));
				}
				if(left != null) {
					//System.out.println("B");
					ret.addAll(left.reportInSubTree(x));
				}
			}
			else {
				if(left != null) {
					ret.addAll(left.queryAfterSplit(x, y, leftY));
				}
			}
		}
		
		
		return ret;
	}
	
	public ArrayList<Interval> reportInSubTree(int x) {
		ArrayList<Interval> ret = new ArrayList<Interval>();
		//System.out.println("C "+pmin.b+","+x);
		if(pmin.a <= x) {
			ret.add(pmin);
			//System.out.println("Added Min "+pmin.id);
			if(left != null) {
				ret.addAll(left.reportInSubTree(x));
			}
			if(right != null) {
				ret.addAll(right.reportInSubTree(x));
			}
		}
		
		return ret;
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
				i++;
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
				/*else if(i == list.size()-1) {
					Collections.sort(temp);
					int x = temp.size() / 2;
					if(temp.size() % 2 != 0) {
						x++;
					}
					medians.add(temp.get(x-1));
				}*/
			}
			
			// Recurse
			return medianOfMedians(medians);
		}
	}
	
}
