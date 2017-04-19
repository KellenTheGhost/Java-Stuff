package radixcomplement;

import java.util.Scanner;


public class RadixComplement {
    static final char[] ALPHABET = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    static char[] alph;
    static String alphStr;
    static int base;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        while(base < 2 || base > 36){
            System.out.print("What is the base?(2 <= base => 36)  ");
            base = input(1);
        }
        baseCopy();
        String numStr;
        char[] numArr;
        while(true){
            System.out.print("Input number to find radix complement of:  ");
            numStr = input("").toUpperCase();
            numArr = numFill(numStr);
            if(baseCheck(numArr)){
                break;
            }
            else
                System.out.println("Error: Inputted number is not of inputted base.");
        }
        char[] comArr = comp(numArr);
        
    }
    
    static void baseCopy(){
        alph = new char[base];
        System.arraycopy(ALPHABET,0,alph,0,base);
        alphStr = arrToString(alph);
        System.out.println(alphStr);
    }
    
    static char[] numFill(String numStr){
        char[] numArr = new char[numStr.length()];
        for(int i = 0; i < numArr.length; i++){
            numArr[i] = numStr.charAt(i);
        }
        return numArr;
    }
    
    static boolean baseCheck(char[] numArr){
        for(char i : numArr){
            if(alphStr.indexOf(i)==-1)
                return false;
        }
        return true;
    }
    
    static char[] comp(char[] numArr){
        char[] comArr = new char[numArr.length];
        for(int i = 0; i < comArr.length; i++){
            int temp = numArr[i];
            int rem = (base -1) - temp;
            while(rem < 0){
                rem = (base -1) - rem;
            }
            System.out.println(rem);
            comArr[i] = alphStr.charAt(rem);
        }
        
        return comArr;
    }
    
    static String arrToString(char[] arr){
        String s = "";
        for(char i : arr){
            s = s + "" + i;
        }
        return s;
    }
    
    
    
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
