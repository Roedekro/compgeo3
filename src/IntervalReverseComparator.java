import java.util.Comparator;


public class IntervalReverseComparator implements Comparator<Interval>{

	@Override
	public int compare(Interval o1, Interval o2) {
		// Compare by left coordinate, so we can sort them
		if(o1.b != o2.b) {
			return (-1 * Integer.compare(o1.b, o2.b));
		}
		else {
			return (-1 * Integer.compare(o1.a, o2.a));
		}
	}
}
