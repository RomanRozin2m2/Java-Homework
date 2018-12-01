package games.Project2048;

import java.util.Scanner;

public class RealPlayer implements AbstractPlayer2048 {

    Scanner sc = new Scanner(System.in);
    public Direction dir = Direction.NOT_SELECTED;

    public void endGame(Field field) {
        System.out.println("Ваш рекорд - " + field.scanForBiggest());
    }

    public void setDirection(Direction d){
        dir = d;
    }

    public void decide(Field field) {
        while (dir == Direction.NOT_SELECTED) {
            try {
                Thread.sleep(15);
            }
            catch (InterruptedException e){
                System.out.println("Но что-то идет не так. RealPlayer.decide. Поток умер во сне.");
            }
        }
        field.moveTo(dir);
        dir = Direction.NOT_SELECTED;
    }
}
