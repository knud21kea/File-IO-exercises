
/* *************************************************************************************************

                                   --  Adventure (part 2) --

DAT21 Java project (compulsory group exercise).
 Developers: Graham Heaven and Lasse Bøgeskov-Jensen, September 2021.

A simple text-based adventure game inspired by "Colossal Cave Adventure",
 by William Crowther and Don Woods, from 1976-1977.

A single player navigates through a small maze of rooms using a simple text parser interface.
In each room it is possible to get limited information about the room, and move to connected rooms.
There is an achievable goal using the available commands.

************************************************************************************************** */

package kea.adventure;

import java.util.ArrayList;
import java.util.Scanner;

public class Adventure {

    private final Scanner input = new Scanner(System.in);
    private final Map map;
    private final Player player;

    public static void main(String[] args) {

        Adventure game = new Adventure();
        game.playGame();
    }

    public Adventure() {

        /*
         Initialise rooms and make connections (auto 2-way joins so no 1-way passages)
         There could be a choice of maps - we have default
         Initialise player object - there could be a user input for player name
         Added starting objects in rooms and players inventory for test
         */

        map = new Map();
        map.buildMap();
        map.addStarterItems();
        player = new Player(map);
    }

    public void playGame() {

        /*
        Introduce game, process player inputs until game is closed - or won
        There could be a choice of starting locations - we have default
        */

        System.out.println("""
                \033[0;33m
                Welcome to this text based Adventure.
                You can try to move North, East, South or West
                It is also possible, nae recommended, to explore your current location. You may find blocked directions, or items lying around.
                Only the weak would think of quitting, but that is always an option.
                To see a list of available commands type help.
                        
                Find the long lost hero or fail trying. How will it end...?\033[0m""");

        // Start game or exit

        System.out.print("Are you ready to start the adventure? ");
        String menuOption = input.nextLine().toUpperCase();
        if (!menuOption.startsWith("Y")) {
            menuOption = "X";
        } else {
            menuOption = "START";
        }
        // Get inputs until user types exit/x or quit/q or game is won

        while (!menuOption.equals("X") && !menuOption.equals("EXIT")) {
            outputBasicDescription();
            boolean canMove = true; //used to only print blocked if user tries a blocked route
            System.out.print("What do you want to do? ");
            menuOption = input.nextLine().toUpperCase();

            // Player choice with multiple command forms - had to drop the case switch

            if (menuOption.equals("EXIT") || menuOption.equals("X") || menuOption.equals("QUIT") || menuOption.equals("Q")) {
                menuOption = endMessage();
            } else if (menuOption.equals("HELP") || menuOption.equals("H") || menuOption.equals("INFO")) {
                getHelp();
            } else if (menuOption.equals("INVENTORY") || menuOption.equals("INV") || menuOption.equals("I")) {
                outputInventory();
            } else if (menuOption.equals("EXPLORE") || menuOption.equals("LOOK") || menuOption.equals("L")) {
                menuOption = lookAround(player.currentRoom, map.getSpecialRoom());
            } else if (menuOption.equals("DROP") || menuOption.equals("D")) {
                dropSomething();
            } else if (menuOption.startsWith("DROP ") || menuOption.startsWith("D ")) {
                dropItem(menuOption);
            } else if (menuOption.equals("TAKE") || menuOption.equals("T")) {
                takeSomething();
            } else if (menuOption.startsWith("TAKE ") || menuOption.startsWith("T ")) {
                takeItem(menuOption);
            } else if (menuOption.equals("GO NORTH") || menuOption.equals("NORTH") || menuOption.equals("N")) {
                canMove = player.changeRoom("N");
            } else if (menuOption.equals("GO EAST") || menuOption.equals("EAST") || menuOption.equals("E")) {
                canMove = player.changeRoom("E");
            } else if (menuOption.equals("GO SOUTH") || menuOption.equals("SOUTH") || menuOption.equals("S")) {
                canMove = player.changeRoom("S");
            } else if (menuOption.equals("GO WEST") || menuOption.equals("WEST") || menuOption.equals("W")) {
                canMove = player.changeRoom("W");
            } else {
                unknownCommand(menuOption);
            }
            if (!canMove) {
                System.out.println("\033[0;31mThat way is blocked.\033[0m");
            }
        }
    }

