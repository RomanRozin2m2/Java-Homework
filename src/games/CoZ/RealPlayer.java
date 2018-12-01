package games.CoZ;

import java.util.Scanner;

public class RealPlayer implements AbstractPlayer {
    private Scanner sc = new Scanner(System.in);

    public void endGame(Field field) {
        field.printField();
    }

    public void decide(Field field, CoZ_Node side) {
        int x, y;
        while (true) {

            field.printField();

            while (true) {
                System.out.println("Ты играешь за " + side.getChar() + ". Введите координату Y от 1 до 3 для своего хода:");
                try {
                    y = sc.nextInt() - 1;
                } catch (Exception ex) {
                    System.out.println("Вы отправили не число. Попробуйте еще раз.");
                    continue;
                }
                if (y > 2 || y < 0) {
                    System.out.println("Вы отправили не число от 1 до 3. Попробуйте еще раз.");
                    continue;
                }
                break;
            }

            while (true) {
                System.out.println("Ты играешь за " + side.getChar() + ". Введите координату X от 1 до 3 для своего хода:");
                try {
                    x = sc.nextInt() - 1;
                } catch (Exception ex) {
                    System.out.println("Вы отправили не число. Попробуйте еще раз.");
                    continue;
                }
                if (x > 2 || x < 0) {
                    System.out.println("Вы отправили не число от 1 до 3. Попробуйте еще раз.");
                    continue;
                }
                break;
            }

            if (field.cellIsEmpty(y, x)) {
                if (side == CoZ_Node.CROSS) {
                    field.setCross(y, x);
                } else if (side == CoZ_Node.ZERO) {
                    field.setZero(y, x);
                }
                return;
            }
            else {
                System.out.println("Клетка уже занята, попробуйте еще раз!");
            }
        }
    }

}
