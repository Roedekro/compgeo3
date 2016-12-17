import java.util.Comparator;

public class Point {

    public int x;
    public int y;
    public int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static class XComparator implements Comparator<Point> {

        @Override
        public int compare(Point point1, Point point2) {
            return point1.x - point2.x;
        }

    }

    public static class YComparator implements Comparator<Point> {

        @Override
        public int compare(Point point1, Point point2) {
            return point1.y - point2.y;
        }

    }

    public static class ZComparator implements Comparator<Point> {

        @Override
        public int compare(Point point1, Point point2) {
            return point1.z - point2.z;
        }

    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}
