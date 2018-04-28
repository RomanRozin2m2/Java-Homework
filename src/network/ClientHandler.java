package network;

import data.CHList;
import data.PasswordKeeper;
import data.Queue;
import data.TextBox;
import misc.FileHandler;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket client;
    private String clientName;
    private Queue<String> SocketMessages;
    private TextBox SocketHistory;
    private CHList clientList;
    private DataInputStream input;
    private int pingCounter;
    private PasswordKeeper pKeeper;
    //УВЕЛИЧИТЬ ПИНГТАЙМ ЕСЛИ СЕРВЕР ВИСНЕТ
    private final int pingTime = 200;
    private DataOutputStream output;
    private boolean isLogged;
    private static final String passToHist = "history.txt";
    private FileHandler SavedSHistory;

    private class SocketListener implements Runnable{

        public void run() {
            String line = null;
            while (true){
                try {
                    line = input.readUTF();
                }
                catch (Exception x){
                    return;
                }
                System.out.println("GOT MESSAGE FROM " + clientName + ": " + line);
                SocketMessages.add(line);
            }
        }
    }

    public ClientHandler(Socket socks, CHList cList, TextBox sHistory, PasswordKeeper passwordKeeper, FileHandler history) throws Exception{
        client = socks;
        pKeeper = passwordKeeper;
        clientList = cList;
        pingCounter = -1000;
        input = new DataInputStream(client.getInputStream());
        output = new DataOutputStream(client.getOutputStream());
        SocketMessages = new Queue<String>();
        SocketHistory = sHistory;
        SocketHistory.addToStart(FileHandler.load(passToHist));
        SavedSHistory = history;
    }

    public void run() {
        new Thread(new SocketListener()).start();
        System.out.println("Starting to listen...");

        while (true){
            try {
                Thread.sleep(10);
            }catch(InterruptedException e){}
            if (SocketMessages.getLength() != 0){
                String kuritsa = SocketMessages.get();
                JSONObject chicken = new JSONObject(kuritsa);
                if(isLogged){
                    String type = chicken.get("type").toString();
                    String message = chicken.get("message").toString().trim();
                    if(type.equals("msg")) {
                        String mesa = "[" + clientName + "]: " + message;
                        String j = getJSONmessage("msg", mesa);
                        sendToAllClients(j);
                        SocketHistory.addToEnd(mesa);
                        SavedSHistory.save(passToHist, SocketHistory.pureToString());
                    }
                    else if(type.equals("ping")){
                        pingCounter = -pingTime;
                    }
                }
                else {
                    String type = chicken.get("type").toString();
                    String login = chicken.get("login").toString();
                    String password = chicken.get("password").toString();
                    if (type.equals("reg")){
                        if (login.length() < 3){
                            sendToClient(getJSONmessage("err", "Логин слишком маленький!"));
                        }
                        else if (password.length() < 6){
                            sendToClient(getJSONmessage("err", "Пароль слишком маленький!"));
                        }
                        else {
                            try {
                                pKeeper.add(login, password);
                                sendToClient(getJSONmessage("cfg", ""));
                                sendToClient(getJSONmessage("msg", SavedSHistory.load(passToHist)));
                                clientName = login;
                                String mesa = clientName + " присоединился к чату.";
                                sendToAllClients(getJSONmessage("msg", mesa));
                                SocketHistory.addToEnd(mesa);
                                SavedSHistory.save(passToHist, SocketHistory.pureToString());
                                isLogged = true;
                            }
                            catch (Exception ex){
                                sendToClient(getJSONmessage("err", "Такой логин уже существует!"));
                            }
                        }
                    }
                    else if (type.equals("auth")){
                        boolean isAlreadyLogged = false;
                        for (int foest = 0; foest < clientList.getLength(); foest++) {
                            if (clientList.getValue(foest).getLogged()){
                                if (clientList.getValue(foest).getClientName().equals(login)){
                                    isAlreadyLogged = true;
                                    break;
                                }
                            }
                        }
                        if(isAlreadyLogged){
                            sendToClient(getJSONmessage("err", "Вы уже подключились к серверу!"));
                        }
                        else {
                            if (pKeeper.check(login, password)) {
                                sendToClient(getJSONmessage("cfg", ""));
                                sendToClient(getJSONmessage("msg", SavedSHistory.load(passToHist)));
                                clientName = login;
                                String mesa = clientName + " присоединился к чату.";
                                sendToAllClients(getJSONmessage("msg", mesa));
                                SocketHistory.addToEnd(mesa);
                                SavedSHistory.save(passToHist, SocketHistory.pureToString());
                                isLogged = true;
                            } else {
                                sendToClient(getJSONmessage("err", "Неправильный логин/пароль!"));
                            }
                        }
                    }
                }
            }

            if (isLogged){
                pingCounter++;

                if(pingCounter == 0){
                    sendToClient(getJSONmessage("ping", ""));
                }
                else if(pingCounter >= pingTime){
                    deleteClient();
                }
            }

        }
    }

    private String getJSONmessage(String type, String message){
        JSONObject j = new JSONObject();
        return j.put("type", type).put("message", message).toString();
    }

    public void sendToAllClients(String message) {
        ClientHandler[] clientArray = clientList.toClientsArray();
        for(int foest = 0; foest < clientArray.length; foest++){
            clientArray[foest].sendToClient(message);
        }
    }

    public void sendToClient(String message) {
        try{
            System.out.println("Trying to send " + message + " to " + clientName + ".");
            output.writeUTF(message);
            output.flush();
        }
        catch (IOException ex){

        }
    }

    public boolean getLogged(){
        return isLogged;
    }

    public String getClientName() {
        return clientName;
    }

    private void deleteClient(){
        int index = clientList.getIndex(this);
        if(index >= 0) {
            System.out.println("Deleting " + clientName + ".");
            clientList.delete(index);
            String mesa = clientName + " покинул нас.";
            sendToAllClients(getJSONmessage("msg", mesa));
            SocketHistory.addToEnd(mesa);
            SavedSHistory.save(passToHist, SocketHistory.pureToString());
        }
    }

}
