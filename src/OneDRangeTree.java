import java.util.ArrayList;


public class OneDRangeTree {

	ThreeDPoint point;
	OneDRangeTree left;
	OneDRangeTree right;
	
	// ASSUME INPUT IS SORTED BY Z-COORDINATE!
	public OneDRangeTree(ArrayList<ThreeDPoint> points) {
		
		// Assume the array is already sorted.
		int goal = points.size() / 2;
		if(points.size() % 2 != 0) {
			goal++;
		}
		point = points.get(goal-1);
		
		ArrayList<ThreeDPoint> toLeft = new ArrayList<ThreeDPoint>();
		ArrayList<ThreeDPoint> toRight = new ArrayList<ThreeDPoint>();
		for(int i = 0; i < points.size(); i++) {
			if(i != goal-1) {
				if(points.get(i).z <= point.z) {
					toLeft.add(points.get(i));
				}
				else {
					toRight.add(points.get(i));
				}
			}
		}
		
		if(toLeft.size() > 0) {
			left = new OneDRangeTree(toLeft);
		}
		if(toRight.size() > 0) {
			right = new OneDRangeTree(toRight);
		}
	}
	
}
