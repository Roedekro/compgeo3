import java.util.ArrayList;
import java.util.List;

public class KDTreeTestSimple {

    public static void main(String[] args) {
        System.out.println(3 > Integer.MIN_VALUE);
        List<Point> points = new ArrayList<>();
        points.add(new Point(1,2,1));
        points.add(new Point(3,7,2));
        points.add(new Point(4,8,3));
        points.add(new Point(5,9,4));
        points.add(new Point(6,10,5));
        points.add(new Point(5,6,6));
        KDTreeNode root = KDTree.buildKDTree(points);
        Range range = new Range(0,8,8,0,4,6);
        List<Point> search = KDTree.searchKDTree(root, range);
        for(int i = 0; i < search.size(); i++) {
        	System.out.println(search.get(i).z);
        }
        
        
    }

}
