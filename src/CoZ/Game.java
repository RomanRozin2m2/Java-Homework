package CoZ;

public class Game{
    private Field field;
    private CoZ_GUI guiField;
    private CoZ_States state;
    private AbstractPlayer crosses;
    private AbstractPlayer zeros;
    
    public Game(AbstractPlayer crossesPlayer, AbstractPlayer zerosPlayer){
        state  = CoZ_States.NOT_STARTED;
        crosses = crossesPlayer;
        zeros = zerosPlayer;
        field = new Field();
        //guiField = new CoZ_GUI(3);
    }

    public CoZ_Winners startGame() {
        state = CoZ_States.CROSSES_MOVE;
        return mainLoop();
    }

    private CoZ_Winners mainLoop() {
        while (true) {
            state = CoZ_States.CROSSES_MOVE;
            crosses.decide(field, CoZ_Node.CROSS);
            CoZ_Winners winner = testForWinning();
            if (winner == CoZ_Winners.CROSSES){
                return CoZ_Winners.CROSSES;
            }
            else if (winner == CoZ_Winners.DRAW){
                return CoZ_Winners.DRAW;
            }
            state = CoZ_States.ZEROS_MOVE;
            zeros.decide(field, CoZ_Node.ZERO);
            winner = testForWinning();
            if (winner == CoZ_Winners.ZEROS){
                return CoZ_Winners.ZEROS;
            }
            else if (winner == CoZ_Winners.DRAW){
                return CoZ_Winners.DRAW;
            }
        }
    }

    private CoZ_Winners testForWinning(){
        if (field.testForWin()) {
            if (state == CoZ_States.CROSSES_MOVE) {
                //System.out.println("Крестики победили!");
                crosses.endGame(field);
                zeros.endGame(field);
                return CoZ_Winners.CROSSES;
            }
            else if (state == CoZ_States.ZEROS_MOVE) {
                //System.out.println("Нолики победили!");
                zeros.endGame(field);
                crosses.endGame(field);
                return CoZ_Winners.ZEROS;
            }
        }
        else {
            if (field.testForDraw()) {
                //System.out.println("Ничья!");
                zeros.endGame(field);
                crosses.endGame(field);
                return CoZ_Winners.DRAW;
            }
        }
        return CoZ_Winners.NOPE;
    }
}

        
