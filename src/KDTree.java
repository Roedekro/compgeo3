import java.util.ArrayList;
import java.util.List;

public class KDTree {

    public static KDTreeNode buildKDTree(PointCollection points, int depth) {
        // Simply return a leaf node if there's only 1 point
        if (points.size() == 1) return new KDTreeNode(points.pointsX.get(0));

        // Detect the depth dimension
        Pair pair = null;
        int medianIndex = (int)Math.ceil((double)points.pointsX.size() / 2) - 1;
        Integer medianValue = null;
        if (depth % 3 == 0) {
            medianValue = points.pointsX.get(medianIndex).x;
            pair = points.splitByXMedian(medianIndex);
        } else if (depth % 3 == 1) {
            medianValue = points.pointsY.get(medianIndex).y;
            pair = points.splitByYMedian(medianIndex);
        } else if (depth % 3 == 2) {
            medianValue = points.pointsZ.get(medianIndex).z;
            pair = points.splitByZMedian(medianIndex);
        }

        // Create left and right nodes
        KDTreeNode vLeft = buildKDTree(pair.left, depth+1);
        KDTreeNode vRight = buildKDTree(pair.right, depth+1);

        // Create the root node for this recursion step
        return new KDTreeNode(medianValue, vLeft, vRight);
    }

    public static class PointCollection {

        public List<Point> pointsX;
        public List<Point> pointsY;
        public List<Point> pointsZ;

        public PointCollection(List<Point> points) {
            pointsX = new ArrayList<>(points);
            pointsX.sort(new Point.XComparator());
            pointsY = new ArrayList<>(points);
            pointsY.sort(new Point.YComparator());
            pointsZ = new ArrayList<>(points);
            pointsZ.sort(new Point.ZComparator());
        }

        private PointCollection(List<Point> pointsX, List<Point> pointsY, List<Point> pointsZ) {
            this.pointsX = pointsX;
            this.pointsY = pointsY;
            this.pointsZ = pointsZ;
        }

        public Pair splitByXMedian(int median) {
            List<Point> leftPointsX = pointsX.subList(0, median+1);
            List<Point> rightPointsX = pointsX.subList(median+1, pointsX.size());

            List<Point> leftPointsY = new ArrayList<>(pointsY);
            leftPointsY.removeAll(rightPointsX);
            List<Point> rightPointsY = new ArrayList<>(pointsY);
            rightPointsY.removeAll(leftPointsX);

            List<Point> leftPointsZ = new ArrayList<>(pointsZ);
            leftPointsZ.removeAll(rightPointsX);
            List<Point> rightPointsZ = new ArrayList<>(pointsZ);
            rightPointsZ.removeAll(leftPointsX);

            return new Pair(new PointCollection(leftPointsX, leftPointsY, leftPointsZ),
                    new PointCollection(rightPointsX, rightPointsY, rightPointsZ));
        }

        public Pair splitByYMedian(int median) {
            List<Point> leftPointsY = pointsY.subList(0, median+1);
            List<Point> rightPointsY = pointsY.subList(median+1, pointsY.size());

            List<Point> leftPointsX = new ArrayList<>(pointsX);
            leftPointsX.removeAll(rightPointsY);
            List<Point> rightPointsX = new ArrayList<>(pointsX);
            rightPointsX.removeAll(leftPointsY);

            List<Point> leftPointsZ = new ArrayList<>(pointsZ);
            leftPointsZ.removeAll(rightPointsY);
            List<Point> rightPointsZ = new ArrayList<>(pointsZ);
            rightPointsZ.removeAll(leftPointsY);

            return new Pair(new PointCollection(leftPointsX, leftPointsY, leftPointsZ),
                    new PointCollection(rightPointsX, rightPointsY, rightPointsZ));
        }

        public Pair splitByZMedian(int median) {
            List<Point> leftPointsZ = pointsZ.subList(0, median+1);
            List<Point> rightPointsZ = pointsZ.subList(median+1, pointsZ.size());

            List<Point> leftPointsX = new ArrayList<>(pointsX);
            leftPointsX.removeAll(rightPointsZ);
            List<Point> rightPointsX = new ArrayList<>(pointsX);
            rightPointsX.removeAll(leftPointsZ);

            List<Point> leftPointsY = new ArrayList<>(pointsY);
            leftPointsY.removeAll(rightPointsZ);
            List<Point> rightPointsY = new ArrayList<>(pointsY);
            rightPointsY.removeAll(leftPointsZ);

            return new Pair(new PointCollection(leftPointsX, leftPointsY, leftPointsZ),
                    new PointCollection(rightPointsX, rightPointsY, rightPointsZ));
        }

        public int size() {
            return pointsX.size();
        }

    }

    public static class Pair {

        public PointCollection left;
        public PointCollection right;

        public Pair(PointCollection left, PointCollection right) {
            this.left = left;
            this.right = right;
        }

    }

}
