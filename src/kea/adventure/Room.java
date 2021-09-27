package kea.adventure;

public class Room {

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

    public void connectSouthNorth(Room south) {
        this.south = south;
        south.north = this;
    }

    public void connectEastWest(Room east) {
        this.east = east;
        east.west = this;
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
