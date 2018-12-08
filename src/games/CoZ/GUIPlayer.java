package games.CoZ;

public class GUIPlayer implements AbstractPlayer {

    public int x;
    public int y;
    public boolean isReady;

    public void endGame(Field field) {

    }

    public void decide(Field field, CoZ_Node side) {
        while (!isReady) {
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException ex) {}
        }
        isReady = false;
        if (side == CoZ_Node.CROSS){
            field.setCross(y, x);
        }
        else if (side == CoZ_Node.ZERO){
            field.setZero(y, x);
        }
        else {
            throw new IllegalArgumentException("Что ты творишь?");
        }
    }
}
