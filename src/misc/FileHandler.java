package misc;

import java.io.*;
import data.*;

public class FileHandler {

    public static void save(String path, String text){
        try{
            text = text.replaceAll("\n[ \n\t\r]*\n", "\n");
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(text);
            bw.close();
        }
        catch (IOException ex){
            System.out.println("Ошибка сохранения файла: " + ex.getMessage());
        }
    }

    public static String load(String path){
        StringBuilder ContentBuilder = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine = br.readLine();
            while(sCurrentLine != null){
                ContentBuilder.append(sCurrentLine).append("\n");
                sCurrentLine = br.readLine();
            }
        }
        catch (IOException ex){
            System.out.println("Ошибка загрузки файла: " + ex.getMessage());
        }
        return ContentBuilder.toString().trim();
    }

    public static <T> void saveObject(T object, String path){
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        }
        catch (Exception ex){
            System.out.println("Ошибка сохранения объекта: " + ex.getMessage());
        }
    }

    public static <T> T loadObject(String path){
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream oin = new ObjectInputStream(fis);
            return (T) oin.readObject();
        }
        catch (Exception ex){
            System.out.println("Ошибка загрузки объекта: " + ex.getMessage());
        }
        return null;
    }
}
