package kea.adventure;

public class Room {

    // known attribute is used to update room description when a direction has been tried and found blocked
    private final String roomName, roomDescription;
    private Room north, east, west, south;
    private boolean knownNorth, knownEast, knownSouth, knownWest;

    public Room(String roomName, String roomDescription) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    // connect two rooms South to North
    public void connectSouthNorth(Room south) {
        this.south = south;
        south.north = this;
    }

    // connect two rooms East to West
    public void connectEastWest(Room east) {
        this.east = east;
        east.west = this;
    }

    public Room getNorthRoom() {
        this.knownNorth = true;
        return north;
    }

    public Room getEastRoom() {
        this.knownEast = true;
        return east;
    }

    public Room getSouthRoom() {
        this.knownSouth = true;
        return south;
    }

    public Room getWestRoom() {
        this.knownWest = true;
        return west;
    }

    public boolean getKnownNorth() {
        return knownNorth;
    }

    public boolean getKnownEast() {
        return knownEast;
    }

    public boolean getKnownSouth() {
        return knownSouth;
    }

    public boolean getKnownWest() {
        return knownWest;
    }
}
