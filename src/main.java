import java.util.ArrayList;


public class main {

	public static void main(String[] args) {
		
		int x = 10;
		simpleTestSegmentTree(x);
		System.out.println("-----------------------------------------");
		simpleTestIntervalTree(x);
	}
	
	public static void simpleTestSegmentTree(int x) {
		
		ArrayList<Interval> array = new ArrayList<Interval>();
		array.add(new Interval(1,2,1));
		array.add(new Interval(3,7,2));
		array.add(new Interval(4,8,3));
		array.add(new Interval(5,9,4));
		array.add(new Interval(6,10,5));
		
		array.add(new Interval(5,6,6));
		
		//for(int i = 0; i < 5; i++)
		
		SegmentTree tree = new SegmentTree(array);
		
		ArrayList<Interval> ret = tree.report(x);
		for(int i = 0; i < ret.size(); i++) {
			System.out.println(ret.get(i).id);
		}
	}
	
	public static void simpleTestIntervalTree(int x) {
		
		ArrayList<Interval> array = new ArrayList<Interval>();
		array.add(new Interval(1,2,1));
		array.add(new Interval(3,7,2));
		array.add(new Interval(4,8,3));
		array.add(new Interval(5,9,4));
		array.add(new Interval(6,10,5));
		
		array.add(new Interval(5,6,6));
		//for(int i = 0; i < 5; i++)
		
		IntervalTree tree = new IntervalTree(array);
		
		ArrayList<Interval> ret = tree.query(x);
		for(int i = 0; i < ret.size(); i++) {
			System.out.println(ret.get(i).id);
		}
	}

}
