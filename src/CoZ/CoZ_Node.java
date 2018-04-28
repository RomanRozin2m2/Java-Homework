package CoZ;

public enum CoZ_Node {
    CROSS, ZERO, NOTHING;

    public char getChar(){
        CoZ_Node node = this;
        if (node == CoZ_Node.ZERO){
            return '0';
        }
        else if (node == CoZ_Node.NOTHING){
            return '*';
        }
        else if (node == CoZ_Node.CROSS){
            return 'X';
        }
        else {
            return '*';
        }
    }
}
