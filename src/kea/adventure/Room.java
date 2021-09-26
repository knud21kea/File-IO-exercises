package kea.adventure;

public class Room {

    private Room north;
    private Room east;
    private Room west;
    private Room south;
    private final String roomName;
    private final String roomDescription;

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
        return north;
    }

    public Room getEastRoom() {
        return east;
    }

    public Room getSouthRoom() {
        return south;
    }

    public Room getWestRoom() {
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
}
