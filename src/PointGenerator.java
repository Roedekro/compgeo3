import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PointGenerator {

    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    public PointGenerator(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public PointGenerator(int size) {
        this(size, size, size, size);
    }

    public List<Point> uniquePoints(int n) {
        List<Integer> xCords = randomUniqueList(n);
        List<Integer> yCords = randomUniqueList(n);
        List<Integer> zCords = randomUniqueList(n);

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point p = new Point(xCords.remove(0), yCords.remove(0), zCords.remove(0));
            points.add(p);
        }

        return points;
    }

    private List<Integer> randomUniqueList(int n) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) list.add(i);
        Collections.shuffle(list);
        return list;
    }

}
