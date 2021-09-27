package kea.adventure;

public class Map {

    private final Room[] rooms = new Room[10];

    public void buildMap() {

        // Create all instances of rooms as an object array
        // Rooms[0] is no longer used, but indices are retained to match room IDs.

        rooms[1] = new Room("Courtyard", "Walls here are clad with sandstone, and the surrounding roofs with copper sheeting.");
        rooms[2] = new Room("Chancellery", "A luxurious room with a magnificent canopy which is woven in gold and silver.");
        rooms[3] = new Room("Ballroom", "A great hall where the loft is panelled with elaborate wood carvings, paintings and gilding.");
        rooms[4] = new Room("Banquet room", "Polished silver and sparkling glass. Brass chandeliers fitted with honey-scented candles.");
        rooms[5] = new Room("Catacomb", "Dark and foreboding with a brooding presence. A special place.");
        rooms[6] = new Room("Royal apartment", "A richly decorated room with ceiling paintings, stone portals and fireplaces.");
        rooms[7] = new Room("Little hall", "The walls are clad with seven intricately woven tapestries.");
        rooms[8] = new Room("Casements", "Gloomy, cold and damp. There are signs that horses and soldiers have been here.");
        rooms[9] = new Room("Chapel", "A small place of worship with well preserved original altar, gallery, and pews.");

        // Make connections - auto 2 way

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

    // Default start is in room 1

    public Room getStarterRoom() {
        return rooms[1];
    }

    // Something special about room 5

    public Room getSpecialRoom() {
        return rooms[5];
    }
}
