package astar;

public class Edges {

    private int left;
    private int right;
    private int bottom;
    private int top;

    private final int RATIO = 11;

    public Edges(int left, int right, int top, int bottom) {
        this.left = left * RATIO;
        this.right = right * RATIO + RATIO;
        this.top = top * RATIO;
        this.bottom = bottom * RATIO + RATIO;
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