    // Help info - only with the short commands

    public void getHelp() {
        System.out.println("""
                \033[0;33m
                You can use these commands, with some variations:
                H - Help (this - see what I did there?)
                L - Look around (room description - always worth a try
                I - List inventory
                N - Go North
                E - Go East
                S - Go South
                W - Go West
                X - Exit\033[0m""");
    }

    // Give up - option to back out of quitting

    public String endMessage() {
        System.out.print("\033[0;33m\nAre you sure you want to quit? (y/n)\033[0m ");
        String menuOption = input.nextLine().toUpperCase();
        if (menuOption.startsWith("Y")) {
            System.out.println("\033[0;33mReally? Hope to see you again soon. Bye.\033[0m");
            return "EXIT";
        } else {
            return "CONTINUE";
        }
    }

    // Invalid input

    public void unknownCommand(String menuOption) {
        System.out.println("\033[0;33mI do not understand \"" + menuOption + "\".\033[0m");
    }

    // Room description with an Easter egg - a way to beat the game

    public String lookAround(Room currentRoom, Room special) {
        if (currentRoom != special) {
            System.out.println("\nLooking around.");
            if (currentRoom.getKnownNorth() && currentRoom.getNorthRoom() == null) {
                System.out.println("\033[0;34mThe way North is blocked.\033[0m");
            }
            if (currentRoom.getKnownEast() && currentRoom.getEastRoom() == null) {
                System.out.println("\033[0;34mThe way East is blocked.\033[0m");
            }
            if (currentRoom.getKnownSouth() && currentRoom.getSouthRoom() == null) {
                System.out.println("\033[0;34mThe way South is blocked.\033[0m");
            }
            if (currentRoom.getKnownWest() && currentRoom.getWestRoom() == null) {
                System.out.println("\033[0;34mThe way West is blocked.\033[0m");
            }
            outputDescription();
            return "CONTINUE";
        } else {
            System.out.println("\nCongratulations, you have found the sleeping Holge Danske in his Kronborg home.");
            return "EXIT";
        }
    }

    // Brief info without looking around

    public void outputBasicDescription() {
        System.out.print("\nYou are in " + player.currentRoom.getRoomName() + ": ");
        System.out.println(player.currentRoom.getRoomDescription());
    }

    // Player inventory - with formatted output

    public void outputInventory() {
        System.out.print("\033[0;34m");
        ArrayList<Item> objects = player.getPlayerItems();
        int size = objects.size();
        System.out.print("You are carrying ");
        if (size == 0) {
            System.out.println("nothing of use.");
        } else if (size == 1) {
            System.out.println("1 item: " + objects.get(0).getItemName() + ".");
        } else {
            System.out.print(size + " items: " + objects.get(0).getItemName());
            for (int i = 1; i < size - 1; i++) {
                System.out.print(", " + makeFirstLetterLowerCase(objects.get(i).getItemName()));
            }
            System.out.println(" and " + makeFirstLetterLowerCase(objects.get(size - 1).getItemName()) + ".");
        }
        System.out.print("\033[0m");
    }

    // Room inventory - with formatted output

    public void outputDescription() {
        System.out.print("\033[0;34m");
        ArrayList<Item> objects = player.currentRoom.getRoomItems();
        int size = objects.size();
        System.out.print("You can see ");
        if (size == 0) {
            System.out.println("nothing of interest.");
        } else if (size == 1) {
            System.out.println("1 item: " + objects.get(0).getItemName() + ".");
        } else {
            System.out.print(size + " items: " + objects.get(0).getItemName());
            for (int i = 1; i < size - 1; i++) {
                System.out.print(", " + makeFirstLetterLowerCase(objects.get(i).getItemName()));
            }
            System.out.println(" and " + makeFirstLetterLowerCase(objects.get(size - 1).getItemName()) + ".");
        }
        System.out.print("\033[0m");
    }


