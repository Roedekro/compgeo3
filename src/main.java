import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


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
	
	public static void test1(int n, int r, int x, int y1, int y2) {
		
		long segmentCounter = 0;
		long intervalCounter = 0;
		long kdCounter = 0;
		long rangeCounter =0;
		
		
		for(int i = 0; i < r; i++) {
			
			Random rand = new Random();
			// Generate intervals
			ArrayList<Interval> intervals = new ArrayList<Interval>();
			ArrayList<Point> points1 = new ArrayList<Point>();
			ArrayList<ThreeDPoint> points2 = new ArrayList<ThreeDPoint>();
			for(int j = 0; j < n; j++) {
				int a = rand.nextInt(n)+1;
				int b = a + rand.nextInt(n)+1;
				if(b > n) {
					b = n;
				}
				int y = rand.nextInt(n)+1;
				Interval interval = new Interval(a,b,y,j);
				intervals.add(interval);
				points1.add(new Point(a,b,y));
				points2.add(new ThreeDPoint(interval));
			}
			
			long tempSegmentCounter = 0;
			long tempIntervalCounter = 0;
			long tempKdCounter = 0;
			long tempRangeCounter =0;
			
			SegmentTree segtree = new SegmentTree(intervals);
			tempSegmentCounter = System.currentTimeMillis();
			segtree.query(x,y1,y2);
			tempSegmentCounter =  System.currentTimeMillis() - tempSegmentCounter;
			System.out.println("Run "+r+" Segment Tree took "+tempSegmentCounter);
			segmentCounter = segmentCounter + tempSegmentCounter;
			
			IntervalTree inttree = new IntervalTree(intervals);
			tempIntervalCounter = System.currentTimeMillis();
			inttree.query(x, y1, y2);
			tempIntervalCounter = System.currentTimeMillis() - tempIntervalCounter;
			System.out.println("Run "+r+" Interval Tree took "+tempIntervalCounter);
			intervalCounter = intervalCounter + tempIntervalCounter;
			
			KDTreeNode kdroot = KDTree.buildKDTree(points1);
			Range range = new Range(0,x,x,n,y1,y2);
			tempKdCounter = System.currentTimeMillis();
			KDTree.searchKDTree(kdroot, range);
			tempKdCounter = System.currentTimeMillis() - tempKdCounter;
			System.out.println("Run "+r+" KD Tree took "+tempKdCounter);
			kdCounter = kdCounter + tempKdCounter;
			
			
		}

		
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
		ArrayList<Interval> ret = tree.query(x,y1,y2);
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
