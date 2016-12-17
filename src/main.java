import java.util.ArrayList;
import java.util.Collections;


public class main {

	public static void main(String[] args) {
		
		int x = 8;
		int y1 = 4;
		int y2 = 6;
		simpleTestSegmentTree(x,y1,y2);
		System.out.println("-----------------------------------------");
		simpleTestIntervalTree(x,y1,y2);
		System.out.println("-----------------------------------------");
		SimpleTestRangeTree(x, y1, y2);
	}
	
	public static void SimpleTestRangeTree(int x, int y1, int y2) {
		
		ArrayList<Interval> array = new ArrayList<Interval>();
		array.add(new Interval(1,2,1,1));
		array.add(new Interval(3,7,2,2));
		array.add(new Interval(4,8,3,3));
		array.add(new Interval(5,9,4,4));
		array.add(new Interval(6,10,5,5));
		
		array.add(new Interval(5,6,6,6));
		
		ArrayList<ThreeDPoint> array2 = new ArrayList<ThreeDPoint>();
		ArrayList<ThreeDPoint> array3 = new ArrayList<ThreeDPoint>();
		ArrayList<ThreeDPoint> array4 = new ArrayList<ThreeDPoint>();
		for(int i = 0; i < array.size(); i++) {
			array2.add(new ThreeDPoint(array.get(i)));
			array3.add(new ThreeDPoint(array.get(i)));
			array4.add(new ThreeDPoint(array.get(i)));
		}
		
		Collections.sort(array2,new ComparatorByX());
		Collections.sort(array3,new CompareByY());
		Collections.sort(array4,new CompareByZ());
		
		ThreeDRangeTree tree = new ThreeDRangeTree(array2, array3, array4);
		
		ArrayList<ThreeDPoint> array5 = tree.query(x, y1, y2);
		for(int i = 0; i<array5.size(); i++) {
			System.out.println(array5.get(i).interval.id);
		}
		
	}
	
	public static void simpleTestSegmentTree(int x, int y1, int y2) {
		
		ArrayList<Interval> array = new ArrayList<Interval>();
		array.add(new Interval(1,2,1,1));
		array.add(new Interval(3,7,2,2));
		array.add(new Interval(4,8,3,3));
		array.add(new Interval(5,9,4,4));
		array.add(new Interval(6,10,5,5));
		
		array.add(new Interval(5,6,6,6));
		
		//for(int i = 0; i < 5; i++)
		
		SegmentTree tree = new SegmentTree(array);
		
		System.out.println("-----------------------------------------");
		ArrayList<Interval> ret = tree.report(x,y1,y2);
		System.out.println("-----------------------------------------");
		for(int i = 0; i < ret.size(); i++) {
			System.out.println(ret.get(i).id);
		}
	}
	
	public static void simpleTestIntervalTree(int x, int y1, int y2) {
		
		ArrayList<Interval> array = new ArrayList<Interval>();
		array.add(new Interval(1,2,1,1));
		array.add(new Interval(3,7,2,2));
		array.add(new Interval(4,8,3,3));
		array.add(new Interval(5,9,4,4));
		array.add(new Interval(6,10,5,5));
		
		array.add(new Interval(5,6,6,6));
		//for(int i = 0; i < 5; i++)
		
		IntervalTree tree = new IntervalTree(array);
		
		ArrayList<Interval> ret = tree.query(x,y1,y2);
		for(int i = 0; i < ret.size(); i++) {
			System.out.println(ret.get(i).id);
		}
	}

}
