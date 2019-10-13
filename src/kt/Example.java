package kt;

public class Example {
    public static void main(String[] args) {
        byte a = 12;
        new Example2().qwerty(12, 13);
    }

    void qwerty(int p1, int p2) {
        System.out.println("E");
    }
}

class Example2 extends Example {
    @Override
    void qwerty(int p1, int p2) {
        System.out.println("E2");
    }
}
