import java.util.Comparator;


public class ComparatorByX implements Comparator<ThreeDPoint>{

	@Override
	public int compare(ThreeDPoint o1, ThreeDPoint o2) {
		if(o1.x != o2.x) {
			return Integer.compare(o1.x, o2.x);
		}
		else if(o1.y != o2.y) {
			return Integer.compare(o1.y, o2.y);
		}
		else {
			return Integer.compare(o1.z, o2.z);
		}
	}

}
