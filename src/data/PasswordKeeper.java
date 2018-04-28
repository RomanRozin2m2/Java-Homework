package data;

import misc.FileHandler;
import org.json.JSONObject;

public class PasswordKeeper extends HashMap<String, Integer> {
    private static String passToDB = "keys.txt";

    public PasswordKeeper(){
        String data = FileHandler.load(passToDB);
        JSONObject jason = new JSONObject(data);
        for(Object type: jason.keySet().toArray()){
            String login = (String) type;
            setValue(login, (Integer)jason.get(login));
        }
    }

    public PasswordKeeper(String keys){
        passToDB = keys;
        String data = FileHandler.load(passToDB);
        JSONObject jason = new JSONObject(data);
        for(Object type: jason.keySet().toArray()){
            String login = (String) type;
            setValue(login, (Integer)jason.get(login));
        }
    }

    public void add(String login, String password) throws Exception{
        if (checkKey(login)){
            throw new Exception("This login already exists!");
        }
        else {
            setValue(login, password.hashCode());
        }
        FileHandler.save(passToDB, getJSON());
    }

    private String getJSON(){
        JSONObject jason = new JSONObject();
        for(int foest = 0; foest < length; foest++){
            Node<KeyValue>[] valist = uberList[foest].toArray();
            for(int foe = 0; foe < valist.length; foe++){
                jason.put(valist[foe].getValue().key, valist[foe].getValue().value);
            }
        }
        return jason.toString();
    }

    public boolean check(String login, String password){
        Integer val = getValue(login);
        return val != null && val.equals(password.hashCode());
    }

}
