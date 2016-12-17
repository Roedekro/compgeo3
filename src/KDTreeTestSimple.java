import java.util.ArrayList;
import java.util.List;

public class KDTreeTestSimple {

    public static void main(String[] args) {
        System.out.println(3 > Integer.MIN_VALUE);
        List<Point> points = new ArrayList<>();
        points.add(new Point(1,2,3));
        points.add(new Point(2,3,4));
        points.add(new Point(3,4,5));
        points.add(new Point(4,5,6));
        points.add(new Point(5,6,7));
        KDTreeNode root = KDTree.buildKDTree(points);
        Range range = new Range(4,5,5,6,6,7);
        List<Point> search = KDTree.searchKDTree(root, range);
    }

}
