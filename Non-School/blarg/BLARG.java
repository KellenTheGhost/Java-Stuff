package blarg;
import java.util.Scanner;
public class BLARG {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("enter string:  ");
        String s = input.nextLine();
        String sS = toCaps(s);
        System.out.println(sS);
    }

    static String toCaps(String s){
        String[] s1 = s.split(" ");
        s = "";
        for(int i = 0; i < s1.length;i++){
            String c = (s1[i].charAt(0) + "").toUpperCase();
            s1[i] = c + s1[i].substring(1);
            s = s + " " + s1[i];
            s = s.trim();
        }
        return s;
    }
    
    
    
}
