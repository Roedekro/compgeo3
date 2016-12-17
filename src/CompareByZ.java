import java.util.Comparator;


public class CompareByZ implements Comparator<ThreeDPoint>{

	@Override
	public int compare(ThreeDPoint o1, ThreeDPoint o2) {
		if(o1.z != o2.z) {
			return Integer.compare(o1.z, o2.z);
		}
		else if(o1.x != o2.x) {
			return Integer.compare(o1.x, o2.x);
		}
		else {
			return Integer.compare(o1.y, o2.y);
		}
	}

}
