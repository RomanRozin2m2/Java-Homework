package network;

import java.net.*;
import java.io.*;

public class MessageClient {
    private ClientListener listen;

    public MessageClient(){
        int port = 25565;
        String adress = "127.0.0.1";

        try{
            InetAddress ipAdress = InetAddress.getByName(adress);
            System.out.println("Socket " + adress + ":" + port);
            Socket socket = new Socket(ipAdress, port);
            ClientListener listener = new ClientListener(socket);
            listen = listener;
            new Thread(listener).start();
        }
        catch (Exception x){ }
    }

    public ClientListener getListener(){
        return listen;
    }
}
