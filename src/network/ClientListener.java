package network;

import data.Queue;
import graphics.GraphClient;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientListener implements Runnable {
    private Socket client;
    private Queue<String> KeyboardMessages;
    private DataOutputStream output;
    private DataInputStream input;
    private Queue<String> SocketMessages;
    private int pingCounter;
    private int pingTime;
    private GraphClient graph = null;

    private class KeyboardListener implements Runnable{

        public void run() {
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while (true){
                try {
                    line = keyboard.readLine();
                }
                catch (Exception x){
                    return;
                }
                KeyboardMessages.add(line);
            }
        }
    }

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
                SocketMessages.add(line);
                System.out.println("Got - " + line);
            }
        }
    }

    public ClientListener(Socket socks) throws Exception{
        client = socks;
        input = new DataInputStream(client.getInputStream());
        output = new DataOutputStream(client.getOutputStream());
        SocketMessages = new Queue<String>();
        KeyboardMessages = new Queue<String>();
        pingCounter = 0;
        pingTime = 0;
}

    public void run() {
        new Thread(new KeyboardListener()).start();
        new Thread(new SocketListener()).start();

        while (true){
            try {
                Thread.sleep(10);
            }catch(InterruptedException e){}
            if(KeyboardMessages.getLength() != 0){
                if(!sendToServer("msg", KeyboardMessages.get())){
                    graph.sendAlert();
                }
            }

            if (SocketMessages.getLength() != 0){
                if(graph == null) {
                    System.out.println(SocketMessages.get());
                }
                else{
                    graph.receiveMessage(SocketMessages.get());
                }
            }

            pingCounter++;
            if(pingCounter > pingTime && pingTime > 0){
                graph.sendAlert();
            }
        }
    }

    public void setGraphClient(GraphClient graphic){
        graph = graphic;
    }

    private String getJSONmessage(String type, String message){
        JSONObject j = new JSONObject();
        return j.put("type", type).put("message", message).toString();
    }

    public void auth(String login, String pass){
        sendRawMsg(getJSONlogin("auth", login, pass));
    }

    public void register(String login, String pass){
        sendRawMsg(getJSONlogin("reg", login, pass));
    }

    private String getJSONlogin(String type, String login, String pass){
        JSONObject j = new JSONObject();
        return j.put("type", type).put("login", login).put("password", pass).toString();
    }

    public boolean sendToServer(String type, String message) {
        return sendRawMsg(getJSONmessage(type, message));
    }

    public boolean sendRawMsg(String message){
        try {
            output.writeUTF(message);
            output.flush();
        }
        catch (Exception x){
            return false;
        }
        return true;
    }

    public boolean sendPing() {
        try {
            output.writeUTF(getJSONmessage("ping", ""));
            output.flush();
        }
        catch (Exception x){
            return false;
        }
        pingCounter = 0;
        if(pingTime == 0){
            pingTime = 500;
        }
        return true;
    }

    public GraphClient getGraph() {
        return graph;
    }
}
