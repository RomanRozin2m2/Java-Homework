package games.Project2048;

public class Game2048 {
    Field field;
    AbstractPlayer2048 player2048;

    public Game2048(AbstractPlayer2048 player, GUI_2048 gui, int winnum) {
        player2048 = player;
        field = new Field(gui, winnum);
    }

    public int startGame(){
        return mainLoop();
    }

    public int mainLoop(){
        field.generate();
        field.generate();
        while (true){
            player2048.decide(field);
            if(field.checkForWin() && false){
                System.out.println("Ты победил!");
                break;
            }
        }
        return field.scanForBiggest();
    }
}
