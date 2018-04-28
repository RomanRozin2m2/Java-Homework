package network;

import data.CHList;
import data.PasswordKeeper;
import data.TextBox;
import misc.FileHandler;

import java.net.*;

public class MessageServer {
    private static CHList clientList = new CHList();
    private static TextBox SocketHistory = new TextBox();
    private static PasswordKeeper PasswordBank = new PasswordKeeper("keys.txt");
    private static FileHandler FileHand = new FileHandler();

    public static void main(String[] ar) throws Exception{
        int port = 25565;
        String ipAdress = "127.0.0.1";
        ServerSocket ss = new ServerSocket(port, 50, InetAddress.getByName(ipAdress));
        System.out.println("Waiting...");

        while (true){
            Socket sock = ss.accept();
            System.out.println("NEW CLIENT HERE");
            ClientHandler handler = new ClientHandler(sock, clientList, SocketHistory, PasswordBank, FileHand);
            clientList.addToEnd(handler);
            new Thread(handler).start();
        }

    }
}
