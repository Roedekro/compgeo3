import java.util.ArrayList;


public class ThreeDRangeTree {

	ThreeDPoint point;
	ThreeDRangeTree left;
	ThreeDRangeTree right;
	TwoDRangeTree tAssoc;
	
	// ASSUME ALL ARRAYLISTS ARE SORTED BEFOREHAND!
	public ThreeDRangeTree(ArrayList<ThreeDPoint> x, ArrayList<ThreeDPoint> y, ArrayList<ThreeDPoint> z) {
		
		tAssoc = new TwoDRangeTree(y, z);
		
		int goal = x.size()/2;
		if(x.size() % 2 != 0) {
			goal++;
		}
		point = x.get(goal-1);
		
		ArrayList<ThreeDPoint> toLeftX = new ArrayList<ThreeDPoint>();
		ArrayList<ThreeDPoint> toRightX = new ArrayList<ThreeDPoint>();
		ArrayList<ThreeDPoint> toLeftY = new ArrayList<ThreeDPoint>();
		ArrayList<ThreeDPoint> toRightY = new ArrayList<ThreeDPoint>();
		ArrayList<ThreeDPoint> toLeftZ = new ArrayList<ThreeDPoint>();
		ArrayList<ThreeDPoint> toRightZ = new ArrayList<ThreeDPoint>();
		
		for(int i = 0; i < x.size(); i++) {
			if(x.get(i).x <= point.x) {
				toLeftX.add(x.get(i));
			}
			else {
				toRightX.add(x.get(i));
			}
		}
		
		for(int i = 0; i < y.size(); i++) {
			if(y.get(i).x <= point.x) {
				toLeftY.add(y.get(i));
			}
			else {
				toRightY.add(y.get(i));
			}
		}
		
		for(int i = 0; i < z.size(); i++) {
			if(z.get(i).x <= point.x) {
				toLeftZ.add(z.get(i));
			}
			else {
				toRightZ.add(z.get(i));
			}
		}
		
		if(toLeftX.size() > 0) {
			left = new ThreeDRangeTree(toLeftX, toLeftY, toLeftZ);
		}
		if(toRightX.size() > 0) {
			right = new ThreeDRangeTree(toRightX, toRightY, toRightZ);
		}
	}
}
