package games.tetris;

import static util.Util.print;
import static util.Util.sleep;

public class Tetris {
    TetrisGUI tetrisGUI;

    public Tetris(int h, int w, boolean cycling) {
        tetrisGUI = new TetrisGUI(w, h, cycling);

        new Thread(() -> {
            while (true){
                if(tetrisGUI.field.isNeedToUpdate()){
                    tetrisGUI.setField();
                    tetrisGUI.field.noNeedToUpdate();
                }
                sleep(10);
            }
        }).start();

        new Thread(() -> {
            while (true){
                if (tetrisGUI.field.isNeedToAdvance()) {
                    tetrisGUI.field.advance();
                } else {
                    tetrisGUI.field.createBlock();
                }
                sleep(500);
            }
        }).start();
    }
}
