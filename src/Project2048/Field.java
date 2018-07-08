package Project2048;

import java.util.Random.*;

public class Field {
    final int fieldSize = 4;
    Node[][] field = new Node[fieldSize][fieldSize];

    boolean isEmpty(int x, int y){
        if (field[x][y] != null) {
            if (field[x][y].getValue() == 0) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return true;
        }
    }

    void generate(){
        for (int h = 0; h < fieldSize; h++){
            for (int i = 0; i < fieldSize; i++){
                if (isEmpty(h, i)){
                    if (field[h][i] != null){
                        field[h][i].setValue(2);
                    }
                    else {
                        field[h][i] = new Node(h, i, 2);
                    }
                }
            }
        }
    }

    boolean checkForWin(){
        for (int h = 0; h < fieldSize; h++){
            for (int i = 0; i < fieldSize; i++){
                if (field[h][i].getValue() == 2048){
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
                   if (field[h][i].getValue() == field[h][i - 1].getValue()){
                       field[h][i - 1].setValue(field[h][i - 1].getValue() + field[h][i].getValue());
                       field[h][i].setValue(0);
                   }
                }
            }
        }
        else if (dir == Direction.DOWN){
            for (int h = 0; h < fieldSize - 1; h++){
                for (int i = 0; i < fieldSize - 1; i++){
                    if (field[h][i].getValue() == field[h][i + 1].getValue()){
                        field[h][i + 1].setValue(field[h][i + 1].getValue() + field[h][i].getValue());
                        field[h][i].setValue(0);
                    }
                }
            }
        }
        else if (dir == Direction.RIGHT){
            for (int h = 0; h < fieldSize - 1; h++){
                for (int i = 0; i < fieldSize - 1; i++){
                    if (field[i][h].getValue() == field[i][h + 1].getValue()){
                        field[i][h + 1].setValue(field[i][h + 1].getValue() + field[i][h].getValue());
                        field[i][h].setValue(0);
                    }
                }
            }
        }
        else if (dir == Direction.LEFT){
            for (int h = fieldSize - 1; h > 0; h--){
                for (int i = 0; i < fieldSize - 1; i++){
                    if (field[i][h].getValue() == field[i][h - 1].getValue()){
                        field[i][h - 1].setValue(field[i][h - 1].getValue() + field[i][h].getValue());
                        field[i][h].setValue(0);
                    }
                }
            }
        }
    }
    
    int scanForBiggest(){
        int lastBiggestNum = 0;
        for (int h = 0; h < fieldSize; h++){
            for (int i = 0; i < fieldSize; i++){
                int curNum = field[h][i].getValue();
                if (curNum >= lastBiggestNum) {
                    lastBiggestNum = curNum;
                }
            }
        }
        return lastBiggestNum;
    }
}
