package examples;

import examples.Lambdas.Class1;
import examples.Lambdas.Interface0;

public class Main {
    public static void main(String[] args){
        //System.out.println(1); test1();
        //System.out.println(2); test2();
        //System.out.println(3); test3();
        //System.out.println(4); test4();
        System.out.println(5); test5();
        //System.out.println(6); test6();
    }

    private static void test1() {
        class ExtendedClass1 extends Class1 {
            int anyMethodClass1() {
                System.out.println("ExtendedClass1::anyMethodClass1");
                return 5;
            }
        }

        ExtendedClass1 extendedClass1 = new ExtendedClass1();

        Lambdas.funcClass1(extendedClass1);
    }

    private static void test2() {
        class ImplementedInterface0 implements Interface0 {
            public int anyMethodInterface0() {
                System.out.println("ImplementedInterface0::anyMethodInterface0");
                return 6;
            }
        }

        ImplementedInterface0 implementedInterface0 = new ImplementedInterface0();

        Lambdas.funcInterface0(implementedInterface0);
    }

    private static void test3() {
        Lambdas.funcInterface0(new Interface0() {
            public int anyMethodInterface0() {
                System.out.println("Interface0::anyMethodInterface0");
                return 10;
            }
        });
    }

    private static void test4() {
        Lambdas.funcInterface0(() -> {
            System.out.println("Interface0::anyMethodInterface0");
            return 20;
        });
    }

    private static void test5(){
        Lambdas.funcInterface0(() -> 5);
        Lambdas.funcInterface1(x -> x + 5);
        Lambdas.funcInterface2((x, y) -> x + y);
        Lambdas.funcInterface3((x, y, z) -> x - y + z);
        Lambdas.funcInterfaceVoid(() -> System.out.println("234234"));
    }

    private static void test6() {
        Lambdas.funcInterface1(x -> x + 15);

        // Передали функцию максимума
        Lambdas.funcInterface2((x, y) -> {
            if (x > y) {
                return x;
            } else {
                return y;
            }
        });

        // Передали функцию минимума
        Lambdas.funcInterface2((x, y) -> {
            if (x > y) {
                return y;
            } else {
                return x;
            }
        });


        // Применение тернарного оператора if ? then : else для максимума и минимума
        Lambdas.funcInterface2((x, y) -> x > y ? x : y);
        Lambdas.funcInterface2((x, y) -> x > y ? y : x);

        // Применение стандартной функции
        Lambdas.funcInterface2(Math::max);
        Lambdas.funcInterface2(Math::min);

        // Применение собственной функции
        Lambdas.funcInterface2(Main::myMaximum);
        Lambdas.funcInterface2(Main::myMinimum);


        for (int i = 10; i <= 15; i++) {
            System.out.print("i = " + i + " ");

            class SaveZ {
                SaveZ(int i) {
                    z = i;
                }
                int z;
            }

            SaveZ z = new SaveZ(i);

            Thread t = new Thread(() -> {
                System.out.println(123);
            });

            t.start();

            Lambdas.funcInterface2((x, y) -> {
                int absX = Math.abs(x - z.z);
                int absY = Math.abs(y - z.z);
                z.z++;
                if (absX < absY){
                    return x;
                }
                else{
                    return y;
                }
            });
        }
    }

    private static int myMaximum(int a, int b){
        if(a > b) {
            return a;
        } else {
            return b;
        }
    }

    private static int myMinimum(int a, int b) {
        if(a > b){
            return b;
        } else {
            return a;
        }
    }
}
