import java.io.Serializable;

public class Interval implements Comparable<Interval>, Serializable {

	public int a;
	public int b;
	public int y;
	public int id;
	
	public Interval(int left, int right, int y, int identity) {
		a = left;
		b = right;
		this.y = y;
		id = identity;
	}

	@Override
	public int compareTo(Interval o) {
		// Compare by left coordinate, so we can sort them
		if(a != o.a) {
			return Integer.compare(a, o.a);
		}
		else if(b != o.b) {
			return Integer.compare(b, o.b);
		}
		else {
			return Integer.compare(id, o.id);
		}
	}
}
