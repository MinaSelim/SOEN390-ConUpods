package astar;

import java.util.ArrayList;
import java.util.List;

public class Spot {

    private int mX;
    private int mY;
    private boolean mWall;
    private int mFloor;
    private String mBuilding;

//    f = total estimated cost of path through current node
//    g = cost so far to reach final node
//    h = estimated cost from start to goal. This is the heuristic part of the cost function, so it is like a guess.

    private double mF;
    private double mG;
    private double mH;
    private List<Spot> mNeighbors;
    private Spot mPrevious;

    /**
     *
     */
    public Spot(){

        this.mX = 0;
        this.mY = 0;
        this.mWall = false;

        this.mF = 0;
        this.mG = 0;
        this.mH = 0;
        this.mNeighbors = new ArrayList<>();
        this.mPrevious = null;

        this.mFloor = -1;
        this.mBuilding = null;
    }

    public Spot(int mX, int mY, boolean mWall) {

        this.mX = mX;
        this.mY = mY;
        this.mWall = mWall;

        this.mF = 0;
        this.mG = 0;
        this.mH = 0;
        this.mNeighbors = new ArrayList<>();
        this.mPrevious = null;

        this.mFloor = -1;
        this.mBuilding = null;

    }

    /**
     *
     * @param Previous
     */
    public void setPrevious(Spot Previous) {
        this.mPrevious = Previous;
    }

    /**
     *
     * @return previous Spot of current Spot
     */
    public Spot getPrevious() {
        return this.mPrevious;
    }

    /**
     *
     * @param neighbor
     * adds neighbours to Spot list
     */
    public void addNeighbor(Spot neighbor) {
        if (!this.equals(neighbor)) {
            this.mNeighbors.add(neighbor);
        }
    }

    /**
     *
     * @return list of neighbors of current Spot
     */
    public List<Spot> getNeighbors() {
        return this.mNeighbors;
    }

    /**
     *
     * @return X coordinate
     */
    public int getX() {
        return mX;
    }

    /**
     *
     * @return Y coordinate
     */
    public int getY() {
        return mY;
    }

    /**
     *
     * @return [x,y] array
     */
    public int[] getXY() {
        return new int[]{this.mX, this.mY};
    }

    /**
     *
     * @param b
     * Set boolean value for wall of current Spot
     */
    public void setWall(boolean b) {
        this.mWall = b;
    }

    /**
     *
     * @return checks it current point is a wall
     */
    public boolean isWall() {
        return this.mWall;
    }

    /**
     *
     * @return total cost of traversal
     */
    public double getF() {
        return mF;
    }

    /**
     * updates total cost of traversal to match current G and H values
     */
    public void updateF() {
        this.mF = this.mG + this.mH;
    }

    /**
     *
     * @return exact cost of current path
     */
    public double getG() {
        return mG;
    }

    /**
     *
     * @param mG
     * sets the exact cost of current path
     */
    public void setG(double mG) {
        this.mG = mG;
    }

    /**
     *
     * @return heuristic cost of the path
     */
    public double getH() {
        return mH;
    }

    /**
     *
     * @param mH
     * sets heuristic cost of the path
     */
    public void setH(double mH) {
        this.mH = mH;
    }

    /**
     *
     * @param b
     * @return checks if spots have same coordinates
     */
    public boolean equals(Spot b) {
        return this.mX == b.mX && this.mY == b.mY;
    }

    /**
     *
     * @return returns floor index
     */
    public int getFloor() {
        return mFloor;
    }

    /**
     *
     * @param mFloor
     * sets floor index
     */
    public void setFloor(int mFloor) {
        this.mFloor = mFloor;
    }

    /**
     *
     * @return building letter acronym
     */
    public String getBuilding() {
        return mBuilding;
    }

    /**
     *
     * @param mBuilding
     * sets building letter acronym
     */
    public void setBuilding(String mBuilding) {
        this.mBuilding = mBuilding;
    }

}
