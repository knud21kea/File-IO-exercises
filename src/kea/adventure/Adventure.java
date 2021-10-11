
/* *************************************************************************************************

                                   --  Adventure (part 2) --

DAT21 Java project (compulsory group exercise).
 Developers: Graham Heaven and Lasse Bøgeskov-Jensen, September 2021.

A simple text-based adventure game inspired by "Colossal Cave Adventure",
 by William Crowther and Don Woods, from 1976-1977.

Version 1:

A single player navigates through a small maze of rooms using a simple text parser interface.
In each room it is possible to get limited information about the room, and move to connected rooms.

There is an achievable goal using the available commands.

Change log for version 2:

Added items that can be carried from room to room, dropped or taken.
Added strength points that are lost by doing actions and gained by resting.
Added weights to items.
Added max carrying weight for player, meaning sometimes something must be dropped to take something else.
Added more commands: Inventory player, Inventory room, Take item, Drop item, Show cheat sheet.
Added mission that requires successful combination of available commands. Player can also die in 2 different ways.
Added music class
Added context colours for text

************************************************************************************************** */

package kea.adventure;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Adventure {

    private final Scanner input = new Scanner(System.in);
    private final Map map;
    private final Player player, holger;
    private boolean canMove;

    public static void main(String[] args) {
        String filepath = "GameOfThrones.wav";
        Music musicObject = new Music();
        musicObject.playMusic(filepath);

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
        player = new Player(map, map.courtyard);
        holger = new Player(map, map.catacombs);
    }

    public void playGame() {

        /*
        Introduce game, process player inputs until game is closed - or won
        There could be a choice of starting locations - we have default
        */

        System.out.println("""
                \033[4;33m
                Welcome to this text based Adventure.\033[0;33m
                You can try to move North, East, South or West
                It is also possible, nae recommended, to explore your current location. You may find blocked directions, or items lying around.
                Only the weak would think of quitting, but that is always an option.
                Most actions cost strength points, but resting gets them back
                                        
                Find the long lost hero or fail trying. How will it end...?\n\033[0m""");

        // Start game or exit

        System.out.print("Are you ready to start the adventure? ");
        String menuOption = input.nextLine().toUpperCase();
        if (!menuOption.startsWith("Y")) {
            menuOption = "X";
        } else {
            menuOption = "START";
        }
        System.out.println("To see a list of available commands type help. Maybe try that now.");

        // Get inputs until user types exit/x or quit/q or game is won

        while (!menuOption.equals("X") && !menuOption.equals("EXIT") && !menuOption.equals("DEAD")) {

            menuOption = getStrength();
            if (!menuOption.equals("DEAD")) {
                outputBasicDescription();
                canMove = true; //used to only print blocked if user tries a blocked route
                System.out.print("What do you want to do? ");
                menuOption = input.nextLine().toUpperCase();
            }

            // Player choice with multiple command forms - had to drop the case switch

            if (menuOption.equals("DEAD")) {
                playerDiedOfExhaustion();
            } else if (menuOption.equals("EXIT") || menuOption.equals("X") || menuOption.equals("QUIT") || menuOption.equals("Q")) {
                menuOption = endMessage();
            } else if (menuOption.equals("HELP") || menuOption.equals("H") || menuOption.equals("INFO")) {
                getHelp();
            } else if (menuOption.equals("CHEAT") || menuOption.equals("C") || menuOption.equals("SPOILER")) {
                showSpoiler();
            } else if (menuOption.equals("REST") || menuOption.equals("R") || menuOption.equals("SLEEP")) {
                getSomeRest();
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
            } else if (menuOption.equals("EAT") || menuOption.equals("F")) {
                eatSomething();
            } else if (menuOption.startsWith("EAT ") || menuOption.startsWith("F ")) {
                eatFood(menuOption);
            } else if (menuOption.equals("GO") || menuOption.equals("G")) {
                canMove = goSomewhere();
            } else if (menuOption.equals("GO NORTH") || menuOption.equals("NORTH") || menuOption.equals("N") || menuOption.equals("GO N")) {
                canMove = player.changeRoom("N");
                updateStrengthPoints(-5);
            } else if (menuOption.equals("GO EAST") || menuOption.equals("EAST") || menuOption.equals("E") || menuOption.equals("GO E")) {
                canMove = player.changeRoom("E");
                updateStrengthPoints(-5);
            } else if (menuOption.equals("GO SOUTH") || menuOption.equals("SOUTH") || menuOption.equals("S") || menuOption.equals("GO S")) {
                canMove = player.changeRoom("S");
                updateStrengthPoints(-5);
            } else if (menuOption.equals("GO WEST") || menuOption.equals("WEST") || menuOption.equals("W") || menuOption.equals("GO W")) {
                canMove = player.changeRoom("W");
                updateStrengthPoints(-5);
            } else {
                unknownCommand(menuOption);
            }

            if (!canMove) {
                System.out.println("\033[0;31mThat way is blocked.\033[0m");
            }
        }
    }

    // Overload if user only types "go" or "g"

    // Help info - only with the short commands

    public void getHelp() {
        System.out.println("""
                \033[4;33m
                You can use these commands, with some variations:\033[0;33m
                H - Help (this - see what I did there?)
                L - Look around (room description - always worth a try)
                I - List inventory
                T - Take item
                D - Drop item
                F - Eat food
                N - Go North
                E - Go East
                S - Go South
                W - Go West
                R - Rest
                X - Exit
                C - Cheat (how to win)\033[0m""");
        updateStrengthPoints(-1);
    }

    public void showSpoiler() {
        System.out.println("""
                \033[4;33m
                ******************** Spoiler Alert ********************\033[0;33m
                The catacomb is a special room.
                If you try to look around there without completing the task, you will lose.
                The task is just to find and move a gold bar and some holy water to the room.
                To win you must first drop these items and then look around.\033[0m                                
                     ___________________     __________________     __________________
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
                """);
        updateStrengthPoints(-10);
    }

    // Give up - option to back out of quitting

    public String endMessage() {
        System.out.print("\033[0;33m\nAre you sure you want to quit? (y/n)\033[0m ");
        String menuOption = input.nextLine().toUpperCase();
        if (menuOption.startsWith("Y")) {
            System.out.println("\033[0;33mReally? Hope to see you again soon. Bye.\033[0m");
            return "EXIT";
        } else {
            updateStrengthPoints(-1);
            return "CONTINUE";
        }
    }

    public String getStrength() {
        int currentStrength = player.getStrengthPoints();
        if (currentStrength < 1) {
            return "DEAD";
        } else if (currentStrength < 20) {
            System.out.println("\033[0;33mYou are exhausted, you really should rest a while!\033[0m");
        } else if (currentStrength < 50) {
            System.out.println("\033[0;32mYou are getting tired, maybe you should rest a while.\033[0m");
        }
        return "ALIVE";
    }

    public void playerDiedOfExhaustion() {
        System.out.println("\033[0;31mYou died of exhaustion. You really should have rested a while!!\033[0m");
    }

    // Invalid input

    public void unknownCommand(String menuOption) {
        System.out.println("\033[0;33mI do not understand \"" + menuOption + "\".\033[0m");
    }

    public boolean goSomewhere() {
        boolean canMove = false;
        System.out.print("Hmmm. Which direction do you want to go? ");
        String direction = input.nextLine().toUpperCase();
        if (direction.startsWith("N")) {
            canMove = player.changeRoom("N");
        } else if (direction.startsWith("E")) {
            canMove = player.changeRoom("E");
        } else if (direction.startsWith("S")) {
            canMove = player.changeRoom("S");
        } else if (direction.startsWith("W")) {
            canMove = player.changeRoom("W");
        }
        updateStrengthPoints(-5);
        return canMove;
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
            updateStrengthPoints(-1);
            outputDescription();
            return "CONTINUE";
        } else {
            if (!checkForHolyWater() || !checkForGoldBar()) {
                System.out.println("\n\033[0;31mYou woke Holger Danske without fullfilling his needs . He is not happy.\033[0m");
            } else {
                System.out.println("\n\033[0;33mYou woke Holger Danske and honoured his needs . Congratulations.\033[0m");
            }
            return "EXIT";
        }
    }

    // Brief info without looking around

    public void outputBasicDescription() {
        System.out.print("\nStrength " + player.getStrengthPoints() + "% ");
        System.out.print(":You are in " + player.currentRoom.getRoomName() + ": ");
        System.out.println(player.currentRoom.getRoomDescription());
    }

    // Player inventory - with formatted output

    public void outputInventory() {
        System.out.print("\033[0;34mStrength is " + player.getStrengthPoints() + "% ");
        ArrayList<Item> objects = player.getPlayerItems();
        int size = objects.size();
        System.out.print(":You are carrying ");
        if (size == 0) {
            System.out.println("nothing of use.");
        } else if (size == 1) {
            System.out.println("1 item of weight " + getTotalWeight() + ":\n" + objects.get(0).getItemName() + ".");
        } else {
            System.out.print(size + " items of weight " + getTotalWeight() + ":\n" + objects.get(0).getItemName());
            for (int i = 1; i < size - 1; i++) {
                System.out.print(", " + makeFirstLetterLowerCase(objects.get(i).getItemName()));
            }
            System.out.println(" and " + makeFirstLetterLowerCase(objects.get(size - 1).getItemName()) + ".");
        }
        System.out.print("\033[0m");
        getTotalWeight();
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
            System.out.println("1 item:\n" + objects.get(0).getItemName() + ".");
        } else {
            System.out.print(size + " items:\n" + objects.get(0).getItemName());
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
        Item foundItem = getMatchingItemNames(itemToDrop, "drop");
        if (foundItem != null) {
            player.dropAnItem(foundItem); // first and only match removed from player
            player.currentRoom.addItemToRoom(foundItem); // and added to current room
            updateStrengthPoints(-2);
        }
    }

    // Takes item from room and adds to player if input string is matched to exactly 1 item

    public void dropItem(String menuItem) {
        if (menuItem.startsWith("DROP ")) {
            menuItem = menuItem.substring(5); // command was "drop string"
        } else {
            menuItem = menuItem.substring(2); // command was "d string"
        }
        Item foundItem = getMatchingItemNames(menuItem, "drop");
        if (foundItem != null) {
            player.dropAnItem(foundItem); // first and only match removed from player
            player.currentRoom.addItemToRoom(foundItem); // and added to current room
            updateStrengthPoints(-2);
        }
    }

    // Overload take item when user types only "take"

    public void takeSomething() {
        System.out.print("Hmmm. Which item do you want to pick up? ");
        String itemToDrop = input.nextLine().toUpperCase();
        Item foundItem = getMatchingItemNames(itemToDrop, "take");
        takeItemIfCan(foundItem);
    }

    // Takes item from room and adds to player if input string is matched to exactly 1 item

    public void takeItem(String menuItem) {
        if (menuItem.startsWith("TAKE ")) {
            menuItem = menuItem.substring(5); // command was "take string"
        } else {
            menuItem = menuItem.substring(2); // command was "t string"
        }
        Item foundItem = getMatchingItemNames(menuItem, "take");
        takeItemIfCan(foundItem);
    }

    public void takeItemIfCan(Item foundItem) {
        if (foundItem != null) {
            if (checkCanTakeFoundItem(foundItem)) {
                moveItemFromRoomToPlayer(foundItem);
                System.out.println("\033[0;34mTaken.\033[0m");
                updateStrengthPoints(-2);
            } else {
                System.out.println("\033[0;31mThat item is too heavy to take without dropping something else.\033[0m");
            }
            updateStrengthPoints(-1);
        }
    }

    public boolean checkCanTakeFoundItem(Item item) {
        return (item.getItemWeight() + getTotalWeight() <= player.getMaxWeight());
    }

    public void moveItemFromRoomToPlayer(Item foundItem) {
        player.takeAnItem(foundItem); // first and only match added to player
        player.currentRoom.takeItemFromRoom(foundItem); // and removed from current room
    }

    // common code for both player inventory and room inventory with boolean control
    // loops through the objects in the given list and check if search is found in an item name
    // there may be found no, one or multiple items

    public Item getMatchingItemNames(String searchFor, String action) {
        String text1, text2; // only minor difference between drop, take and eat
        ArrayList<Item> givenInventory;
        if (Objects.equals(action, "drop")) {
            givenInventory = player.getPlayerItems();
            text1 = "Hmmm. You do not seem to have ";
            text2 = "\033[0;34mDropping the ";
        } else if (Objects.equals(action, "take")) {
            givenInventory = player.currentRoom.getRoomItems();
            text1 = "Hmmm. I cannot seem to see ";
            text2 = "\033[0;34mTrying to take the ";
        } else {
            givenInventory = player.getPlayerItems();
            text1 = "Hmmm. You do not seem to have ";
            text2 = "\033[0;34mTrying to eat the ";
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

    // Overload eatFood when user types only "eat"

    public void eatSomething() {
        System.out.print("Hmmm. Which item do you want to eat? ");
        String itemToEat = input.nextLine().toUpperCase();
        Item foundItem = getMatchingItemNames(itemToEat, "eat");
        eatCommon(foundItem);
    }

    public void eatFood(String menuItem) {
        if (menuItem.startsWith("EAT ")) {
            menuItem = menuItem.substring(5); // command was "eat string"
        } else {
            menuItem = menuItem.substring(2); // command was "f string" (food)
        }
        Item foundItem = getMatchingItemNames(menuItem, "eat");
        eatCommon(foundItem);
    }

    public void eatCommon(Item foundItem) {
        if (foundItem != null) {
            if (foundItem instanceof Food) {
                int strength = ((Food) foundItem).foodValue; // Cast item to food
                if (strength < 0) {
                    System.out.println("\033[0;31mEaten. You lost " + -strength + " strength :(\033[0m");
                } else {
                    System.out.println("\033[0;34mEaten. You gained " + strength + " strength :)\033[0m");
                }
                updateStrengthPoints(strength);
                player.dropAnItem(foundItem); // first and only match removed from player;
            } else {
                System.out.println("\033[0;31mYou cannot eat that.\033[0m");
            }
        }
    }

    // return total weight of inventory. Not used for anything yet.
    public int getTotalWeight() {
        ArrayList<Item> objects = player.getPlayerItems();
        int weight = 0;
        for (int i = 0; i < objects.size(); i++) {
            weight += (objects.get(i).getItemWeight());
        }
        return weight;
    }

    public boolean checkForHolyWater() {
        ArrayList<Item> objects = map.catacombs.getRoomItems();
        return (objects.contains(map.holyWater));
    }

    public boolean checkForGoldBar() {
        ArrayList<Item> objects = map.catacombs.getRoomItems();
        return (objects.contains(map.goldBar));
    }

    public void getSomeRest() {
        updateStrengthPoints(20);
    }

    // Actions use strength points, resting gains strength points

    public void updateStrengthPoints(int update) {
        int strength = player.getStrengthPoints();
        strength += update;
        if (strength > 100) {
            strength = 100;
            System.out.println("\033[;34mYou are fully rested!\033[0m");
        } else if (strength < 1) {
            strength = 0;
        }
        player.setStrengthPoints(strength);
    }
}