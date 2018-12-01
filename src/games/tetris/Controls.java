package games.tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Controls implements KeyListener {
    private Keys keyPressed = Keys.NOTHING;

    public Keys getKeyPressed() {
        return keyPressed;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            keyPressed = Keys.CLOCKWISE;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            keyPressed = Keys.ANTI_CLOCKWISE;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keyPressed = Keys.GO_RIGHT;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            keyPressed = Keys.GO_LEFT;
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            keyPressed = Keys.DROP;
        }
    }

    public void keyReleased(KeyEvent e) {
        keyPressed = Keys.NOTHING;
    }
}
