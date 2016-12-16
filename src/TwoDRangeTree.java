import java.util.ArrayList;


public class TwoDRangeTree {

	ThreeDPoint point;
	TwoDRangeTree left;
	TwoDRangeTree right;
	OneDRangeTree tAssoc;
	
	// Assume points comes presorted
	public TwoDRangeTree(ArrayList<ThreeDPoint> y, ArrayList<ThreeDPoint> z) {
		

		tAssoc = new OneDRangeTree(z);
		
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
			if(y.get(i).y <= point.y) {
				toLeftY.add(y.get(i));
			}
			else {
				toRightY.add(y.get(i));
			}
		}
		
		for(int i = 0; i < y.size(); i++) {
			if(z.get(i).y <= point.y) {
				toLeftZ.add(z.get(i));
			}
			else {
				toRightZ.add(z.get(i));
			}
		}
		
		if(toLeftY.size() > 0) {
			left = new TwoDRangeTree(toLeftY, toLeftZ);
		}
		if(toRightY.size() > 0) {
			right = new TwoDRangeTree(toRightY, toRightZ);
		}
	}
	
}
