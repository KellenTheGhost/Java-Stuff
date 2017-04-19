/* Kellen Parker
 * CS-1180L-07
 * Instructor A. Goshtasby
 * Project 3:  Caesar Cipher
 */
package project3_parker;

import java.io.*;
import java.util.Scanner;

/**
 * Caesar Cipher class
 * Like a proper Caesar Cipher, the encryption is only letters and 
 * letters encrypted out of being a letter, such as 'z',will return to 'a' and loop around.
 * Plain:    ABCDEFGHIJKLMNOPQRSTUVWXYZ  +3
 * Cipher:   XYZABCDEFGHIJKLMNOPQRSTUVW
 * 
 * @author kjpar
 */
public class Project3_Parker {

    /**
     * main method
     * loops:
     *   call getMenuOption
     *   following actions based on return
     *     call selectFile
     *     call selectKey
     *     encrypt
     *     decrypt
     *     exit
     * 
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        while(true){
            // call getMenuOption
            int choice = getMenuOption();

            //exit program
            if (choice == 3) 
                System.exit(0);

            //get file and key
            File inputFile = selectFile();
            int key = selectKey();
            
            //call encrypt or decrypt
            if (choice == 1)
                encrypt(inputFile, key);
            else if (choice == 2) 
                decrypt(inputFile, key);
        }
    }

    /**
     * display the menu and scan keyboard for input selection
     * ensure the selection is a menu option
     * 
     * @return - the inputted selection
     */
    public static int getMenuOption() {
        Scanner keyboard = new Scanner(System.in);
        int c;
        do {
            System.out.println("1.  Encrypt");
            System.out.println("2.  Decrypt");
            System.out.println("3.  Quit");
            System.out.print("What would you like to do?  ");
            c = keyboard.nextInt();
            if(c < 1 || c > 3)
                System.out.println("Error:  value must be between 1 and 3");
        }while (c < 1 || c > 3);
        return c;
    }
    
    
    /**
     * encrypt each letter in txt file
     * check if file is .txt extention
     * construct outputFile, the file that is encrypted
     * look at each character, add the key to it, and print to outputFile
     * 
     * @param inputFile - file to scan
     * @param key - encryption key, the number to add to each character
     * @throws IOException 
     */
    public static void encrypt(File inputFile, int key) throws IOException{
        Scanner reader = new Scanner(inputFile);
        if(!(inputFile.getName().substring(inputFile.getName().length() -4).equals(".txt"))){
            System.out.println("Error:  Selected file is not a .txt file");
        }
        String outputS = inputFile.getAbsolutePath().substring(0,inputFile.getAbsolutePath().length()-4)+".enc";
        File outputFile = new File(outputS);
        PrintWriter writer = new PrintWriter(outputFile);        
        while(reader.hasNext()){
            String s = reader.nextLine();
            for(int i = 0; i < s.length(); i++){
                char c = s.charAt(i);
                int cE;
                if(key >= 0){
                    if(c <= 'z' && c >= 'a'){
                        if(c > 'z' - key && c <= 'z')
                            cE = (c - 26 + key);
                        else
                            cE = (c + key);
                    }
                    else if(c <='Z' && c >= 'A'){
                        if(c > 'Z' - key && c <= 'Z')
                            cE = (c - 26 + key);
                        else
                            cE = (c + key);
                    }
                    else    
                        cE = c;
                }else{
                    if(c <= 'z' && c >= 'a'){
                        if(c < 'a' - key && c >= 'a')
                            cE = (c + 26 + key);
                        else
                            cE = (c + key);
                    }
                    else if(c <='Z' && c >= 'A'){
                        if(c < 'A' - key && c >= 'A')
                            cE = (c + 26 + key);
                        else
                            cE = (c + key);
                    }
                    else    
                        cE = c;
                }
                
                char cEc = (char)cE;
                writer.print(cEc);
            }
            writer.println();
        }
        writer.close();
        reader.close();
        inputFile.delete();
        System.out.println("Encrypted.\n");
    }

    /**
     * decrypt each letter in enc file
     * check if file is .enc extention
     * construct outputFile, the file that is decrypted
     * look at each character, subtract the key to it, and print to outputFile
     * 
     * @param inputFile - file to scan
     * @param key - encryption key, the number to add to each character
     * @throws IOException 
     */
    public static void decrypt(File inputFile, int key) throws IOException{
        Scanner reader = new Scanner(inputFile);
        if(!(inputFile.getName().substring(inputFile.getName().length() -4).equals(".enc"))){
            System.out.println("Error:  Selected file is not a .enc file");
            return;
        }
        String outputS = inputFile.getAbsolutePath().substring(0,inputFile.getAbsolutePath().length()-4)+".txt";
        File outputFile = new File(outputS);
        PrintWriter writer = new PrintWriter(outputFile);        
        while(reader.hasNext()){
            String s = reader.nextLine();
            for(int i = 0; i < s.length(); i++){
                char c = s.charAt(i);
                int cE;
                if(key >= 0){                
                    if(c <= 'z' && c >= 'a'){
                        if(c < 'a' + key && c >= 'a')
                            cE = (c + 26 - key);
                        else
                            cE = (c - key);
                    }
                    else if(c <='Z' && c >= 'A'){
                        if(c < 'A' + key && c >= 'A')
                            cE = (c + 26 - key);
                        else
                            cE = (c - key);
                    }
                    else    
                        cE = c;
                }else{
                    if(c <= 'z' && c >= 'a'){
                        if(c > 'z' + key && c <= 'z')
                            cE = (c - 26 - key);
                        else
                            cE = (c - key);
                    }
                    else if(c <='Z' && c >= 'A'){
                        if(c > 'Z' + key && c <= 'Z')
                            cE = (c - 26 - key);
                        else
                            cE = (c - key);
                    }
                    else    
                        cE = c;
                }
                char cEc = (char)cE;
                writer.print(cEc);
            }
            writer.println();
        }
        writer.close();
        reader.close();
        inputFile.delete();
        System.out.println("Decrypted.\n");
    }

    /**
     * prompt user for filepath and scan keyboard for string
     * checks if file exists
     * construct file from inputted filepath
     * 
     * @return - constructed file
     */
    public static File selectFile() {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the file location [ex:  D:\\Folder\\folder\\File.txt]:  ");
        String dir = keyboard.nextLine();
        File inputFile = new File(dir);
        if(!inputFile.exists()){
            System.out.println("Error:  File does not exist.");
            inputFile = selectFile();
        }
        return inputFile;
    }

    /**
     * prompt user for key and scan keyboard for integer
     * 
     * @return - integer inputted as key
     */
    public static int selectKey() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the key:  ");
        int key = in.nextInt();
        return key;
    }
    
}
