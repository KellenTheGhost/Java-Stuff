package project.pkg1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Project1Interface {

    private static Scanner in = new Scanner(System.in);

    public static int getMenuOption() {
        int option = 0;
        String inputString = "";

        while (option < 1 || option > 6) {

            System.out.println("");
            System.out.println("1. List the items");
            System.out.println("2. Add a new item");
            System.out.println("3. Mark an item as on loan");
            System.out.println("4. Mark an item as returned");
            System.out.println("5. Remove an item");
            System.out.println("6. Quit");
            System.out.print("What would you like to do? ");

            try {
                inputString = in.nextLine().trim();
                option = Integer.parseInt(inputString);
            } catch (NumberFormatException e) {
                // intentionally empty
            }

            if (option < 1 || option > 6) {
                System.out.println("Error: " + option
                        + " is not a valid option");
            }
        }

        System.out.println("");
        return option;
    }

    
    private static String getInput(String prompt) {
        System.out.print(prompt + " ");
        return in.nextLine().trim();
    }

    
    private static Date getDate(String prompt) throws Exception {
        System.out.print(prompt + " ");
        String dateString = in.nextLine().trim();

        try {
            Date loanedOn = new SimpleDateFormat(
                    "MM-dd-yyyy").parse(dateString);
            return loanedOn;
        } catch (ParseException e) {
            throw new Exception(dateString + " is not a valid date.");
        }
    }

    
    public static void main(String[] args) {

        MediaCollection collection = new MediaCollection();

        try {
            collection.readCollection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Continuing with a fresh collection");
        }

        int choice = getMenuOption();

        while (choice != 6) {
            try {
                switch (choice) {
                    case 1:
                        collection.listItems();
                        break;
                    case 2:
                        collection.addItem(getInput("What is the item's title?"),
                                getInput("What is the item's format (DVD, X-box, etc.)?"));
                        break;
                    case 3:
                        MediaItem toLoan = collection.retrieveItem(
                                getInput("What is the item's title?"));
                        if (collection.isLoanable(toLoan)) {
                            collection.loanItem(toLoan,
                                    getInput("Who did you loan " + toLoan.getTitle() + " to?"),
                                    getDate("What date did you loan it the them on? (MM-DD-YYYY)"));
                        } else {
                            System.out.println(toLoan.getTitle() + " is already on loan.");
                        }
                        break;
                    case 4:
                        MediaItem toReturn = collection.retrieveItem(
                                getInput("What is the item's title?"));
                        collection.returnItem(toReturn);
                        break;
                    case 5:
                        MediaItem toRemove = collection.retrieveItem(
                                getInput("What is the item's title?"));
                        collection.removeItem(toRemove);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            choice = getMenuOption();
        }

        try {
            collection.storeCollection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
