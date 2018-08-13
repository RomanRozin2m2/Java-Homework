package Project2048;

public class Game2048 {
    Field field = new Field();
    AbstractPlayer2048 player2048;

    public Game2048(AbstractPlayer2048 player) {
        player2048 = player;
    }

    public int startGame(){
        return mainLoop();
    }

    public int mainLoop(){
        field.generate();
        field.generate();
        while (true){
            player2048.decide(field);
            field.generate();
            if(field.checkForWin()){
                System.out.println("Ты победил!");
                break;
            }
        }
        return field.scanForBiggest();
    }
}
