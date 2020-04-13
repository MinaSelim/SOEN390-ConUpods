package com.conupods.IndoorMaps;

public class IndoorCoordinates {

    private int mX;
    private int mY;
    private int mFloor;

    public IndoorCoordinates(int x, int y, int floor){
        this.mX = x;
        this.mY = y;
        this.mFloor = floor;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getFloor() {
        return this.mFloor;
    }

}
