package games.tetris;

import static util.Util.sleep;

public class MCThread implements Runnable {
    Field field;
    Controls controls;

    public MCThread(Field field, Controls controls) {
        this.field = field;
        this.controls = controls;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void monitorControls() {
        sleep(10);
        if (controls.getKeyPressed() != Keys.NOTHING){
            if (controls.getKeyPressed() == Keys.GO_LEFT){
                field.moveLeft();
            }
            else if (controls.getKeyPressed() == Keys.GO_RIGHT){
                field.moveRight();
            }
            else if (controls.getKeyPressed() == Keys.CLOCKWISE){
                System.out.println("dd");
                field.rotate(true);
            }
            else if (controls.getKeyPressed() == Keys.ANTI_CLOCKWISE){
                System.out.println("ww");
                field.rotate(false);
            }
            else if (controls.getKeyPressed() == Keys.DROP){
                field.drop();
            }
            controls.reset();
        }
    }

    public void run() {
        while (true) {
            monitorControls();
        }
    }
}
