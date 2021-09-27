
/* *************************************************************************************************

                                   --  Adventure (part 1) --

DAT21 Java project (compulsory group exercise).
 Developers: Graham Heaven and Lasse Bøgeskov-Jensen, September 2021.

A simple text-based adventure game inspired by "Colossal Cave Adventure",
 by William Crowther and Don Woods, from 1976-1977.

A single player navigates through a small maze of rooms using a simple text parser interface.
In each room it is possible to get limited information about the room, and move to connected rooms.
There is an achievable goal using the available commands.

************************************************************************************************** */

package kea.adventure;

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
        player = new Player(map);
    }

    public void playGame() {

        /*
        Introduce game, process player inputs until game is closed - or won
        There could be a choice of starting locations - we have default
        */

        System.out.println("\nWelcome to this text based Adventure.");
        System.out.println("Find the treasure or fail trying. How will it end...?");

        // Get inputs until user types exit/x or quit/q or game is won

        String menuOption = "START";
        while (!menuOption.equals("X") && !menuOption.equals("EXIT")) {
            Room requestedRoom = player.currentRoom; //used to only print blocked if user tries a blocked route
            System.out.print("\n" + player.currentRoom.getRoomName() + ": ");
            System.out.println(player.currentRoom.getRoomDescription());
            System.out.print("What do you want to do? ");
            menuOption = input.nextLine().toUpperCase();

            // Player choice with multiple command forms

            switch (menuOption) {
                case "GO NORTH", "NORTH", "N" -> requestedRoom = player.changeRoom("N");
                case "GO EAST", "EAST", "E" -> requestedRoom = player.changeRoom("E");
                case "GO SOUTH", "SOUTH", "S" -> requestedRoom = player.changeRoom("S");
                case "GO WEST", "WEST", "W" -> requestedRoom = player.changeRoom("W");
                case "EXPLORE", "LOOK", "L" -> menuOption = lookAround(player.currentRoom, map.getSpecialRoom());
                case "HELP", "H", "INFO", "I" -> getHelp();
                case "EXIT", "X", "QUIT", "Q" -> menuOption = endMessage();
                default -> unknownCommand(menuOption);
            }
            if (requestedRoom == null) {
                System.out.println("That way is blocked.");
            }
        }
    }

    // Help info - only with the short commands

    public void getHelp() {
        System.out.println("\nYou can use these commands, with some variations:");
        System.out.println("H - Help (this - see what I did there?)");
        System.out.println("L - Look around (room description - always worth a try)");
        System.out.println("X - Exit");
        System.out.println("N - Go North");
        System.out.println("E - Go East");
        System.out.println("S - Go South");
        System.out.println("W - Go West");
    }

    // Give up

    public String endMessage() {
        System.out.print("\nAre you sure you want to quit? (y/n) ");
        String menuOption = input.nextLine().toUpperCase();
        if (menuOption.equals("YES") || menuOption.equals("Y")) {
            System.out.println("Really? Hope to see you again soon. Bye.");
            return "EXIT";
        } else {
            return "CONTINUE";
        }
    }

    // Invalid input

    public void unknownCommand(String menuOption) {
        System.out.println("I do not understand \"" + menuOption + "\".");
    }

    // Room description with an Easter egg - a way to beat the game

    public String lookAround(Room currentRoom, Room special) {
        if (currentRoom != special) {
            System.out.println("\nLooking around. ");
            if (currentRoom.getKnownNorth() && currentRoom.getNorthRoom() == null) {
                System.out.println("The way North is blocked.");
            }
            if (currentRoom.getKnownEast() && currentRoom.getEastRoom() == null) {
                System.out.println("The way East is blocked.");
            }
            if (currentRoom.getKnownSouth() && currentRoom.getSouthRoom() == null) {
                System.out.println("The way South is blocked.");
            }
            if (currentRoom.getKnownWest() && currentRoom.getWestRoom() == null) {
                System.out.println("The way West is blocked.");
            }
            return "CONTINUE";
        } else {
            System.out.println("\nCongratulations, you have found the sleeping Holge Danske in his Kronborg home.");
            return "EXIT";
        }
    }
}
