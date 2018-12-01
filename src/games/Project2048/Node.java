package games.Project2048;

class Node {
    private int value;
    private int x;
    private int y;
    private boolean isNew;

    Node(int x, int y, int value){
         this.value = value;
         this.x = x;
         this.y = y;
         isNew = false;
     }

    public void setNew(boolean value){
        isNew = value;
    }

    public boolean isNew(){
        return isNew;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
