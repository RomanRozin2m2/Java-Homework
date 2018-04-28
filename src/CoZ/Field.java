package CoZ;

class Field {
    final int fieldSize = 3;
    CoZ_Node[][] field;

        public Field(){
            field = new CoZ_Node[fieldSize][fieldSize];
            clearField();
        }

        void clearField() {
            field[0][0] = CoZ_Node.NOTHING; field[0][1] = CoZ_Node.NOTHING; field[0][2] = CoZ_Node.NOTHING;
            field[1][0] = CoZ_Node.NOTHING; field[1][1] = CoZ_Node.NOTHING; field[1][2] = CoZ_Node.NOTHING;
            field[2][0] = CoZ_Node.NOTHING; field[2][1] = CoZ_Node.NOTHING; field[2][2] = CoZ_Node.NOTHING;
        }

        void setCross(int y, int x) {
            field[y][x] = CoZ_Node.CROSS;
        }

        void setZero(int y, int x) {
            field[y][x] = CoZ_Node.ZERO;
        }
        
        boolean cellIsEmpty(int y, int x){
            return field[y][x] != CoZ_Node.ZERO && field[y][x] != CoZ_Node.CROSS;
        }

        void printField(){
            System.out.println(field[0][0].getChar() + " " + field[0][1].getChar() + " " + field[0][2].getChar() + "\n"
            + field[1][0].getChar() + " " + field[1][1].getChar() + " " + field[1][2].getChar() + "\n"
            + field[2][0].getChar() + " " + field[2][1].getChar() + " " + field[2][2].getChar());
        }

        boolean testForWin() {

            if (field[0][0] == field[0][1] && field[0][0] == field[0][2] && field[0][0] != CoZ_Node.NOTHING) {
                return true;
            }

            else if (field[1][0] == field[1][1] && field[1][0] == field[1][2] && field[1][0] != CoZ_Node.NOTHING) {
                return true;
            }

            else if (field[2][0] == field[2][1] && field[2][0] == field[2][2] && field[2][0] != CoZ_Node.NOTHING) {
                return true;
            }

            else if (field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0] != CoZ_Node.NOTHING) {
                return true;
            }

            else if (field[2][0] == field[1][1] && field[2][0] == field[0][2] && field[2][0] != CoZ_Node.NOTHING) {
                return true;
            }

            else if (field[0][0] == field[1][0] && field[0][0] == field[2][0] && field[0][0] != CoZ_Node.NOTHING) {
                return true;
            }

            else if (field[0][1] == field[1][1] && field[0][1] == field[2][1] && field[0][1] != CoZ_Node.NOTHING) {
                return true;
            }

            else if (field[0][2] == field[1][2] && field[0][2] == field[2][2] && field[0][2] != CoZ_Node.NOTHING) {
                return true;
            }

            return false;
        }

        boolean testForDraw() {
            if(testForWin()){
                return false;
            }
            else if (field[0][0] != CoZ_Node.NOTHING && field[1][0] != CoZ_Node.NOTHING && field[2][0] != CoZ_Node.NOTHING
                    && field[0][1] != CoZ_Node.NOTHING && field[1][1] != CoZ_Node.NOTHING && field[2][1] != CoZ_Node.NOTHING
                    && field[0][2] != CoZ_Node.NOTHING && field[1][2] != CoZ_Node.NOTHING && field[2][2] != CoZ_Node.NOTHING)
            { 
                return true;
            }
            else{
                return false;
            }
        }
    }