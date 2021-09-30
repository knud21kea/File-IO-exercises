
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

            // Player choice with multiple command forms

            switch (menuOption) {
                case "EXIT", "X", "QUIT", "Q" -> menuOption = endMessage();
                case "HELP", "H", "INFO" -> getHelp();
                case "INVENTORY", "INV", "I" -> outputInventory();
                case "EXPLORE", "LOOK", "L" -> menuOption = lookAround(player.currentRoom, map.getSpecialRoom());
                case "GO NORTH", "NORTH", "N" -> canMove = player.changeRoom("N");
                case "GO EAST", "EAST", "E" -> canMove = player.changeRoom("E");
                case "GO SOUTH", "SOUTH", "S" -> canMove = player.changeRoom("S");
                case "GO WEST", "WEST", "W" -> canMove = player.changeRoom("W");
                default -> unknownCommand(menuOption);
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

    // Give up

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

    // Player inventory

    public void outputInventory() {
        System.out.print("\033[0;34m");
        ArrayList<Item> objects = player.getPlayerItems();
        int size = objects.size();
        System.out.print("You are carrying ");
        if (size == 0) {
            System.out.print("nothing of use.");
        } else if (size == 1) {
            System.out.print("1 item: " + objects.get(0).getItemName() + ".");
        } else {
            System.out.print(size + " items: " + objects.get(0).getItemName());
            for (int i = 1; i < size - 1; i++) {
                System.out.print(", " + makeFirstLetterLowerCase(objects.get(i).getItemName()));
            }
            System.out.println(" and " + makeFirstLetterLowerCase(objects.get(size - 1).getItemName()) + ".");
        }
        System.out.print("\033[0m");
    }

    // Room inventory

    public void outputDescription() {
        System.out.print("\033[0;34m");
        ArrayList<Item> objects = player.currentRoom.getRoomItems();
        int size = objects.size();
        System.out.print("You can see ");
        if (size == 0) {
            System.out.print("nothing of interest.");
        } else if (size == 1) {
            System.out.print("1 item: " + objects.get(0).getItemName() + ".");
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
}
