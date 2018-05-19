package examples;

public class Lambdas {

    static abstract class Class1{
        abstract int anyMethodClass1();
    }

    interface Interface0{
        int anyMethodInterface0();
    }

    interface Interface1{
        int anyMethodInterface1(int x);
    }

    interface Interface2{
        int anyMethodInterface2(int x, int y);
    }

    interface Interface3{
        int anyMethodInterface3(int x, int y, int z);
    }

    public static void funcClass1(Class1 obj){
        System.out.println("Number returned: " + obj.anyMethodClass1());
    }

    public static void funcInterface0(Interface0 obj){
        System.out.println("Number returned: " + obj.anyMethodInterface0());
    }

    public static void funcInterface1(Interface1 obj){
        System.out.println("Number from: " + 10 + ", Number returned: " + obj.anyMethodInterface1(10));
    }

    public static void funcInterface2(Interface2 obj){
        System.out.println("Number from: " + 10 + ", " + 15 + ", Number returned: " +
                obj.anyMethodInterface2(10, 15));
    }

    public static void funcInterface3(Interface3 obj){
        System.out.println("Number from: " + 10 + ", " + 15 + ", " + 30 + ", Number returned: " +
                obj.anyMethodInterface3(10, 15, 30));
    }


}
