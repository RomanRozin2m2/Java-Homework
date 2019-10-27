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

    }

    public void run() {
        while (true) {
            monitorControls();
        }
    }
}
