package games.tetris;

import static util.Util.*;

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
                    while(tetrisGUI.field.clearFullLines());
                    if (!tetrisGUI.field.createBlock()){
                        System.out.println("can't");
                    }
                }
                sleep(500);
            }
        }).start();
    }
}
