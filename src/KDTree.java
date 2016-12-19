import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KDTree {

    public static KDTreeNode buildKDTree(List<Point> points) {
        PointCollection pc = new PointCollection(points);
        return recurBuildKDTree(null, pc, 0);
    }

    public static KDTreeNode recurBuildKDTree(Range parentRange, PointCollection points, int depth) {
        // Simply return a leaf node if there's only 1 point
        if (points.size() == 1) return new KDTreeNode(parentRange, points.pointsX.get(0));

        // Setup the range if no parent range has been provided
        if (parentRange == null) {
            parentRange = new Range(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE,
                    Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        // Detect the depth dimension
        Range leftRange = null;
        Range rightRange = null;
        Pair pair = null;
        int medianIndex = (int)Math.ceil((double)points.pointsX.size() / 2) - 1;
        if (depth % 3 == 0) {
            int medianValue = points.pointsX.get(medianIndex).x;
            leftRange = new Range(parentRange.x1, medianValue,
                    parentRange.y1, parentRange.y2, parentRange.z1, parentRange.z2);
            rightRange = new Range(medianValue+1, parentRange.x2,
                    parentRange.y1, parentRange.y2, parentRange.z1, parentRange.z2);
            pair = points.splitByXMedian(medianIndex);
        } else if (depth % 3 == 1) {
            int medianValue = points.pointsY.get(medianIndex).y;
            leftRange = new Range(parentRange.x1, parentRange.x2,
                    parentRange.y1, medianValue, parentRange.z1, parentRange.z2);
            rightRange = new Range(parentRange.x1, parentRange.x2,
                    medianValue+1, parentRange.y2, parentRange.z1, parentRange.z2);
            pair = points.splitByYMedian(medianIndex);
        } else if (depth % 3 == 2) {
            int medianValue = points.pointsZ.get(medianIndex).z;
            leftRange = new Range(parentRange.x1, parentRange.x2, parentRange.y1,
                    parentRange.y2, parentRange.z1, medianValue);
            rightRange = new Range(parentRange.x1, parentRange.x2, parentRange.y1,
                    parentRange.y2, medianValue+1, parentRange.z2);
            pair = points.splitByZMedian(medianIndex);
        }

        // Create left and right nodes
        KDTreeNode vLeft = recurBuildKDTree(leftRange, pair.left, depth+1);
        KDTreeNode vRight = recurBuildKDTree(rightRange, pair.right, depth+1);

        // Create the root node for this recursion step
        return new KDTreeNode(parentRange, vLeft, vRight);
    }

    public static List<Point> searchKDTree(KDTreeNode root, Range range) {
        // Check if the root is a leaf node
        if (root.point != null) {
            if (range.contains(root.point)) {
                return new ArrayList<>(Arrays.asList(root.point));
            }
            return new ArrayList<>();
        }

        // Check if the region of the left child is contained in or intersects the range
        List<Point> leftPoints = new ArrayList<>();
        if (range.contains(root.leftChild.region)) {
            leftPoints = getLeafNodes(root.leftChild);
        } else if (root.leftChild.region.intersects(range)) {
            leftPoints = searchKDTree(root.leftChild, range);
        }

        // Check if the region of the right child is contained in or intersects the range
        List<Point> rightPoints = new ArrayList<>();
        if (range.contains(root.rightChild.region)) {
            rightPoints = getLeafNodes(root.rightChild);
        } else if (root.rightChild.region.intersects(range)) {
            rightPoints = searchKDTree(root.rightChild, range);
        }

        // Merge the two results and return
        leftPoints.addAll(rightPoints);
        return leftPoints;
    }

    public static List<Point> getLeafNodes(KDTreeNode node) {
        if (node.point != null) return new ArrayList<>(Arrays.asList(node.point));
        List<Point> leftPoints = getLeafNodes(node.leftChild);
        List<Point> rightPoints = getLeafNodes(node.rightChild);
        leftPoints.addAll(rightPoints);
        return leftPoints;
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
            Point medianPoint = pointsX.get(median);

            List<Point> leftPointsX = new ArrayList<>();
            List<Point> rightPointsX = new ArrayList<>();
            for (Point p : pointsX) {
                if (p.x <= medianPoint.x) {
                    leftPointsX.add(p);
                } else {
                    rightPointsX.add(p);
                }
            }

            List<Point> leftPointsY = new ArrayList<>();
            List<Point> rightPointsY = new ArrayList<>();
            for (Point p : pointsY) {
                if (p.x <= medianPoint.x) {
                    leftPointsY.add(p);
                } else {
                    rightPointsY.add(p);
                }
            }

            List<Point> leftPointsZ = new ArrayList<>();
            List<Point> rightPointsZ = new ArrayList<>();
            for (Point p : pointsZ) {
                if (p.x <= medianPoint.x) {
                    leftPointsZ.add(p);
                } else {
                    rightPointsZ.add(p);
                }
            }

            return new Pair(new PointCollection(leftPointsX, leftPointsY, leftPointsZ),
                    new PointCollection(rightPointsX, rightPointsY, rightPointsZ));
        }

        public Pair splitByYMedian(int median) {
            Point medianPoint = pointsY.get(median);

            List<Point> leftPointsY = new ArrayList<>();
            List<Point> rightPointsY = new ArrayList<>();
            for (Point p : pointsY) {
                if (p.y <= medianPoint.y) {
                    leftPointsY.add(p);
                } else {
                    rightPointsY.add(p);
                }
            }

            List<Point> leftPointsX = new ArrayList<>();
            List<Point> rightPointsX = new ArrayList<>();
            for (Point p : pointsX) {
                if (p.y <= medianPoint.y) {
                    leftPointsX.add(p);
                } else {
                    rightPointsX.add(p);
                }
            }

            List<Point> leftPointsZ = new ArrayList<>();
            List<Point> rightPointsZ = new ArrayList<>();
            for (Point p : pointsZ) {
                if (p.y <= medianPoint.y) {
                    leftPointsZ.add(p);
                } else {
                    rightPointsZ.add(p);
                }
            }

            return new Pair(new PointCollection(leftPointsX, leftPointsY, leftPointsZ),
                    new PointCollection(rightPointsX, rightPointsY, rightPointsZ));
        }

        public Pair splitByZMedian(int median) {
            Point medianPoint = pointsZ.get(median);

            List<Point> leftPointsZ = new ArrayList<>();
            List<Point> rightPointsZ = new ArrayList<>();
            for (Point p : pointsZ) {
                if (p.z <= medianPoint.z) {
                    leftPointsZ.add(p);
                } else {
                    rightPointsZ.add(p);
                }
            }

            List<Point> leftPointsX = new ArrayList<>();
            List<Point> rightPointsX = new ArrayList<>();
            for (Point p : pointsX) {
                if (p.z <= medianPoint.z) {
                    leftPointsX.add(p);
                } else {
                    rightPointsX.add(p);
                }
            }

            List<Point> leftPointsY = new ArrayList<>();
            List<Point> rightPointsY = new ArrayList<>();
            for (Point p : pointsY) {
                if (p.z <= medianPoint.z) {
                    leftPointsY.add(p);
                } else {
                    rightPointsY.add(p);
                }
            }

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
