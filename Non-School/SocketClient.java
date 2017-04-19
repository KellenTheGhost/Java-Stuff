/*
 */
package socketclient;
import java.util.Scanner;
import java.io.*;
import java.net.*;
/**
 *
 * @author erik
 */
public class SocketClient {
    private static String serverName;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String sName = "localhost";
        if(0 < args.length) {
           sName = args[0];
        }
        
        int port = 8080;
        try {
            System.out.println("Connecting to " + sName
                    + " on port " + port);
            try (Socket client = new Socket(sName, port)) {
                System.out.println("Just connected to " + client.getRemoteSocketAddress());

                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);                
                String prompt;
                do{
                    System.out.print("prompt:  ");
                    prompt = kb.nextLine();                
                    out.writeUTF(prompt);
                    System.out.println(in.readUTF());
                }while(!prompt.equals("quit"));
                
                System.out.println("Server says " + in.readUTF());
            }
        } catch (IOException e) {
            System.out.println("Failed to conect. Is Server running?");
        }
        
        System.out.println("Exiting");
    }
}