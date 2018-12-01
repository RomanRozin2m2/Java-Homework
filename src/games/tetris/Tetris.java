package games.tetris;

public class Tetris {
    TetrisGUI tetrisGUI;

    public Tetris(int h, int w, boolean cycling) {
        tetrisGUI = new TetrisGUI(w,h,cycling);
        mainloop();
    }

    private void mainloop(){
        while (true){
            if (true) {
                if (tetrisGUI.field.isNeedToAdvance()) {
                    tetrisGUI.field.advance();
                } else {
                    tetrisGUI.field.createBlock();
                }
                tetrisGUI.setField();
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException ex){

                }
            }
        }
    }
}
