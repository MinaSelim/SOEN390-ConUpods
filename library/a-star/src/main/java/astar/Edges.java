package astar;

public class Edges {

    int left;
    int right;
    int bottom;
    int top;

    private final int RATIO = 11;

    public Edges(int left, int right, int bottom, int top) {
        this.left = left * RATIO;
        this.right = right * RATIO + RATIO;
        this.bottom = bottom * RATIO - RATIO;
        this.top = top * RATIO;
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
