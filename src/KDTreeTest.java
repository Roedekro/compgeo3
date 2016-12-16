import java.util.ArrayList;
import java.util.List;

public class KDTreeTest {

    public static void main(String[] args) {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(2, 3, 5));
        points.add(new Point(4, 7, 10));
        points.add(new Point(6, 8, 9));
        KDTree.PointCollection pc = new KDTree.PointCollection(points);
        KDTreeNode node = KDTree.buildKDTree(pc, 0);
        int test = 1;
    }

}
