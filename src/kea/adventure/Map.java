/*   ___________________     __________________     __________________
     |                  |    |                 |    |                 |
     |    Courtyard     | == |   Chancellery   | == |    Ballroom     |
     |__________________|    |_________________|    |_________________|
              ||                                            ||
     ___________________     __________________     __________________
     |                  |    |                 |    |                 |
     |   Banquet room   |    |    Catacomb     |    | Royal apartment |
     |__________________|    |_________________|    |_________________|
              ||                     ||                     ||
     ___________________     __________________     __________________
     |                  |    |                 |    |                 |
     |    Little hall   | == |    Casements    | == |     Chapel      |
     |__________________|    |_________________|    |_________________|
*/

package kea.adventure;

import java.util.ArrayList;

public class Map {

    private Room room1, room2, room3, room4, room5, room6, room7, room8, room9;

    public Map() {
    }

    public void buildMap() {

        // Create all instances of rooms

        room1 = new Room("a courtyard", "Walls here are clad with sandstone, and the surrounding roofs with copper sheeting.");
        room2 = new Room("the chancellery", "A luxurious room with a magnificent canopy which is woven in gold and silver.");
        room3 = new Room("the ballroom", "A great hall where the loft is panelled with elaborate wood carvings, paintings and gilding.");
        room4 = new Room("a banquet room", "Polished silver and sparkling glass. Brass chandeliers fitted with honey-scented candles.");
        room5 = new Room("the catacombs", "Dark and foreboding with a brooding presence. A special place.");
        room6 = new Room("one of the royal apartments", "A richly decorated room with ceiling paintings, stone portals and fireplaces.");
        room7 = new Room("a small hall", "The walls are clad with seven intricately woven tapestries.");
        room8 = new Room("the casements", "Gloomy, cold and damp. There are signs that horses and soldiers have been here.");
        room9 = new Room("a chapel", "A small place of worship with well preserved original altar, gallery, and pews.");

        // Make connections - auto 2 way

        room1.connectSouthNorth(room4);
        room1.connectEastWest(room2);
        room2.connectEastWest(room3);
        room3.connectSouthNorth(room6);
        room6.connectSouthNorth(room9);
        room8.connectEastWest(room9);
        room7.connectEastWest(room8);
        room5.connectSouthNorth(room8);
        room4.connectSouthNorth(room7);
    }

    // Add starting items to the rooms

    public void addStarterItems(){
        Item item1 = new Item("A brass lamp", 1);
        Item item2 = new Item("A small knife", 1);
        Item item3 = new Item("An empty bottle", 1);
        Item item4 = new Item("A silver key", 1);
        Item item5 = new Item("A gold key", 1);
        Item item6 = new Item("A gold bar", 1);
        Item item7 = new Item("An apple", 1);
        Item item8 = new Item("Some water", 1);
        Item item9 = new Item("An old parchment", 1);
        room2.addItemToRoom(item1);
        room3.addItemToRoom(item2);
        room3.addItemToRoom(item3);
        room4.addItemToRoom(item7);
        room4.addItemToRoom(item4);
        room4.addItemToRoom(item8);
        room6.addItemToRoom(item5);
        room7.addItemToRoom(item9);
        room9.addItemToRoom(item1);
    }

    // Default start is in room 1

    public Room getStarterRoom() {
        return room1;
    }

    // Something special about room 5

    public Room getSpecialRoom() {
        return room5;
    }

    public ArrayList<String> getstarterItems() {
        return null;
    }
}
