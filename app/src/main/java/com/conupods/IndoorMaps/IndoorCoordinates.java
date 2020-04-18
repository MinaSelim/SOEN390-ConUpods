package com.conupods.IndoorMaps;

public class IndoorCoordinates {

    private int mX;
    private int mY;
    private int mFloor;
    private String mRoom;

    public IndoorCoordinates(int x, int y, int floor, String room) {
        this.mX = x;
        this.mY = y;
        this.mFloor = floor;
        this.mRoom = room;
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

    public String getRoom() {
        return this.mRoom;
    }

}
