package games.tetris;
import java.awt.*;

public class Cell {
    boolean isFilled;
    boolean isFlying;
    int[][] rotations;
    Color color;
    static Color background = new Color(0xFFFFFF);;
    static Color filled = new Color(0);

    public Cell(){
        rotations = new int[2][4];
        isFilled = false;
        isFlying = false;
        color = background;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
        if (isFilled){
            color = Cell.filled;
        }
        else {
            color = background;
        }
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }
}
