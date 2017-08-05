import java.io.*;
import java.net.*;

public class TCPClient {
 public static void main(String argv[]) throws Exception {
  String receivedMessage;
  if(argv.length != 2) {
    System.out.println("Please provide host and port of server client");
  }
  Socket clientSocket = new Socket(argv[0], Integer.parseInt(argv[1]));
  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  outToServer.writeBytes("Hello server\n");
  receivedMessage = inFromServer.readLine();
  System.out.println("FROM SERVER: " + receivedMessage);
  clientSocket.close();
 }
}
