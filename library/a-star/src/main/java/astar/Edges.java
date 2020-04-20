package astar;

public class Edges {

    private int left;
    private int right;
    private int bottom;
    private int top;

    /**
     * Ratio is hardcoded from the ratio
     * of the pictures and the grid set in the metadata
     */
    private final int RATIO = 11;


    /**
     *
     * @param left
     * @param right
     * @param top
     * @param bottom
     */
    public Edges(int left, int right, int top, int bottom) {
        this.left = left * RATIO;
        this.right = right * RATIO + RATIO;
        this.top = top * RATIO;
        this.bottom = bottom * RATIO + RATIO;
    }

    /**
     *
     * @return left most coordinate of square
     */
    public int getLeft() {
        return left;
    }

    /**
     *
     * @return right most coordinate of square
     */
    public int getRight() {
        return right;
    }


    /**
     *
     * @return bottom most coordinate of square
     */
    public int getBottom() {
        return bottom;
    }

    /**
     *
     * @return top most coordinate of square
     */
    public int getTop() {
        return top;
    }

}
