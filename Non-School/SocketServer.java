/*
 */
package socketserver;
import java.net.*;
import java.io.*;
public class SocketServer extends Thread
{
   private static int port;
   private final ServerSocket serverSocket;
   public SocketServer(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(20000);
   }
   @Override
   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("Waiting for client on port " +
            serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            String utf;
            do{
                utf = in.readUTF();
                System.out.println("inner loop...");
                switch(utf){
                    case "ls":
                        System.out.println("getting dir...");
                        File f = new File(".");
                        String s = "===========\n";
                        for(File file : f.listFiles()){
                            s += file.getName()+"\n";
                        }
                        s+= "===========\n";
                        out.writeUTF(s);
                        break;
                    case "get":
                        
                        break;
                    case "put":
                        
                        break;
                    default:
                        break;
                }
            }while(!utf.equals("quit"));
            out.writeUTF("Thank you for connecting to "
              + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();

         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            break;
         }
      }
   }
   
   
   public static void main(String [] args)
   {
      port=8080;
      try
      {
         Thread t = new SocketServer(port);
         t.start();
      }catch(IOException e)
      {
      }
   }
}