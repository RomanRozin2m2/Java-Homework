package util;

public class Util {

    public static void print(Object... args){
        for(Object o : args){
            System.out.println(o + " ");
        }
    }

    public static void sleep(long ms){
        try {
            Thread.sleep(ms);
        }
        catch (Exception e) { }
    }
}