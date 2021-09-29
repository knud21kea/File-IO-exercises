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

public class Map {

    private Room courtyard, chancellery, ballroom, banquet, catacombs, apartment, hall, casements, chapel;

    public Map() {
    }

    public void buildMap() {

        // Create all instances of Room class

        courtyard = new Room("a courtyard", "Walls here are clad with sandstone, and the surrounding roofs with copper sheeting.");
        chancellery = new Room("the chancellery", "A luxurious room with a magnificent canopy which is woven in gold and silver.");
        ballroom = new Room("the ballroom", "A great hall where the loft is panelled with elaborate wood carvings, paintings and gilding.");
        banquet = new Room("a banquet room", "Polished silver and sparkling glass. Brass chandeliers fitted with honey-scented candles.");
        catacombs = new Room("the catacombs", "Dark and foreboding with a brooding presence. A special place.");
        apartment = new Room("one of the royal apartments", "A richly decorated room with ceiling paintings, stone portals and fireplaces.");
        hall = new Room("a small hall", "The walls are clad with seven intricately woven tapestries.");
        casements = new Room("the casements", "Gloomy, cold and damp. There are signs that horses and soldiers have been here.");
        chapel = new Room("a chapel", "A small place of worship with well preserved original altar, gallery, and pews.");

        // Make connections - auto 2 way

        courtyard.connectSouthNorth(banquet);
        courtyard.connectEastWest(chancellery);
        chancellery.connectEastWest(ballroom);
        ballroom.connectSouthNorth(apartment);
        apartment.connectSouthNorth(chapel);
        casements.connectEastWest(chapel);
        hall.connectEastWest(casements);
        catacombs.connectSouthNorth(casements);
        banquet.connectSouthNorth(hall);
    }

    public void addStarterItems() {

        // Create all instances of Item class

        Item brassLamp = new Item("A brass lamp", 1);
        Item smallKnife = new Item("A small knife", 1);
        Item emptyBottle = new Item("An empty bottle", 1);
        Item silverKey = new Item("A silver key", 1);
        Item goldKey = new Item("A gold key", 1);
        Item goldBar = new Item("A gold bar", 1);
        Item anApple = new Item("An apple", 1);
        Item holyWater = new Item("Some holy water", 1);
        Item oldParchment = new Item("An old parchment", 1);

        // Place items in rooms

        chancellery.addItemToRoom(brassLamp);
        ballroom.addItemToRoom(smallKnife);
        ballroom.addItemToRoom(emptyBottle);
        banquet.addItemToRoom(anApple);
        banquet.addItemToRoom(silverKey);
        banquet.addItemToRoom(goldBar);
        apartment.addItemToRoom(goldKey);
        hall.addItemToRoom(oldParchment);
        chapel.addItemToRoom(holyWater);
    }

    // Default start is in room 1

    public Room getStarterRoom() {
        return courtyard;
    }

    // Something special about room 5

    public Room getSpecialRoom() {
        return catacombs;
    }
}
