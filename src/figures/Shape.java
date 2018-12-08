package figures;
import util.Util;

public abstract class Shape implements Location {
    int x;
    int y;
    static int count = 0;

    public Shape(int xipt, int yipt){

        Util.print("In Shape");
        x = xipt;
        y = yipt;
        count += 1;
    }

    public Shape(){
        Util.print("In Shape-2");
        x = 0;
        y = 0;
        count += 1;
    }

    public abstract double calculateArea();

    public abstract double calculatePerimeter();

    public int getX(){
        return x;
    }

    public int getY() { return y; }

    public void setX(int new_x){
        x = new_x;
    }

    public void setY(int new_y) {
        y = new_y;
    }

    public void setLocation(int new_x, int new_y){
        y = new_y;
        x = new_x;
    }

    public void moveadd(int moveXadd, int moveYadd){
        x += moveXadd;
        y += moveYadd;
    }

    public void moverem(int moveXrem, int moveYrem){
        x -= moveXrem;
        y -= moveYrem;
    }

    public void coor(){
        Util.print("Координаты этой формы: X - " + x + ", Y - " + y + ".");
    }

    public String toString(){
        return "Координаты этой формы: X - " + x + ", Y - " + y + ".";
    }
}
