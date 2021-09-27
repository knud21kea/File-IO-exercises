package kea.adventure;

import java.util.Scanner;

public class Adventure {

    //   private static final Room[] rooms = new Room[10];
    private static final Scanner input = new Scanner(System.in);

    private final Map map;
    private final Player player;

    public static void main(String[] args) {

        /* *****************************************************************************
           Initialise rooms and make connections (auto 2-way joins so no 1-way passages)
           There could be a choice of maps - we have default
         **************************************************************************** */

        Adventure game = new Adventure();
        game.playGame();

        /* *******************************************************************
           Introduce game, process player inputs until game is closed - or won
           There could be a choice of starting locations - we have default
        ******************************************************************** */


    }

    public Adventure() {
        map = new Map();
        map.buildMap();
        player = new Player(map);
    }

    public void playGame() {

        //Intro and description of start room

        System.out.println("\nWelcome to this text based Adventure.");
        System.out.println("Find the treasure or fail trying. How will it end...?");

        //Get inputs until user types exit or x or game is won
        Room currentRoom = map.getStarterRoom();
        String menuOption = "Z";
        //Player.lookAround();
        while (!menuOption.equals("X") && !menuOption.equals("EXIT")) {
            Room requestedRoom = player.currentRoom; //used to only print blocked if user tries a blocked route
            System.out.print("\n" + player.currentRoom.getRoomName() + ": ");
            System.out.println(player.currentRoom.getRoomDescription());
            System.out.print("What do you want to do? ");
            menuOption = input.nextLine().toUpperCase();

            //player choice with multiple command forms

            switch (menuOption) {
                case "GO NORTH", "NORTH", "N" -> requestedRoom = player.go("N");
                case "GO EAST", "EAST", "E" -> requestedRoom = player.go("E");
                case "GO SOUTH", "SOUTH", "S" -> requestedRoom = player.go("S");
                case "GO WEST", "WEST", "W" -> requestedRoom = player.go("W");
                case "EXPLORE", "LOOK", "L" -> menuOption = lookAround(player.currentRoom, map.getSpecialRoom());
                case "HELP", "H" -> getHelp();
                case "EXIT", "X" -> endMessage();
                default -> unknownCommand(menuOption);
            }
            if (requestedRoom == null) {
                System.out.println("That way is blocked.");
            }
        }
    }

    //Help info - only with the short commands

    public void getHelp() {
        System.out.println("\nYou can use these commands:");
        System.out.println("H - Help (this - see what I did there?)");
        System.out.println("L - Look around (room description - always worth a try)");
        System.out.println("X - Exit");
        System.out.println("N - Go North");
        System.out.println("E - Go East");
        System.out.println("S - Go South");
        System.out.println("W - Go West");
    }

    //Give up

    public void endMessage() {
        System.out.println("Really? Hope to see you again soon.");
    }

    //Invalid input

    public void unknownCommand(String menuOption) {
        System.out.println("I do not understan d \"" + menuOption + "\".");
    }

    //Easter egg - way to beat the game

    public String lookAround(Room currentRoom, Room special) {
        if (currentRoom == special) {
            System.out.println("\nCongratulations, you found the beer!");
            return "X";
        } else {
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
            return "L";
        }
    }
}
