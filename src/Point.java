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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        if (y != point.y) return false;
        return z == point.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
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
