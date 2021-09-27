// Only the player object knows which room he is in

package kea.adventure;

public class Player {

    public Room currentRoom;
    private Room requestedRoom;

    public Player(Map map) {
        this.currentRoom = map.getStarterRoom();
    }

    // Player moves in the given direction or finds the way blocked

    public Room changeRoom(String direction) {
        switch (direction) {
            case "N" -> requestedRoom = this.currentRoom.getNorthRoom();
            case "E" -> requestedRoom = this.currentRoom.getEastRoom();
            case "S" -> requestedRoom = this.currentRoom.getSouthRoom();
            case "W" -> requestedRoom = this.currentRoom.getWestRoom();
        }
        if (requestedRoom != null) {
            this.currentRoom = requestedRoom; // move to new room
        }
        return requestedRoom; // or print blocked message if null
    }
}

