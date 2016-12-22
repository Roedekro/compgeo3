import java.io.Serializable;

public class Range implements Serializable {

    public int x1;
    public int x2;
    public int y1;
    public int y2;
    public int z1;
    public int z2;

    public Range(int x1, int x2, int y1, int y2, int z1, int z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }

    public boolean contains(Range range) {
        return x1 <= range.x1 &&
                x2 >= range.x2 &&
                y1 <= range.y1 &&
                y2 >= range.y2 &&
                z1 <= range.z1 &&
                z2 >= range.z2;
    }

    public boolean contains(Point point) {
        return x1 <= point.x && x2 >= point.x &&
                y1 <= point.y && y2 >= point.y &&
                z1 <= point.z && z2 >= point.z;
    }

    public boolean intersects(Range range) {
        return (x1 <= range.x1 && range.x1 <= x2) || (x1 <= range.x2 && range.x2 <= x2)
                || (y1 <= range.y1 && range.y1 <= y2) || (y1 <= range.y2 && range.y2 <= y2)
                || (z1 <= range.z1 && range.z1 <= z2) || (z1 <= range.z2 && range.z2 <= z2);
    }

    @Override
    public String toString() {
        return "Range{" +
                "x[" + x1 +
                ":" + x2 +
                "], y[" + y1 +
                ":" + y2 +
                "], z[" + z1 +
                ":" + z2 +
                "]}";
    }
}
