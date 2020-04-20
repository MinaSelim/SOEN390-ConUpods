package astar;

public class Destination {

    String mBuilding;
    String mFloor;
    String mRoom;

    /**
     *
     *  Destination object used to to hold data, with params
     */
    public Destination() {
        this.mBuilding = null;
        this.mFloor = null;
        this.mRoom = null;
    }

    /**
     *
     * @param building
     * @param floor
     * @param room
     *  Destination object used to to hold data, with params
     */
    Destination(String building, String floor, String room) {
        this.mBuilding = building;
        this.mFloor = floor;
        this.mRoom = room;
    }

    /**
     *
     * @return mBuilding parameter
     */
    public String getmBuilding() {
        return mBuilding;
    }

    /**
     *
     * @param mBuilding
     */
    public void setmBuilding(String mBuilding) {
        this.mBuilding = mBuilding;
    }

    /**
     *
     * @return mFloor parameter
     */
    public String getmFloor() {
        return mFloor;
    }

    /**
     *
     * @param mFloor
     */
    public void setmFloor(String mFloor) {
        this.mFloor = mFloor;
    }

    /**
     *
     * @return mRoom parameter
     */
    public String getmRoom() {
        return mRoom;
    }

    /**
     *
     * @param mRoom
     */
    public void setmRoom(String mRoom) {
        this.mRoom = mRoom;
    }

}
