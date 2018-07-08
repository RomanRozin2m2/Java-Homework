package Project2048;

import java.util.Scanner;

public class RealPlayer implements AbstractPlayer2048 {

    Scanner sc = new Scanner(System.in);

    public void endGame(Field field) {
        System.out.println("Ваш рекорд - " + field.scanForBiggest());
    }

    public void decide(Field field) {
        Direction dir;
        int answer;
        while (true) {
            System.out.println("Выберите направление сдвига. 1 - вверх, 2 - вниз, 3 - влево, 4 - вправо.");
            answer = sc.nextInt();
            if (answer < 1 || answer > 4) {
                System.out.println("Вы отправили не число. Попробуйте ещё раз.");
            }
            else {
                break;
            }
        }
        if (answer == 1){
            dir = Direction.UP;
        }
        else if (answer == 2){
            dir = Direction.DOWN;
        }
        else if (answer == 3){
            dir = Direction.LEFT;
        }
        else {
            dir = Direction.RIGHT;
        }
        field.moveTo(dir);
    }
}
