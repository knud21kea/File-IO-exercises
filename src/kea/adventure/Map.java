package kea.adventure;

public class Map {

    private Room[] rooms = new Room[10];

    public void buildMap() {

        //create all instances of rooms as an object array - data very basic

        rooms[1] = new Room("Room 1", "Looks like an entrance.");
        rooms[2] = new Room("Room 2", "Not much to see.");
        rooms[3] = new Room("Room 3", "The way straight seems blocked");
        rooms[4] = new Room("Room 4", "Looks empty");
        rooms[5] = new Room("Room 5", "This room looks special.");
        rooms[6] = new Room("Room 6", "You see nothing interesting");
        rooms[7] = new Room("Room 7", "What is here?...Oh, nothing.");
        rooms[8] = new Room("Room 8", "Seems to be several exits");
        rooms[9] = new Room("Room 9", "No beer here");
      //  rooms[0] = rooms[1]; //current room

        //Make connections - auto 2 way

        rooms[1].connectSouthNorth(rooms[4]);
        rooms[1].connectEastWest(rooms[2]);
        rooms[2].connectEastWest(rooms[3]);
        rooms[3].connectSouthNorth(rooms[6]);
        rooms[6].connectSouthNorth(rooms[9]);
        rooms[8].connectEastWest(rooms[9]);
        rooms[7].connectEastWest(rooms[8]);
        rooms[5].connectSouthNorth(rooms[8]);
        rooms[4].connectSouthNorth(rooms[7]);
    }

    public Room getStarterRoom() {
        return rooms[1];
    }

    public Room getSpecialRoom() {
        return rooms[5];
    }
}
