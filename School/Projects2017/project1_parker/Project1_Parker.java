/* Kellen Parker
 * CS-1181L
 * Instructor M. Cheatham
 * Project 1 
 */
package project1_parker;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Project1_Parker {

    private static File save;
    private static ArrayList<item> collection = new ArrayList<item>();
    private static Scanner kb = new Scanner(System.in);

    /**
     * Driving method
     * @param args -- n/a
     */
    public static void main(String[] args){
        if (checkFile()) {
            System.out.print("Previous user data loading...");
            try {
                load();
            } catch (Exception e) {
                System.err.println("Error loading saved collection.");
                System.err.println(e.getMessage());
            }
            System.out.println("done!");
        } else {
            System.out.println("No previous user data detected.");
        }

        System.out.println("Welcome to User's Movie and Video Game Collection Tracker Command Line Menu-Driven Interface Mk.I!\n");
        while (true) {
            System.out.print(">>");
            switch (kb.next()) {
                case "new":
                    newItem();
                    break;
                case "loan":
                    loan();
                    break;
                case "return":
                    returned();
                    break;
                case "list":
                    listItems();
                    break;
                case "remove":
                    removeItem();
                    break;
                case "quit":
                    try {
                        quit();
                    } catch (FileNotFoundException e) {
                        System.err.println("Error saving collection.");
                        System.err.println(e.getMessage());
                    }
                    break;
                case "help":
                    help();
                    break;
                default:
                    System.err.println("Unrecognisable command. Type 'help' for list of commands.");
                    break;
            }
        }

    }

    /**
     * Sees if file 'collection' exists
     * @return -- true if exists, else false
     */
    static boolean checkFile() {
        save = new File("collection");
        return save.exists();
    }

    /**
     * Method to add new item to collection
     * prompt for title, verify title is not in collection, prompt for format, and add to collection
     */
    static void newItem() {
        System.out.print("Enter Title: ");
        String title = kb.next();
        for (item i : collection) {
            if (i.getTitle().equals(title)) {
                System.err.println("Title already exists.");
                return;
            }
        }
        System.out.print("Enter Format: ");
        String format = kb.next();
        collection.add(new item(false, title, format, "none", "none"));
        System.out.println("New item added!\n");
    }

    /**
     * Method to modify item in collection to be on loan
     * prompt for title, verify title is in collection and not on loan, then modify item
     * if title is not in collection, error message is given and method ends
     */
    static void loan() {
        System.out.print("Enter Title: ");
        String title = kb.next();
        for (item i : collection) {
            if (i.getTitle().equals(title) && !i.isOnLoan()) {
                System.out.print("Enter name of person loaned to: ");
                String loanee = kb.next();
                System.out.print("Enter date loaned: ");
                String loanDate = kb.next();
                i.loan(loanee, loanDate);
                System.out.println("Item loaned!\n");
                return;
            }
        }
        //if here, title doesnt exist or item is already on loan
        System.err.println("Title not in collection or is already loaned.");
        //loan();
    }

    /**
     * Method to modify loaned item in collection back to default
     * prompt for title, verify title is in collection and item is on loan, then modify item
     * if title is not in collection, error message is given and method ends
     */
    static void returned() {
        System.out.print("Enter Title: ");
        String title = kb.next();
        for (item i : collection) {
            if (i.getTitle().equals(title) && i.isOnLoan()) {
                i.returned();
                System.out.println("Item returned!\n");
                return;
            }
        }
        //if here, title doesnt exist
        System.err.println("Title not in collection of loaned items.");
        //returned();
    }

    /**
     * Method to alphabetically list all items in format:
     * 'title -- format'
     * 'title -- format (loanee -- loanDate)'
     */
    static void listItems() {
        String[] list = new String[collection.size()];
        int c = 0;
        for (item i : collection) {
            list[c] = i.toString();
            c++;
        }
        java.util.Arrays.sort(list);
        for (String s : list) {
            System.out.println(s);
        }
    }

    /**
     * Method to remove item from collection
     * prompt for title, verify item is in collection, then remove item
     * if title is not in collection, error message is given and method ends
     */
    static void removeItem() {
        System.out.print("Enter Title: ");
        String title = kb.next();
        for (item i : collection) {
            if (i.getTitle().equals(title)) {
                collection.remove(i);
                System.out.println("Item removed!\n");
                return;
            }
        }
        //if here, title doesnt exist
        System.err.println("Title not in collection.");
        //removeItem();
    }

    /**
     * Method to save and quit program
     * prompt for verification, if yes, then save collection to file and end program
     * @throws FileNotFoundException
     */
    static void quit() throws FileNotFoundException {
        System.out.println("Are you sure you want to quit? [y]");
        if (kb.next().equals("y")) {
            PrintWriter pw = new PrintWriter(save);
            for (item i : collection) {
                pw.println(i.isOnLoan() + " " + i.getTitle() + " " + i.getFormat() + " " + i.getLoanee() + " " + i.getLoanDate());
            }
            pw.close();
            System.exit(0);
        }
    }

    /**
     * Method to display commands in method main()'s command-line menu
     */
    static void help() {
        System.out.println("Commands for UMVGCTCLM-DI Mk.I:"
                + "\n help.....lists all accepted commands"
                + "\n list.....lists all items in collection alphabetically"
                + "\n loan.....loan item from collection out"
                + "\n new......add new item to collection"
                + "\n quit.....exit program"
                + "\n remove...remove item from collection"
                + "\n return...loaned item is returned\n");
    }

    /**
     * Method to load collection from file
     * @throws FileNotFoundException
     */
    static void load() throws FileNotFoundException {
        Scanner in = new Scanner(save);
        while (in.hasNextBoolean()) {
            collection.add(new item(in.nextBoolean(), in.next(), in.next(), in.next(), in.next()));
        }

    }

}


