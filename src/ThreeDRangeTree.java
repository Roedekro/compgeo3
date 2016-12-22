import java.io.Serializable;
import java.util.ArrayList;


public class ThreeDRangeTree implements Serializable {

	ThreeDPoint point;
	ThreeDRangeTree left;
	ThreeDRangeTree right;
	TwoDRangeTree tAssoc;
	
	// ASSUME ALL ARRAYLISTS ARE SORTED BEFOREHAND!
	public ThreeDRangeTree(ArrayList<ThreeDPoint> x, ArrayList<ThreeDPoint> y, ArrayList<ThreeDPoint> z) {
		
		tAssoc = new TwoDRangeTree(y, z);
		
		if(x.size() > 1) {
			
			int goal = x.size()/2;
			if(x.size() % 2 != 0) {
				goal++;
			}
			point = x.get(goal-1);
			//System.out.println("Level1: Placed "+point.interval.id);
			
			ArrayList<ThreeDPoint> toLeftX = new ArrayList<ThreeDPoint>();
			ArrayList<ThreeDPoint> toRightX = new ArrayList<ThreeDPoint>();
			ArrayList<ThreeDPoint> toLeftY = new ArrayList<ThreeDPoint>();
			ArrayList<ThreeDPoint> toRightY = new ArrayList<ThreeDPoint>();
			ArrayList<ThreeDPoint> toLeftZ = new ArrayList<ThreeDPoint>();
			ArrayList<ThreeDPoint> toRightZ = new ArrayList<ThreeDPoint>();
			
			for(int i = 0; i < x.size(); i++) {
				
				if(x.get(i).id != point.id) {
					if(x.get(i).x <= point.x) {
						toLeftX.add(x.get(i));
						//System.out.println("Level1: Placed Left "+x.get(i).interval.id);
					}
					else {
						toRightX.add(x.get(i));
						//System.out.println("Level1: Placed Right "+x.get(i).interval.id);
					}
				}	
			}
			
			//System.out.println("Level1: Placement  X done");
			
			for(int i = 0; i < y.size(); i++) {
				if(y.get(i).id != point.id) {
					if(y.get(i).x <= point.x) {
						toLeftY.add(y.get(i));
						//System.out.println("Level1: Placed Left "+y.get(i).interval.id);
					}
					else {
						toRightY.add(y.get(i));
						//System.out.println("Level1: Placed Right "+y.get(i).interval.id);
					}	
				}
			}
			
			//System.out.println("Level1: Placement  Y done");
			
			for(int i = 0; i < z.size(); i++) {
				if(z.get(i).id != point.id) {
					if(z.get(i).x <= point.x) {
						toLeftZ.add(z.get(i));
						//System.out.println("Level1: Placed Right "+z.get(i).interval.id);
					}
					else {
						toRightZ.add(z.get(i));
						//System.out.println("Level1: Placed Right "+z.get(i).interval.id);
					}
				}	
			}
			
			//System.out.println("Level1: Placement  Z done");
			
			if(toLeftX.size() > 0) {
				left = new ThreeDRangeTree(toLeftX, toLeftY, toLeftZ);
			}
			if(toRightX.size() > 0) {
				right = new ThreeDRangeTree(toRightX, toRightY, toRightZ);
			}
		}
		else {
			point = y.get(0);
			//System.out.println("'Level1: Placed "+point.interval.id);
		}
	}
	
	public ArrayList<ThreeDPoint> query(int x, int y1, int y2) {
		
		ArrayList<ThreeDPoint> ret = new ArrayList<ThreeDPoint>();
		
		// Search for points with x (a) value smaller than
		// or equal to the x value being searched for
		boolean goLeft = false;
		if(x <= point.x) {
			goLeft = true;
		}
		
		if(point.x <= x && point.y >= x && point.z >= y1 && point.z <= y2) {
			ret.add(point);
			//System.out.println("Level1: Added "+point.interval.id);
		}
		
		// Check if leaf
		if(left == null && right == null) {
			
		}
		else if(goLeft) {
			
			// Search left
			if(left != null) {
				ret.addAll(left.query(x, y1, y2));
			}
		}
		else {
			
			// Report left, search right
			if(left != null) {
				//System.out.println("Level1: Reporting left from "+point.interval.id);
				ret.addAll(left.report(x,y1,y2));
				//System.out.println("Level1: Reporting done");
			}
			if(right != null) {
				ret.addAll(right.query(x, y1, y2));
			}
		}
		
		return ret;
	}
	
	public ArrayList<ThreeDPoint> report(int x, int y1, int y2) {
		
		ArrayList<ThreeDPoint> ret = new ArrayList<ThreeDPoint>();
		ret.addAll(tAssoc.query(x,y1,y2));
		return ret;
	}
}
