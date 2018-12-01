package games.Project2048;

import java.util.Random;

public class Field {
    int fieldSize;
    Node[][] field;
    GUI_2048 gui_2048;
    int winnum;

    public Field (GUI_2048 gui, int winnum){
        gui_2048 = gui;
        fieldSize = gui_2048.getFieldSize();
        this.winnum = winnum;
        field = new Node[fieldSize][fieldSize];
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field.length; j++){
                field[i][j] = new Node(i, j, 0);
            }
        }
    }

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
        Random random = new Random();
        for (int h = 0; h < fieldSize; h++){
            for (int i = 0; i < fieldSize; i++){
                if (isEmpty(h, i)){
                    int val = random.nextInt(100);
                    int new_cell = 0;
                    if (val == 0) {
                        new_cell = 4;
                    }
                    else if (val < 20){
                        new_cell = 2;
                    }
                    else {
                        new_cell = 0;
                    }
                    if (field[h][i] != null){
                        field[h][i].setValue(new_cell);
                    }
                    else {
                        field[h][i] = new Node(h, i, new_cell);
                    }
                }
            }
        }
    }

    boolean checkForWin(){
        for (int h = 0; h < fieldSize; h++){
            for (int i = 0; i < fieldSize; i++){
                if (field[h][i].getValue() >= winnum){
                    return true;
                }
            }
        }
        return false;
    }

    public void moveTo(Direction dir){
        if (dir == Direction.UP){
            for (int i = 0; i < field.length; i++){
                Node[] forMAR = new Node[field.length];
                for (int j = field.length - 1, k = 0; j >= 0; j--, k++){
                    forMAR[k] = field[j][i];
                }
                forMAR = moveARow(forMAR);
                for (int j = field.length - 1, k = 0; j >= 0; j--, k++){
                    field[j][i] = forMAR[k];
                }
            }
        }
        if (dir == Direction.DOWN){
            for (int i = 0; i < field.length; i++){
                Node[] forMAR = new Node[field.length];
                for (int k = 0; k < field.length; k++){
                    forMAR[k] = field[k][i];
                }
                forMAR = moveARow(forMAR);
                for (int k = 0; k < field.length; k++){
                    field[k][i] = forMAR[k];
                }
            }
        }
        if (dir == Direction.LEFT){
            for (int i = 0; i < field.length; i++){
                Node[] forMAR = new Node[field.length];
                for (int j = field.length - 1, k = 0; j >= 0; j--, k++){
                    forMAR[k] = field[i][j];
                }
                forMAR = moveARow(forMAR);
                for (int j = field.length - 1, k = 0; j >= 0; j--, k++){
                    field[i][j] = forMAR[k];
                }
            }
        }
        if (dir == Direction.RIGHT){
            for (int i = 0; i < field.length; i++){
                Node[] forMAR = new Node[field.length];
                for (int k = 0; k < field.length; k++){
                    forMAR[k] = field[i][k];
                }
                forMAR = moveARow(forMAR);
                for (int j = field.length - 1, k = 0; j >= 0; j--, k++){
                    field[i][k] = forMAR[k];
                }
            }
        }
        generate();
        gui_2048.refreshCells();
    }

    public void printField(){
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field.length; j++){
                System.out.print(field[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }

    Node[] moveARow(Node[] field){
        for (int i = 0; i < field.length; i++){
            field[i].setNew(false);
        }
        for (int n = 0; n < field.length; n++) {
            for (int i = field.length - 1; i >= 1; i--) {
                if (field[i].getValue() == field[i-1].getValue() && !field[i].isNew() && !field[i-1].isNew()){
                    field[i].setValue(field[i].getValue() + field[i-1].getValue());
                    field[i-1].setValue(0);
                    field[i].setNew(true);
                }
                else if (field[i-1].getValue() != 0 && field[i].getValue() == 0){
                    field[i].setValue(field[i-1].getValue());
                    field[i-1].setValue(0);
                    field[i].setNew(field[i-1].isNew());
                    field[i-1].setNew(false);
                }
            }
        }
        return field;
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
