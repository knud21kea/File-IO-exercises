package kea.adventure;

import java.util.Scanner;

public class Adventure {

 //   private static final Room[] rooms = new Room[10];
    private static final Scanner input = new Scanner(System.in);

    private Map map;

    public static void main(String[] args) {

        /* *****************************************************************************
           Initialise rooms and make connections (auto 2-way joins so no 1-way passages)
           There could be a choice of maps - we have default
         **************************************************************************** */
        // hej med dig

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
            Room requestedRoom = currentRoom; //used to only print blocked if user tries a blocked route
            System.out.print("\n" + currentRoom.getRoomName() + ": ");
            System.out.println(currentRoom.getRoomDescription());
            System.out.print("What do you want to do? ");
            menuOption = input.nextLine().toUpperCase();

            //player choice with multiple command forms

            switch (menuOption) {
                case "GO NORTH", "NORTH", "N" -> requestedRoom = currentRoom.getNorthRoom(); //replace with player.goNorth
                case "GO EAST", "EAST", "E" -> requestedRoom = currentRoom.getEastRoom(); //replace with player.goEast
                case "GO SOUTH", "SOUTH", "S" -> requestedRoom = currentRoom.getSouthRoom(); //replace with player.goSouth
                case "GO WEST", "WEST", "W" -> requestedRoom = currentRoom.getWestRoom(); //replace with player.goWest
                case "EXPLORE", "LOOK", "L" -> menuOption = lookAround(currentRoom, map.getSpecialRoom());  //replace with player.lookAround
                case "HELP", "H" -> getHelp();
                case "EXIT", "X" -> endMessage();
                default -> unknownCommand(menuOption);
            }
            if (requestedRoom == null) {
                System.out.println("That way is blocked.");
            } else {
                currentRoom = requestedRoom;
            }
        }
    }

    //Help info - only with the short commands

    public static void getHelp() {
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

    public static void endMessage() {
        System.out.println("Really? Hope to see you again soon.");
    }

    //Invalid input

    public static void unknownCommand(String menuOption) {
        System.out.println("I do not understan d \"" + menuOption + "\".");
    }

    //public static void buildMap() {

        /*//create all instances of rooms as an object array - data very basic

        rooms[1] = new Room("Room 1", "Looks like an entrance.");
        rooms[2] = new Room("Room 2", "Not much to see.");
        rooms[3] = new Room("Room 3", "The way straight seems blocked");
        rooms[4] = new Room("Room 4", "Looks empty");
        rooms[5] = new Room("Room 5", "This room looks special.");
        rooms[6] = new Room("Room 6", "You see nothing interesting");
        rooms[7] = new Room("Room 7", "What is here?...Oh, nothing.");
        rooms[8] = new Room("Room 8", "Seems to be several exits");
        rooms[9] = new Room("Room 9", "No beer here");
        rooms[0] = rooms[1]; //current room

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
    }*/

    //Easter egg - way to beat the game

    public static String lookAround(Room currentRoom, Room special) {
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
