/* Kellen Parker
 * CS-1180L-07
 * Instructor A. Goshtasby
 * Project 4
 */
package project4_parker;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * class to control driver program
 * @author kjpar
 */
public class Project4_Parker {

    private static final File INPUT = new File("name_list.txt");
    private static int balls;
    private static int strikes;

    /**
     * loops driver program if 'y' is pressed
     * @param args - n/a
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        char inp = 'y';
        while (inp == 'y') {
            driver();
            System.out.print("\nEnter 'y' for next pitch:  ");
            inp = keyboard.next().charAt(0);
            System.out.println("");
        }
    }

    /**
     * takes random name from file
     * only returns one name
     * @return - random name
     */
    public static String createName() {
        String name = null;
        try (Scanner reader = new Scanner(INPUT)) {
            int rand = (int)(Math.random()*1245);
            for (int i = 0; i < rand && reader.hasNextLine(); i++) {
                reader.nextLine();
            }   name = reader.nextLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Project4_Parker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }

    /**
     * creates batter object and/or overwrites object pointer on next driver program iteration
     * @return - batter object with random name
     */
    public static Batter createBatter() {
        String name = createName() + " " + createName();
        double average = Math.random();
        Batter batter = new Batter(name, average);
        return batter;
    }

    /**
     * creates pitcher object and/or overwrites object pointer on next driver program iteration
     * @return - pitcher object with random name
     */
    public static Pitcher createPitcher() {
        String name = createName() + " " + createName();
        double average = Math.random();
        Pitcher pitcher = new Pitcher(name, average);
        return pitcher;
    }

    /**
     * creates string with count of balls and strikes of this iteration with proper pluralization
     * @return - string created
     */
    public static String count() {
        String count;
        if (balls == 1 && strikes == 1) {
            count = "\tThe count is " + balls + " ball and " + strikes + " strike";
        } else if (balls == 1 && strikes != 1) {
            count = "\tThe count is " + balls + " ball and " + strikes + " strikes";
        } else if (balls != 1 && strikes == 1) {
            count = "\tThe count is " + balls + " balls and " + strikes + " strike";
        } else {
            count = "\tThe count is " + balls + " balls and " + strikes + " strikes";
        }
        return count;
    }

    /**
     * driver program
     * outputs to console play-by-play for this pitch
     * ends when pitch is over with a walk, a hit, or a strike.
     */
    public static void driver() {
        Pitcher pitcher = createPitcher();
        Batter batter = createBatter();
        balls = 0;
        strikes = 0;

        System.out.println(pitcher.getName() + " is pitching to " + batter.getName());
        while (true) {
            if (pitcher.pitch()) {
                if (batter.hit()) {
                    System.out.println(count());
                    System.out.println(batter.getName() + " got a hit!");
                    break;
                } else {
                    System.out.println("\t" + batter.getName() + " swung and missed");
                    strikes++;
                }
            } else {
                System.out.println("\t" + pitcher.getName() + " threw a ball");
                balls++;
            }
            System.out.println(count());
            if (balls >= 4) {
                System.out.println(batter.getName() + " walked.");
                break;
            }
            if (strikes >= 3) {
                System.out.println(batter.getName() + " struck out.");
                break;
            }
        }

    }

}