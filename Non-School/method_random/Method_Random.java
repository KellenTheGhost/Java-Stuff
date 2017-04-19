/* 
 * Kellen Parker
 * CS-1180L-07
 * Instructor A. Goshtasby
 * Lab 
 */
package method_random;

import java.util.Scanner;

/**
 *
 * @author Kellen Parker
 */
public class Method_Random {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int low, high;
        do{
            System.out.print("low:  ");
            low = input(1);
            System.out.print("high:  ");
            high = input(1);

            if(low > high)
                System.out.println("low is greater than high. input new numbers.");
            else
                break;
        }
        while(true);
        
        System.out.println("");
        int min = 2147483647, max = -2147483648,count=0;
        for(int i = 0; i < 10000; i++){
            int rand = random(low,high);
            System.out.printf("%5d",rand);
            count++;
            if(count==10){
                System.out.print("\n|");
                count = 0;
            }
            if(rand<min)
                min = rand;
            if(rand>max)
                max = rand;
        }
        System.out.println(" ====== min: "+min+"   max: "+max+" ======");
    }
    
    public static int random(int low, int high){
        int rand = (int)(Math.random()*(high+1-low))+low;
        
        return rand;
    }
    
    /**     
     * method to prevent incorrect Scanner input errors
     * to use:  input(##)  //where ## is any example of the type to return
     * if multiple input methods included:
     *    Uses method overloading to detect and return different variable types
     * @param number - any example the the type to return
     * @return the value entered by the user.
     */ 
    public static int input(int number){
        Scanner input = new Scanner(System.in);
        int inp = 2147483647;
        boolean active = true;
        while(active){
            if(input.hasNextInt()){
                inp = input.nextInt();
                active = false;
            }
            else{
                System.out.println("Input an integer.  ");
                input.next();
            }
        }
        return inp;
    }
    
    
}
