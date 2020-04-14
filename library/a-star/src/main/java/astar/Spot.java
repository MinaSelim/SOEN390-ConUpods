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

    public void setPrevious(Spot Previous) {
        this.mPrevious = Previous;
    }

    public Spot getPrevious() {
        return this.mPrevious;
    }

    public void addNeighbor(Spot neighbor) {
        if (!this.equals(neighbor)) {
            this.mNeighbors.add(neighbor);
        }
    }

    public List<Spot> getNeighbors() {
        return this.mNeighbors;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int[] getXY() {
        return new int[]{this.mX, this.mY};
    }

    public void setWall(boolean b) {
        this.mWall = b;
    }

    public boolean isWall() {
        return this.mWall;
    }

    public double getF() {
        return mF;
    }

    public void updateF() {
        this.mF = this.mG + this.mH;
    }

    public double getG() {
        return mG;
    }

    public void setG(double mG) {
        this.mG = mG;
    }

    public double getH() {
        return mH;
    }

    public void setH(double mH) {
        this.mH = mH;
    }

    public boolean equals(Spot b) {
        return this.mX == b.mX && this.mY == b.mY;
    }

    public int getFloor() {
        return mFloor;
    }

    public void setFloor(int mFloor) {
        this.mFloor = mFloor;
    }

    public String getBuilding() {
        return mBuilding;
    }

    public void setBuilding(String mBuilding) {
        this.mBuilding = mBuilding;
    }

}
