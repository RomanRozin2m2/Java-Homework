package CoZ;

public interface AbstractPlayer {
    public void endGame(Field field);

    public void decide(Field field, CoZ_Node side);

    public void decide(Field field, CoZ_GUI guiField, CoZ_Node side);
}
