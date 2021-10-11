// Only the player object knows which room he is in and which items he has

package kea.adventure;

import java.util.ArrayList;

public class Player {

    private Room getCurrentRoom;
    private Room requestedRoom;
    private ArrayList itemsPlayer; // Player inventory

    private final int maxWeight = 25;
    private int strengthPoints = 100;

    public Player(Map map, Room start) {
        this.getCurrentRoom = start; //map.getStarterRoom();
        this.itemsPlayer = map.getInitialInventory();
    }

    // Player moves in the given direction or finds the way blocked

    public boolean changeRoom(String direction) {
        switch (direction) {
            case "N" -> requestedRoom = this.getCurrentRoom.getNorthRoom();
            case "E" -> requestedRoom = this.getCurrentRoom.getEastRoom();
            case "S" -> requestedRoom = this.getCurrentRoom.getSouthRoom();
            case "W" -> requestedRoom = this.getCurrentRoom.getWestRoom();
        }
        if (requestedRoom != null) {
            this.getCurrentRoom = requestedRoom; // move to new room
            return true;
        }
        return false;
    }

    public Room getCurrentRoom() {
        return this.getCurrentRoom;
    }

    // Player inventory list

    public ArrayList getPlayerItems() {
        return this.itemsPlayer;
    }

    // remove an item from inventory

    public void dropAnItem(Item itemDropped) {
        this.itemsPlayer.remove(itemDropped);
    }

    // add an item to inventory

    public void takeAnItem(Item itemTaken) {
        this.itemsPlayer.add(itemTaken);
    }

    public int getStrengthPoints() {
        return this.strengthPoints;
    }

    public void setStrengthPoints(int strengthPoints) {
        this.strengthPoints = strengthPoints;
    }

    public int getMaxWeight() {
        return this.maxWeight;
    }
}


