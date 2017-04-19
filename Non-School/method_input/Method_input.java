package method_input;
import java.util.Scanner;
public class Method_input {
    public static void main(String[] args) {
        System.out.print("int: ");
        int i = input(1);
        System.out.println("You entered "+i);
        System.out.print("double: ");
        double d = input(1.2);
        System.out.println("You entered "+d);
        System.out.print("string: ");      
        String str = input("ee");
        System.out.println("You entered "+str);        
        System.out.print("boolean: ");
        boolean bool = input(true);
        System.out.println("You entered "+bool);
    }
     
//==============================================================================for just ints in program
    /**     
     * method to prevent incorrect Scanner input errors
     * @return the value entered by the user.
     */     /*
    public static int input(){
        Scanner input = new Scanner(System.in);
        int inp;
        while(true){
            if(input.hasNextInt()){
                inp = input.nextInt();
                break;
            }
            else{
                System.out.print("Input an integer:  ");
                input.next();
            }
        }
        return inp;
    }     
*/  
//==============================================================================end for just ints in program    
    
    /**     
     * method to prevent incorrect Scanner input errors
     * to use:  input(##)  //where ## is any example of the type to return
     * if multiple input methods included:
     *    Uses method overloading to detect and return different variable types
     * @param number - any example the the element type to return
     * @return the value entered by the user.
     */ 
    public static int input(int number){
        Scanner input = new Scanner(System.in);
        int inp;
        while(true){
            if(input.hasNextInt()){
                inp = input.nextInt();
                break;
            }
            else{
                System.out.println("Input an integer.  ");
                input.next();
            }
        }
        return inp;
    }
    
    public static double input(double number){
        Scanner input = new Scanner(System.in);
        double inp;
        while(true){
            if(input.hasNextDouble()){
                inp = input.nextDouble();
                break;
            }
            else{
                System.out.println("Input a double.  ");
                input.next();
            }
        }
        return inp;
    }
    
    public static boolean input(boolean number){
        Scanner input = new Scanner(System.in);
        boolean inp;
        while(true){
            if(input.hasNextBoolean()){
                inp = input.nextBoolean();
                break;
            }
            else{
                System.out.println("Input a boolean.  ");
                input.next();
            }
        }
        return inp;
    }
    
    public static String input(String number){
        Scanner input = new Scanner(System.in);
        String inp;
        while(true){
            if(input.hasNextLine()){
                inp = input.nextLine();
                break;
            }
            else{
                System.out.println("Input a string.  ");
                input.next();            
            }
        }
    return inp;
    }  
   
    
    
}
