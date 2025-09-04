package com.example.utils;

import java.util.Scanner;

public class TicketManagementEngine {

    //for your ease a scanner is created here to take inputs. You can change this if you want to.
    private static final Scanner keyboard= new Scanner(System.in);

    public static void main(String[] args){
        String[] myArgs = new String[4]; // 定义长度为4的字符串数组
        // 给myArgs数组赋值
        myArgs[0] = "5";
        myArgs[1] = "4";
        myArgs[2] = "5";
        myArgs[3] = "4";
        if (myArgs.length != 4) {
            System.out.println("Invalid Inputs to set layout. Exiting the program now.");
            return;

        }
        int numRows = Integer.parseInt(myArgs[0]);
        int leftSeats = Integer.parseInt(myArgs[1]);
        int middleSeats = Integer.parseInt(myArgs[2]);
        int rightSeats = Integer.parseInt(myArgs[3]);

        Venue venue = new Venue(numRows, leftSeats, middleSeats, rightSeats);

        TicketManagementEngine tme = new TicketManagementEngine();
        tme.displayMessage();
        tme.mainMenu(venue);
    }

    public void mainMenu(Venue venue) {
        boolean exit = false;
        do {
            System.out.println("Select an option to get started!");
            System.out.println("Press 1 to look at the show timings");
            System.out.println("Press 2 to look at the ticket costs");
            System.out.println("Press 3 to view the layout of the concert");
            System.out.println("Press 4 to book a seat");
            System.out.println("Press 5 to exit");
            System.out.print("> ");

            int yourChoice = keyboard.nextInt();

            switch (yourChoice) {
                case 1:
                    showTimings();
                    break;
                case 2:
                    ticketCosts();
                    break;
                case 3:
                    venue.showSeats3();
                    break;
                case 4:
                    bookSeat(venue);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Goodbye from the Ticket Management System! See you next time!");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (!exit);
    }

    public void showTimings() {
        System.out.println("Show Timings");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-15s%-15s%-15s%-30s%10s\n", "Date", "Artist Name", "Timing", "Venue Name", "Total Seats");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-15s%-15s%-15s%-30s%10s\n", "2024-05-01", "Taylor Swift", "1700 - 2100", "Melbourne Cricket Ground", "100");
        System.out.printf("%-15s%-15s%-15s%-30s%10s\n", "2024-05-02", "Taylor Swift", "1700 - 2100", "Melbourne Cricket Ground", "100");
        System.out.printf("%-15s%-15s%-15s%-30s%10s\n", "2024-05-03", "Taylor Swift", "1700 - 2100", "Melbourne Cricket Ground", "100");
        System.out.println("----------------------------------------------------------------------------------------");
    }

    public void ticketCosts(){
        System.out.println("Seat Costs");
        System.out.println("--------------------");
        System.out.printf("%-13s%-4s%-3s\n", "Left Zone: ", "AUD", "200");
        System.out.printf("%-13s%-4s%-3s\n", "Right Zone: ", "AUD", "200");
        System.out.printf("%-13s%-4s%-3s\n", "Middle Zone: ", "AUD", "500");
        System.out.println("--------------------");
    }

    public void bookSeat(Venue venue) {
        // Get the current booked seat position
        int[] currentPosition = venue.currentBookedSeat();
        int i = currentPosition[0];
        int j = currentPosition[1];

        // Initialize the first seat as selected
        venue.seats[1][1] = 'X';

        // Show the initial seat layout
        venue.showSeats();
        System.out.println("Continue to the seat selection!");
        System.out.println("You can select the seats that are empty and marked by _");

        // Prompt the user to select a seat
        System.out.println("Press W to move up.");
        System.out.println("Press A to move left.");
        System.out.println("Press S to move down.");
        System.out.println("Press D to move right.");
        System.out.println("Press Q to exit.");
        System.out.print("> ");

        // Continuously accept user input to move the selected seat
        while (true) {
            char input = keyboard.next().toUpperCase().charAt(0);
            venue.aaa(input);
            venue.printSeats(1,1);
            System.out.println("Press W to move up.");
            System.out.println("Press A to move left.");
            System.out.println("Press S to move down.");
            System.out.println("Press D to move right.");
            System.out.println("Press Q to exit.");
            System.out.print("> ");
            if (input=='Q'){
                System.out.println("Your seat selection is saved.");
                return;
            }
        }
    }



    // you must not change this code.
    public void displayMessage(){
        System.out.println("Welcome to a revised version of Taylor Swift's Eras tour.");

        System.out.print("\n" +
                " _____                _____                  ________  ___ _____ \n" +
                "|  ___|              |_   _|                |_   _|  \\/  |/  ___|\n" +
                "| |__ _ __ __ _ ___    | | ___  _   _ _ __    | | | .  . |\\ `--. \n" +
                "|  __| '__/ _` / __|   | |/ _ \\| | | | '__|   | | | |\\/| | `--. \\\n" +
                "| |__| | | (_| \\__ \\   | | (_) | |_| | |      | | | |  | |/\\__/ /\n" +
                "\\____/_|  \\__,_|___/   \\_/\\___/ \\__,_|_|      \\_/ \\_|  |_/\\____/ \n" +
                "                                                                 \n" +
                "                                                                 \n");
    }
}
