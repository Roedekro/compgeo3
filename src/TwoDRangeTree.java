import java.util.ArrayList;


public class TwoDRangeTree {

	ThreeDPoint point;
	TwoDRangeTree left;
	TwoDRangeTree right;
	OneDRangeTree tAssoc;
	
	// Assume points comes presorted
	public TwoDRangeTree(ArrayList<ThreeDPoint> y, ArrayList<ThreeDPoint> z) {
		

		tAssoc = new OneDRangeTree(z);
		
		if(y.size() > 1) {
			int goal = y.size() / 2;
			if(y.size() % 2 != 0) {
				goal++;
			}
			point = y.get(goal-1);
			
			ArrayList<ThreeDPoint> toLeftY = new ArrayList<ThreeDPoint>();
			ArrayList<ThreeDPoint> toRightY = new ArrayList<ThreeDPoint>();
			ArrayList<ThreeDPoint> toLeftZ = new ArrayList<ThreeDPoint>();
			ArrayList<ThreeDPoint> toRightZ = new ArrayList<ThreeDPoint>();
			
			for(int i = 0; i < y.size(); i++) {
				if(y.get(i).id != point.id) {
					if(y.get(i).y <= point.y) {
						toLeftY.add(y.get(i));
					}
					else {
						toRightY.add(y.get(i));
					}	
				}
			}
			
			for(int i = 0; i < y.size(); i++) {
				if(z.get(i).id != point.id) {
					if(z.get(i).y <= point.y) {
						toLeftZ.add(z.get(i));
					}
					else {
						toRightZ.add(z.get(i));
					}	
				}
			}
			
			//System.out.println(point.interval.id+","+toLeftY.size()+","+toRightY.size());
			
			if(toLeftY.size() > 0) {
				left = new TwoDRangeTree(toLeftY, toLeftZ);
			}
			if(toRightY.size() > 0) {
				right = new TwoDRangeTree(toRightY, toRightZ);
			}
		}
		else {
			point = y.get(0);
		}
	}
	
	public ArrayList<ThreeDPoint> query(int x, int y1, int y2) {
		
		ArrayList<ThreeDPoint> ret = new ArrayList<ThreeDPoint>();
		
		// Find and report points with y (b) value greater than
		// or equal to x. 
		boolean goLeft = false;
		if(x <= point.y) {
			goLeft = true;
		}
		
		if(x <= point.y && point.z >= y1 && point.z <= y2) {
			ret.add(point);
			//System.out.println("Level2: Added "+point.interval.id);
		}
		
		// Check if leaf
		if(left == null && right == null) {
			
		}
		else if(goLeft) {
			
			// We went left, so everything on the right is
			// greater than or equal to x
			
			if(right != null) {
				ret.addAll(right.report(x,y1,y2));
			}
			if(left != null) {
				ret.addAll(left.query(x, y1, y2));
			}
		}
		else {
			if(right != null) {
				ret.addAll(right.query(x, y1, y2));
			}
		}
		
		return ret;
		
	}
	
	public ArrayList<ThreeDPoint> report(int x, int y1, int y2) {
		
		ArrayList<ThreeDPoint> ret = new ArrayList<ThreeDPoint>();
		// Query tAssoc for points between y1 and y2.
		
		// First find vSplit
		OneDRangeTree vSplit = tAssoc;
		while((vSplit.point.z <= y1 && vSplit.point.z <= y2) || 
				(vSplit.point.z > y1 && vSplit.point.z > y2)) {
			if(y1 <= vSplit.point.z) {
				if(vSplit.left != null) {
					vSplit = vSplit.left;
				}
				else {
					break;
				}
			}
			else {
				if(vSplit.right != null) {
					vSplit = vSplit.right;
				}
				else {
					break;
				}
			}
		}
		
		// Check if vSplit is a leaf
		if(vSplit.left == null && vSplit.right == null) {
			if(vSplit.point.z >= y1 && vSplit.point.z <= y2) {
				ret.add(vSplit.point);
				
			}
		}
		else {
			
			// Split into two tracks: One following y1, and
			// one following y2 down the tree.
			OneDRangeTree followy1 = vSplit.left;
			boolean b1 = true;
			if(followy1 != null) {
				while(b1 && (followy1.left != null || followy1.right != null)) {
					boolean goLeft = false;
					if(y1 <= followy1.point.z) {
						goLeft = true;
					}
					if(goLeft) {
						if(followy1.right != null) {
							ret.addAll(followy1.right.report());
						}
						if(followy1.left != null) {
							followy1 = followy1.left;
						}
						else {
							b1 = false;
						}
					}
					else {
						if(followy1.right != null) {
							followy1 = followy1.right;
						}
						else {
							b1 = false;
						}
					}
				}
			}
			
			// Follow y2
			OneDRangeTree followy2 = vSplit.left;
			boolean b2 = true;
			if(followy2 != null) {
				while(b2 && (followy2.left != null || followy2.right != null)) {
					boolean goLeft = false;
					if(y2 <= followy2.point.z) {
						goLeft = true;
					}
					if(goLeft) {
						if(followy2.left != null) {
							followy2 = followy2.left;
						}
						else {
							b2 = false;
						}
					}
					else {
						if(followy2.left != null) {
							ret.addAll(followy2.left.report());
						}
						if(followy2.right != null) {
							followy2 = followy2.right;
						}
						else {
							b2 = false;
						}
					}
				}
			}
			
			
			
		}
		return ret;
	}
	
}
