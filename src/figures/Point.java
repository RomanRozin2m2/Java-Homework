package figures;

public class Point implements Location{
    int x;
    int y;

    public Point(int xipt, int yipt){
        x = xipt;
        y = yipt;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setX(int xipt) { x = xipt; }

    public void setY(int yipt) { y = yipt; }

    public void setLocation(int xipt, int yipt) {
        x = xipt;
        y = yipt;
    }
}
