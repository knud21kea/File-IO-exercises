package kea.adventure;

import java.util.Scanner;

public class Adventure {

    public static void main(String[] args) {

        //Initialise rooms

        //create all instances of room objects - probably should be an array - data very basic
        Room room1 = new Room("Room1", "Looks like an entrance.");
        Room room2 = new Room("Room2", "Not much to see.");
        Room room3 = new Room("Room3", "The way straight seems blocked");
        Room room4 = new Room("Room4", "Looks empty");
        Room room5 = new Room("Room5", "This room looks special.");
        Room room6 = new Room("Room6", "You see nothing interesting");
        Room room7 = new Room("Room7", "What is here?...Oh, nothing.");
        Room room8 = new Room("Room8", "Seems to be several exits");
        Room room9 = new Room("Room9", "No beer here");

        Room currentRoom = room1;
        Room requestedRoom = null;

        //Make connections - not auto 2 way and not with map - todo?
        room1.setEast(room2);
        room2.setWest(room1);
        room2.setEast(room3);
        room3.setWest(room2);
        room3.setSouth(room6);
        room6.setNorth(room3);
        room6.setSouth(room9);
        room9.setNorth(room6);
        room9.setWest(room8);
        room8.setEast(room9);
        room8.setNorth(room5);
        room5.setSouth(room8);
        room8.setWest(room7);
        room7.setEast(room8);
        room7.setNorth(room4);
        room4.setSouth(room7);
        room4.setNorth(room1);
        room1.setSouth(room4);

        Scanner input = new Scanner(System.in);

        //Intro and description of start room
        System.out.println("\nWelcome to this text based Adventure.");

        //Get inputs until user types exit or x
        String menuOption = "Z";
        ;
        while (!menuOption.equals("X") && !menuOption.equals("EXIT")) {
            requestedRoom = currentRoom; //used to only print blocked if user tries a blocked route
            System.out.print(currentRoom.getRoomName() + ": ");
            System.out.println(currentRoom.getRoomDescription());
            System.out.print("What do you want to do? ");
            menuOption = input.nextLine().toUpperCase();

            //construction is very messy but works. Improvements needed here. Eliminate static variable?
            switch (menuOption) {
                case "GO NORTH", "NORTH", "N" -> requestedRoom = currentRoom.getNorthRoom();
                case "GO EAST", "EAST", "E" -> requestedRoom = currentRoom.getEastRoom();
                case "GO SOUTH", "SOUTH", "S" -> requestedRoom = currentRoom.getSouthRoom();
                case "GO WEST", "WEST", "W" -> requestedRoom = currentRoom.getWestRoom();
                case "LOOK", "L" -> System.out.print("OK. ");
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

    //Help info, only with the short commands
    public static void getHelp() {
        System.out.println("\nYou can use these commands:");
        System.out.println("H - Help (this)");
        System.out.println("L - Look around (room description)");
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
        System.out.println("I do not understand \"" + menuOption + "\".");
    }
}