class item {

    private String title;
    private String format;
    private boolean onLoan;
    private String loanee;
    private String loanDate;

    /**
     * constructor for item objects
     * @param onLoan -- [true/false] whether the file is loaned out or not
     * @param title -- [any single-word string] the title of the item
     * @param format -- [any single-word string] the format in which you own the item
     * @param loanee -- [if onLoan is true, any single-word string, else 'none'] to whom you loaned the item
     * @param loanDate -- [if onLoan is true, any single-word string, else 'none'] the date in which you loaned the item
     */
    public item(boolean onLoan, String title, String format, String loanee, String loanDate) {
        this.title = title;
        this.format = format;
        this.onLoan = onLoan;
        this.loanee = loanee;
        this.loanDate = loanDate;
    }

    /**
     * Getter method for the title
     * @return -- title of the item
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter method for the format
     * @return -- format of the item
     */
    public String getFormat() {
        return format;
    }

    /**
     * Getter method for whether the item is on loan
     * @return -- true if loaned, else false
     */
    public boolean isOnLoan() {
        return onLoan;
    }

    /**
     * Getter method for the name of the person you loaned the item to
     * @return -- loanee of item, if OnLoan is false then 'none'
     */
    public String getLoanee() {
        return loanee;
    }

    /**
     * Getter method for the date the item was loaned
     * @return -- loanDate of item, if OnLoan is false then 'none'
     */
    public String getLoanDate() {
        return loanDate;
    }

    /**
     * Method to set the item as loaned
     * @param loanee -- name of person loaned to
     * @param loanDate -- date loaned
     */
    public void loan(String loanee, String loanDate) {
        onLoan = true;
        this.loanee = loanee;
        this.loanDate = loanDate;
    }

    /**
     * Method to set loaned item back as default
     */
    public void returned() {
        onLoan = false;
        loanee = "none";
        loanDate = "none";
    }

    /**
     * Method to cast the item as a viewable string
     * in format:
     * 'title -- format'
     * 'title -- format (loanee -- loanDate)'
     * @return s -- string or the item
     */
    @Override
    public String toString() {
        String s = title + " -- " + format;
        if (onLoan) {
            s += " (" + loanee + " -- " + loanDate + ")";
        }
        return s;
    }

}
