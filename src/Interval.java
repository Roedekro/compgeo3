
public class Interval implements Comparable<Interval> {

	public int a;
	public int b;
	public int id;
	
	public Interval(int left, int right, int identity) {
		a = left;
		b = right;
		id = identity;
	}

	@Override
	public int compareTo(Interval o) {
		// Compare by left coordinate, so we can sort them
		return Integer.compare(a, o.a);
	}
}
