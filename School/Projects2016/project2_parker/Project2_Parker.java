/* Kellen Parker
 * CS-1180L-07
 * Instructor A. Goshtasby
 * Project 2: Magic Square
 */
package project2_parker;

import java.util.Scanner;

public class Project2_Parker {

    /**
     * the gridsize referenced within multiple methods
     */
    static int n;

    /**
     * the array/grid/square of numbers referenced within multiple methods
     */
    static int[][] grid;

    /**
     * main method that calls gridsize, then loops magic square game, then
     * congratulates user
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //initialization
        grid = gridSize();

        //play magic square!!
        while (true) {

            //display
            display();

            //value entry and input validation   
            System.out.println("Where do you want to put a new value?");
            System.out.print("Row:  ");
            int row = input();
            if (!gridCheck(row)) {
                continue;
            }

            System.out.print("Column:  ");
            int col = input();
            if (!gridCheck(col)) {
                continue;
            }

            System.out.print("What value hould go there?  ");
            int val = input();
            if (!valueCheck(val)) {
                continue;
            }

            grid[row][col] = val;

            //solution detection
            if (solution()) {
                display();
                break;
            }

        }//end magic square loop

        //congratulate user
        System.out.println("Victory!");
    }

    /**
     * method to get the size of the magic square and make sure it is within the
     * domain[3,8]
     *
     * @return n - the size of the magic square
     */
    static int[][] gridSize() {
        while (true) {
            System.out.print("Let's make a Magic Square! How big should it be?  ");
            n = input();
            if (n < 3) {
                System.out.println("That would violate the laws of mathematics!");
            } else if (n > 8) {
                System.out.println("That's huge! Please enter a number less than 9.");
            } else {
                return new int[n][n];
            }
        }
    }

    /**
     * displays 2d array grid
     *
     * @param grid - the array to be displayed
     */
    static void display() {
        System.out.println("\nThe square currently looks like this:");
        for (int i[] : grid) {
            for (int j : i) {
                System.out.printf("%-4d", j);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * checks if the number can be an index if array grid
     *
     * @param pos - row or column position to check if it is smaller than
     * gridsize
     * @return - true if smaller than gridsize, else false
     */
    static boolean gridCheck(int pos) {
        if (pos > n - 1) {
            System.out.println("You can only use numbers between 0 and " + (n - 1) + " for this square.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * checks if value can be placed in array by rules of magic square game
     * value cannot be larger than number of rows squared, or smaller than one
     * value cannot be repeated
     *
     * @param val - the value to check if fits parameters listed above
     * @return - true if fits parameters, else false
     */
    static boolean valueCheck(int val) {

        if ((val > n * n) || (val < 1)) {
            System.out.println("You can only use numbers between 1 and " + (n * n) + " for this square.");
            return false;
        }

        //if entire grid is filled but still need values
        boolean filled = true;
        for (int i[] : grid) {
            //if there is a zero in the grid (a space of the grid is not filled)
            if (java.util.Arrays.binarySearch(i, 0) > 0) {
                filled = false;
                continue;
            }
        }
        if (filled) {
            gridFilled(val);
            return true;
        }

        //checks if value is in grid
        for (int k[] : grid) {
            if (java.util.Arrays.binarySearch(k, val) > 0) {
                System.out.println("This value is already in use.");
                return false;
            }
        }
        return true;
    }

    /**
     * takes value from valueCheck and when grid is full, replaces the array
     * position where value currently is with zero, allowing the program to
     * continue.
     *
     * @param val - the value to find the array position of
     */
    static void gridFilled(int val) {
        int row = -1, col = -1;
        for (int i = 0; i < n; i++) {
            if (java.util.Arrays.binarySearch(grid[i], val) > 0) {
                row = i;
                col = java.util.Arrays.binarySearch(grid[row], val);
            }
        }
        if (row >= 0 && col >= 0) {
            System.out.println("This value is currently in: (" + row + "," + col + ").   Replacing with 0");
            grid[row][col] = 0;
        }
    }

    /**
     * detects if magic square is complete sum in all directions equals same
     * value
     *
     * @return - true if all sums equal, else false
     */
    static boolean solution() {
        //base sum
        int sum1 = 0;
        for (int o = 0; o < n; o++) {
            sum1 += grid[0][o];
        }

        //horizontal
        for (int l[] : grid) {
            int sum2 = 0;
            for (int m : l) {
                sum2 += m;
            }
            if (sum1 != sum2) {
                return false;
            }
        }

        //vertical
        for (int p = 0; p < n; p++) {
            int sum2 = 0;
            for (int q = 0; q < n; q++) {
                sum2 += grid[q][p];
            }
            if (sum1 != sum2) {
                return false;
            }
        }

        //diagonal
        int sum2 = 0;
        for (int r = 0; r < n; r++) {
            sum2 += grid[r][r];
        }
        if (sum1 != sum2) {
            return false;
        }

        //other diagonal
        sum2 = 0;
        for (int t = 0; t < n; t++) {
            sum2 += grid[t][(n - t) - 1];
        }
        if (sum1 != sum2) {
            return false;
        }

        return true;

    }

    /**
     * method to prevent incorrect Scanner input errors
     *
     * @return - the value entered by the user.
     */
    public static int input() {
        Scanner input = new Scanner(System.in);
        int inp;
        while (true) {
            if (input.hasNextInt()) {
                inp = input.nextInt();
                break;
            } else {
                System.out.print("Input an integer:  ");
                input.next();
            }
        }
        return inp;
    }

}
