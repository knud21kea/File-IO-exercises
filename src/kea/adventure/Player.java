package kea.adventure;

public class Player {

    public Room currentRoom;
    private Room requestedRoom;

    public Player(Map map) {
        this.currentRoom = map.getStarterRoom();
    }

    public Room goNorth() {
        requestedRoom = this.currentRoom.getNorthRoom();
        if (requestedRoom != null) {
            this.currentRoom = requestedRoom;
        }
        return requestedRoom;
    }

    public Room goEast() {
        requestedRoom = this.currentRoom.getEastRoom();
        if (requestedRoom != null) {
            this.currentRoom = requestedRoom;
        }
        return requestedRoom;
    }

    public Room goSouth() {
        requestedRoom = this.currentRoom.getSouthRoom();
        if (requestedRoom != null) {
            this.currentRoom = requestedRoom;
        }
        return requestedRoom;
    }

    public Room goWest() {
        requestedRoom = this.currentRoom.getWestRoom();
        if (requestedRoom != null) {
            this.currentRoom = requestedRoom;
        }
        return requestedRoom;
    }
}
