import java.io.Serializable;

public class ThreeDPoint implements Serializable {

	int x,y,z,id;
	Interval interval;
	
	public ThreeDPoint(Interval i) {
		interval = i;
		x = i.a;
		y = i.b;
		z = i.y;
		id = i.id;
	}
}
