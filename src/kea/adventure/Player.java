package kea.adventure;

public class Player {

    private final Room[] rooms;

    //Here some attributes representing what the player knows or has
    //Eg if a player has found a blocked direction, then it could be included in the room description

    public Player(Room[] rooms) {
        this.rooms = rooms;
    }

    public void goNorth() {
        Room requestedRoom = rooms[0].getNorthRoom();
        if (requestedRoom == null) {
            System.out.println("That way is blocked.");
        } else {
            rooms[0] = requestedRoom;
        }
    }

    //method to set a room connection to be known
    //this is an extension to Room
    public void discoverPassage() {
    }

    //method to get the known status of a room connection
    //This is an extension to room
    public boolean isPassageDiscovered() {
        return false;
    }
}
