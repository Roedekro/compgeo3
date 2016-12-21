import java.util.ArrayList;
import java.util.Collections;


public class PrioritySearchTreeMax {

	Interval pmax;
	int ymedian;
	PrioritySearchTreeMax left;
	PrioritySearchTreeMax right;

	public PrioritySearchTreeMax(ArrayList<Interval> intervals) {
		
		pmax = null;
		// Find pmax
		for(int i = 0; i < intervals.size(); i++) {
			if(pmax == null || intervals.get(i).a > pmax.a) {
				pmax = intervals.get(i);
			}
		}
		
		//System.out.println("Found pmax="+pmax.a+","+pmax.b+","+pmax.y);
		
		// Make array of y-coordinates
		ArrayList<Integer> ycoordinates = new ArrayList<Integer>();
		for(int i = 0; i < intervals.size(); i++) {
			if(intervals.get(i).id != pmax.id) {
				ycoordinates.add(intervals.get(i).y);
				//System.out.println("Added "+intervals.get(i).y);
			}
		}
		
		if(ycoordinates.size() > 0) {
			int goal = ycoordinates.size()/2;
			if(ycoordinates.size() % 2 != 0) {
				goal++;
			}
			ymedian = medianSelection(ycoordinates, goal);
			
			ArrayList<Interval> toLeft = new ArrayList<Interval>();
			ArrayList<Interval> toRight = new ArrayList<Interval>();
			for(int i = 0; i < intervals.size(); i++) {
				Interval interval = intervals.get(i);
				if(interval.id == pmax.id) {
					// Nothing
				}
				else if(interval.y < ymedian) {
					toLeft.add(interval);
				}
				else {
					toRight.add(interval);
				}
			}
			if(toLeft.size() > 0) {
				left = new PrioritySearchTreeMax(toLeft);
			}
			if(toRight.size() > 0) {
				right = new PrioritySearchTreeMax(toRight);
			}	
		}
		else {
			ymedian = pmax.y;
		}

		//System.out.println("MaxTree "+pmax.a+" with ymedian="+ymedian);
	}
	
	public ArrayList<Interval> query(int x, int y1, int y2) {
		ArrayList<Interval> ret = new ArrayList<Interval>();
		if(pmax.a <= x && x <= pmax.b && pmax.y >= y1 && pmax.y <= y2) {
			ret.add(pmax);
		}
		
		// Find out if this is vsplit or not
		boolean split = false;
		/*if(!(y1 < ymedian && y2 < ymedian) || !(y1 >= ymedian && y2 >= ymedian)) {
			split = true;
		}*/
		if(y1 <= ymedian && ymedian <= y2) {
			split = true;
		}
		
		if(split) {
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
		ArrayList<Interval> ret = new ArrayList<Interval>();
		boolean add = false;
		if(leftY && pmax.y >= y) {
			add = true;
		}
		if(!leftY && pmax.y <= y) {
			add = true;
		}
		
		if(pmax.a <= x && x <= pmax.b && add) {
			ret.add(pmax);
			//System.out.println("Added Max "+pmax.id);
		}
		
		if(leftY) {
			if(y < ymedian) {
				if(left != null) {
					ret.addAll(left.queryAfterSplit(x, y, leftY));
				}
				if(right != null) {
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
			if(y >= ymedian) {
				if(right != null) {
					ret.addAll(right.queryAfterSplit(x, y, leftY));
				}
				if(left != null) {
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
		if(pmax.a >= x) {
			ret.add(pmax);
			//System.out.println("Added Max "+pmax.id);
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
		else if(list.size() <= 32) {
			// List is short enough to just sort, using any sort
			//System.out.println("Size="+list.size() + " goal="+goal);
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
					/*System.out.println("-----");
					System.out.println(left.size());
					System.out.println(median);
					if(left.size() > 5) {
						System.out.println("---");
						System.out.println(left.get(0));
						System.out.println(left.get(1));
						System.out.println(left.get(2));
						System.out.println(left.get(3));
						System.out.println(left.get(4));
						System.out.println(left.get(5));
					}
					System.out.println("-----");*/
					return medianSelection(left,goal);
				}
				else {
					return medianSelection(right,goal-left.size());
				}
			}
		}
	}
	
	public int medianOfMedians(ArrayList<Integer> list) {
		
		if(list.size() <= 32) {
			Collections.sort(list);
			int item = list.size() / 2;
			if(list.size() % 2 != 0) {
				item = item+1;
			}
			return(list.get(item-1));
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
					int x = temp.size() / 2;
					if(temp.size() % 2 != 0) {
						x++;
					}
					medians.add(temp.get(x-1));
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
