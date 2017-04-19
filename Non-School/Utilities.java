/* 
 * Kellen Parker
 * CS-1180L-07
 * Instructor A. Goshtasby
 * Lab 
 */
package utilities;

import java.awt.GraphicsEnvironment;

/**
 *
 * @author Kellen Parker
 */
public class Utilities {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        getFonts();
    }
    
    public static void getFonts(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        for (int index = 0; index < fontNames.length; index++){
             System.out.println(fontNames[index]);
        }
    }
    
    public static boolean isInteger(String str) {
        if (str == null)
            return false;
        int length = str.length();
        if (length == 0)
            return false;
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1)
                return false;
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }
    
    
    
}
