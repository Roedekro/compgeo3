import java.io.Serializable;

public class KDTreeNode implements Serializable {

    public Point point;
    public KDTreeNode leftChild;
    public KDTreeNode rightChild;
    public Range region;

    public KDTreeNode(Range region, Point point) {
        this.region = region;
        this.point = point;
    }

    public KDTreeNode(Range region, KDTreeNode leftChild, KDTreeNode rightChild) {
        this.region = region;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

}
