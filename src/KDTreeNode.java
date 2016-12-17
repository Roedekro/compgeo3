public class KDTreeNode {

    public Point point;
    public KDTreeNode leftChild;
    public KDTreeNode rightChild;
    public Integer line;

    public KDTreeNode(Point point) {
        this.point = point;
    }

    public KDTreeNode(int line, KDTreeNode leftChild, KDTreeNode rightChild) {
        this.line = line;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

}
