package kea.adventure;

import java.util.ArrayList;
import java.util.List;

public class Room {

    // "known" attribute is used to update room description when a direction has been tried and found blocked

    private final String roomName, roomDescription;
    private Room north, east, west, south;
    private boolean knownNorth, knownEast, knownSouth, knownWest;
    public ArrayList<Item> items = new ArrayList<>(); // List of Item objects that are in the room


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

    public ArrayList<Item> getRoomItems() {
        return this.items;
    }

    // Add item to room
    public void addItemToRoom(Item item) {
        this.items.add(item);
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

    // 4 methods to find a room in the given direction and note that the player has now tried to go there

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

    // 4 methods to check if the player has previously tried to go the given direction

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

    @Override
    public String toString() {
        return roomName + "{" +
                "items=" + items +
                '}';
    }
}
