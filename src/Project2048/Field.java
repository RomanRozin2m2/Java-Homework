package Project2048;

public class Field {
    final int fieldSize = 4;
    Node[][] field = new Node[fieldSize][fieldSize];

    boolean checkForWin(){
        for (int h = 0; h < fieldSize; h++){
            for (int i = 0; i < fieldSize; i++){
                if (field[h][i].value == 2048){
                    return true;
                }
            }
        }
        return false;
    }

    void moveTo(Direction dir){
        if (dir == Direction.UP){
            for (int h = 0; h < fieldSize - 1; h++){
                for (int i = fieldSize - 1; i > 0; i--){
                   if (field[h][i].value == field[h][i - 1].value){
                       field[h][i - 1].value = field[h][i - 1].value + field[h][i].value;
                       field[h][i].value = 0;
                   }
                }
            }
        }
        else if (dir == Direction.DOWN){
            for (int h = 0; h < fieldSize - 1; h++){
                for (int i = 0; i < fieldSize - 1; i++){
                    if (field[h][i].value == field[h][i + 1].value){
                        field[h][i + 1].value = field[h][i + 1].value + field[h][i].value;
                        field[h][i].value = 0;
                    }
                }
            }
        }
        else if (dir == Direction.RIGHT){
            for (int h = 0; h < fieldSize - 1; h++){
                for (int i = 0; i < fieldSize - 1; i++){
                    if (field[i][h].value == field[i][h + 1].value){
                        field[i][h + 1].value = field[i][h + 1].value + field[i][h].value;
                        field[i][h].value = 0;
                    }
                }
            }
        }
        else if (dir == Direction.LEFT){
            for (int h = fieldSize - 1; h > 0; h--){
                for (int i = 0; i < fieldSize - 1; i++){
                    if (field[i][h].value == field[i][h - 1].value){
                        field[i][h - 1].value = field[i][h - 1].value + field[i][h].value;
                        field[i][h].value = 0;
                    }
                }
            }
        }
    }
}
