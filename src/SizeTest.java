import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.io.output.NullOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SizeTest {

    private static final Random rand = new Random();

    public static void main(String[] args) throws IOException {
        int[] testSizes = { 1000, 10000, 100000, 1000000 };
        for (int i = 0; i < testSizes.length; i++) {
            int n = testSizes[i];
            System.out.println("Running tests for n=" + n);

            //System.out.println("Running test for KD-tree...");
            //System.out.println("Size of tree: " + runKDTreeTest(n) + " bytes");

            System.out.println("Running test for Segment Tree...");
            System.out.println("Size of tree: " + runSegmentTreeTest(n) + " bytes");

            //System.out.println("Running test for Interval Tree...");
            //System.out.println("Size of tree: " + runIntervalTreeTest(n) + " bytes");

            //System.out.println("Running test for 3D Range Tree...");
            //System.out.println("Size of tree: " + runThreeDRangeTreeTest(n) + " bytes");
        }
    }

    private static int runKDTreeTest(int n) throws IOException {
        // Generate segments
        List<Point> points = new ArrayList<>();
        for(int j = 0; j < n; j++) {
            int x1 = rand.nextInt(n) + 1;
            int x2 = x1 + rand.nextInt(n) + 1;
            int y = rand.nextInt(n) + 1;
            points.add(new Point(x1, x2, y));
        }

        // Build the tree
        KDTreeNode root = KDTree.buildKDTree(points);

        // Determine the size of the tree (in bytes)
        return getSize(root);
    }

    private static int runSegmentTreeTest(int n) throws IOException {
        // Generate segments
        ArrayList<Interval> intervals = new ArrayList<Interval>();
        for(int j = 0; j < n; j++) {
            int left = rand.nextInt(n) + 1;
            int right = left + rand.nextInt(n) + 1;
            int y = rand.nextInt(n) + 1;
            intervals.add(new Interval(left, right, y, j));
        }

        // Build the tree
        SegmentTree tree = new SegmentTree(intervals);

        // Determine the size of the tree
        return getSize(tree);
    }

    private static int runIntervalTreeTest(int n) throws IOException {
        // Generate segments
        ArrayList<Interval> intervals = new ArrayList<Interval>();
        for(int j = 0; j < n; j++) {
            int left = rand.nextInt(n) + 1;
            int right = left + rand.nextInt(n) + 1;
            int y = rand.nextInt(n) + 1;
            intervals.add(new Interval(left, right, y, j));
        }

        // Build the tree
        IntervalTree tree = new IntervalTree(intervals);

        // Determine the size of the tree
        return getSize(tree);
    }

    private static int runThreeDRangeTreeTest(int n) throws IOException {
        // Generate segments
        ArrayList<ThreeDPoint> points1 = new ArrayList<ThreeDPoint>();
        ArrayList<ThreeDPoint> points2 = new ArrayList<ThreeDPoint>();
        ArrayList<ThreeDPoint> points3 = new ArrayList<ThreeDPoint>();
        for(int j = 0; j < n; j++) {
            int left = rand.nextInt(n) + 1;
            int right = left + rand.nextInt(n) + 1;
            int y = rand.nextInt(n) + 1;
            Interval interval = new Interval(left, right, y, j);
            points1.add(new ThreeDPoint(interval));
            points2.add(new ThreeDPoint(interval));
            points3.add(new ThreeDPoint(interval));
        }

        // Build the tree
        ThreeDRangeTree rangetree = new ThreeDRangeTree(points1, points2, points3);

        // Determine the size of the tree
        return getSize(rangetree);
    }

    private static int getSize(Serializable o) throws IOException {
        CountingOutputStream cos = new CountingOutputStream(new NullOutputStream());
        ObjectOutputStream oos = new ObjectOutputStream(cos);
        oos.writeObject(o);
        oos.close();
        return cos.getCount();
    }

}
