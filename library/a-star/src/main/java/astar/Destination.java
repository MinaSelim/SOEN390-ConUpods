package astar;

public class Destination {

    String mBuilding;
    String mFloor;
    String mRoom;

    public Destination() {
        this.mBuilding = null;
        this.mFloor = null;
        this.mRoom = null;
    }

    Destination(String building, String floor, String room) {
        this.mBuilding = building;
        this.mFloor = floor;
        this.mRoom = room;
    }

    public String getmBuilding() {
        return mBuilding;
    }

    public void setmBuilding(String mBuilding) {
        this.mBuilding = mBuilding;
    }

    public String getmFloor() {
        return mFloor;
    }

    public void setmFloor(String mFloor) {
        this.mFloor = mFloor;
    }

    public String getmRoom() {
        return mRoom;
    }

    public void setmRoom(String mRoom) {
        this.mRoom = mRoom;
    }

}