    public String makeFirstLetterLowerCase(String itemName) {
        return itemName.substring(0, 1).toLowerCase() + itemName.substring(1);
    }

    // Overload dropItem when user types only "drop"

    public void dropSomething() {
        System.out.print("Hmmm. Which item do you want to drop? ");
        String itemToDrop = input.nextLine().toUpperCase();
        Item foundItem = getMatchingItemNames(itemToDrop, true);
        if (foundItem != null) {
            player.dropAnItem(foundItem); // first and only match removed from player
            player.currentRoom.addItemToRoom(foundItem); // and added to current room
        }
    }

    // Takes item from room and adds to player if input string is matched to exactly 1 item

    public void dropItem(String menuItem) {
        if (menuItem.startsWith("DROP ")) {
            menuItem = menuItem.substring(5); // command was "drop string"
        } else {
            menuItem = menuItem.substring(2); // command was "d string"
        }
        Item foundItem = getMatchingItemNames(menuItem, true);
        if (foundItem != null) {
            player.dropAnItem(foundItem); // first and only match removed from player
            player.currentRoom.addItemToRoom(foundItem); // and added to current room
        }
    }

    // Overload take item when user types only "take"

    public void takeSomething() {
        System.out.print("Hmmm. Which item do you want to pick up? ");
        String itemToDrop = input.nextLine().toUpperCase();
        Item foundItem = getMatchingItemNames(itemToDrop, false);
        if (foundItem != null) {
            player.takeAnItem(foundItem); // first and only match removed from player
            player.currentRoom.takeItemFromRoom(foundItem); // and added to current room
        }
    }

    // Takes item from room and adds to player if input string is matched to exactly 1 item

    public void takeItem(String menuItem) {
        if (menuItem.startsWith("TAKE ")) {
            menuItem = menuItem.substring(5); // command was "take string"
        } else {
            menuItem = menuItem.substring(2); // command was "t string"
        }
        Item foundItem = getMatchingItemNames(menuItem, false);
        if (foundItem != null) {
            player.takeAnItem(foundItem); // first and only match removed from player
            player.currentRoom.takeItemFromRoom(foundItem); // and added to current room
        }
    }

    // common code for both player inventory and room inventory with boolean control
    // loops through the objects in the given list and check if search is found in an item name
    // there may be found no, one or multiple items

    public Item getMatchingItemNames(String searchFor, boolean drop) {
        String text1, text2; // only minor difference between drop and take
        ArrayList<Item> givenInventory;
        if (drop) {
            givenInventory = player.getPlayerItems();
            text1 = "Hmmm. You do not seem to have ";
            text2 = "\033[0;34mDropping the ";
        } else {
            givenInventory = player.currentRoom.getRoomItems();
            text1 = "Hmmm. I cannot seem to see ";
            text2 = "\033[0;34mTaking the ";
        }
        ArrayList<String> foundItemNames = new ArrayList<>();
        Item foundItem = null; // default no item match
        int numberOfNames = givenInventory.size();
        int countItems = 0;
        for (int i = 0; i < numberOfNames; i++) {
            if (givenInventory.get(i).getItemName().toUpperCase().contains(searchFor)) {
                countItems++;
                foundItem = givenInventory.get(i); // any matching item
                foundItemNames.add(foundItem.getItemName());
            }
        }
        if (countItems == 0) {
            System.out.println(text1 + searchFor + ".");
        } else if (countItems == 1) {
            int firstSpace = foundItemNames.get(0).indexOf(" ");
            System.out.println(text2 + foundItemNames.get(0).substring(firstSpace + 1) + ".\033[0m");
        } else {
            foundItem = null; // need to reset when more than 1 match
            System.out.print("I found " + countItems + " items: " + foundItemNames.get(0));
            for (int i = 1; i < countItems - 1; i++) {
                System.out.print(", " + makeFirstLetterLowerCase(foundItemNames.get(i)));
            }
            System.out.println(" and " + makeFirstLetterLowerCase(foundItemNames.get(countItems - 1)) + "?");
            System.out.println("Could you give me more of a clue?");
        }
        return foundItem; // null unless only one match
    }
}