//Mewan Couplé      ECE PARIS

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

    //Initiate socket and port where we listen
    private static ServerSocket mysocket = null;

    private static Vector<ClientHandler> listofClient = new Vector<>();

    public static Vector<ClientHandler> getListofClient() {
        return listofClient;
    }

    public static void main(String args[]) {
        try {
            //We are listening to this port
            mysocket = new ServerSocket(9999);
            System.out.println("Server créé au port 9999");
            while (true) {
                //We accept all the new connections
                Socket client = mysocket.accept();
                System.out.println("New client connected");
                ClientHandler c = new ClientHandler(client);
                listofClient.add(c);
                new Thread((Runnable) c).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
