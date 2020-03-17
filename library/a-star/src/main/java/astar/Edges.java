package astar;

public class Edges {

    int left;
    int right;
    int bottom;
    int top;

    public Edges(int left, int right, int bottom, int top, int ratio) {
        this.left = left * ratio;
        this.right = right * ratio + ratio;
        this.bottom = bottom * ratio - ratio;
        this.top = top * ratio;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }

}
