package games.tetris;

public class Field {
    Cell[][] field;
    public final int fieldHeight;
    public final int fieldWidth;
    boolean isCycled;
    boolean isNeedToUpdate;

    public Field(int height, int width, boolean cycling){
        isCycled = cycling;
        fieldHeight = height;
        fieldWidth = width;
        field = new Cell[fieldWidth][fieldHeight];
        for (int h = 0; h < fieldWidth; h++){
            for (int i = 0; i < fieldHeight; i++){
                field[h][i] = new Cell();
            }
        }
    }

    public boolean isNeedToAdvance(){
        for (int i = 0; i < fieldWidth; i++){
            for (int h = 0; h < fieldHeight; h++){
                if (field[i][h].isFlying){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean clearFullLines(){
        for (int y = 0; y < fieldHeight; y++){
            for (int x = 0; x < fieldWidth;){
                if (field[x][y].isFilled){
                    if (x == fieldWidth-1){
                        for (int tx = 0; tx < fieldWidth; tx++){
                            field[tx][y].isFilled = false;
                        }
                        for (int ny = y; ny < fieldHeight-1; ny++){
                            for (int nx = 0; nx < fieldWidth; nx++){
                                field[nx][ny].isFilled = field[nx][ny+1].isFilled;
                                field[nx][ny+1].isFilled = false;
                            }
                        }
                        return true;
                    }
                    else {
                        x++;
                    }
                }
                else{
                    break;
                }
            }
        }
        return false;
    }

    public boolean createBlock(){
        return true;
    }

    public boolean isNeedToUpdate() {
        return isNeedToUpdate;
    }

    public void noNeedToUpdate(){
        isNeedToUpdate = false;
    }

    public void advance(){
        boolean canBeeMoved = true;
        boolean[][] WhatCanBeeMoved = new boolean[fieldWidth][fieldHeight];
        for (int i = fieldWidth - 1; i >= 0; i--){
            for (int h = fieldHeight - 1; h >= 0; h--){
                if (field[i][h].isFilled && field[i][h].isFlying && h == 0){
                    canBeeMoved = false;
                }
                else if (field[i][h].isFilled && field[i][h].isFlying){
                    if (field[i][h-1].isFilled && !field[i][h-1].isFlying){
                        canBeeMoved = false;
                    }
                }
            }
        }
        if (canBeeMoved){
            for (int i = 0; i < fieldWidth; i++){
                for (int h = 0; h < fieldHeight; h++){
                    WhatCanBeeMoved[i][h] = false;
                }
            }
            for (int i = 0; i < fieldWidth; i++){
                for (int h = 0; h < fieldHeight; h++){
                    if (field[i][h].isFlying && field[i][h].isFilled){
                        WhatCanBeeMoved[i][h-1] = true;
                    }
                }
            }
            for (int i = 0; i < fieldWidth; i++){
                for (int h = 0; h < fieldHeight; h++){
                    if (field[i][h].isFlying && field[i][h].isFilled){
                        field[i][h].isFlying = false;
                        field[i][h].isFilled = false;
                    }
                }
            }
            for (int i = 0; i < fieldWidth; i++){
                for (int h = 0; h < fieldHeight; h++){
                    if (WhatCanBeeMoved[i][h]){
                        field[i][h].isFlying = true;
                        field[i][h].isFilled = true;
                    }
                }
            }
        }
        else{
            for (int i = 0; i < fieldWidth; i++) {
                for (int h = 0; h < fieldHeight; h++) {
                    if (field[i][h].isFlying && field[i][h].isFilled) {
                        field[i][h].isFlying = false;
                    }
                }
            }
        }
        isNeedToUpdate = true;
    }

    public void moveRight(){
        boolean canBeeMoved = true;
        boolean[][] WhatCanBeeMoved = new boolean[fieldWidth][fieldHeight];
        for (int i = fieldWidth - 1; i >= 0; i--){
            for (int h = 0; h < fieldHeight; h++){
                if (i == fieldWidth - 1){
                    if (!isCycled){
                        canBeeMoved = false;
                    }
                    else{
                        if (field[i][h].isFilled && field[i][h].isFlying){
                            if (field[0][h].isFilled && !field[0][h].isFlying){
                                canBeeMoved = false;
                            }
                        }
                    }
                }
                else if (field[i][h].isFilled && field[i][h].isFlying){
                    if (field[i+1][h].isFilled && !field[i+1][h].isFlying){
                        canBeeMoved = false;
                    }
                }
            }
        }
        if (canBeeMoved){
            for (int i = 0; i < fieldWidth; i++){
                for (int h = 0; h < fieldHeight; h++){
                    WhatCanBeeMoved[i][h] = false;
                }
            }
            for (int i = 0; i < fieldWidth; i++){
                for (int h = 0; h < fieldHeight; h++){
                    if (field[i][h].isFlying && field[i][h].isFilled){
                        WhatCanBeeMoved[(i+1) % fieldWidth][h] = true;
                    }
                }
            }
            for (int i = 0; i < fieldWidth; i++){
                for (int h = 0; h < fieldHeight; h++){
                    if (field[i][h].isFlying && field[i][h].isFilled){
                        field[i][h].isFlying = false;
                        field[i][h].isFilled = false;
                    }
                }
            }
            for (int i = 0; i < fieldWidth; i++){
                for (int h = 0; h < fieldHeight; h++){
                    if (WhatCanBeeMoved[i][h]){
                        field[i][h].isFlying = true;
                        field[i][h].isFilled = true;
                    }
                }
            }
        }
        isNeedToUpdate = true;
    }

    public void moveLeft(){
        boolean canBeeMoved = true;
        boolean[][] WhatCanBeeMoved = new boolean[fieldWidth][fieldHeight];
        for (int i = 0; i < fieldWidth; i++){
            for (int h = 0; h < fieldHeight; h++){
                if (i == 0){
                    if (!isCycled){
                        canBeeMoved = false;
                    }
                    else{
                        if (field[i][h].isFilled && field[i][h].isFlying){
                            if (field[fieldWidth-1][h].isFilled && !field[fieldWidth-1][h].isFlying){
                                canBeeMoved = false;
                            }
                        }
                    }
                }
                else if (field[i][h].isFilled && field[i][h].isFlying){
                    if (field[i-1][h].isFilled && !field[i-1][h].isFlying){
                        canBeeMoved = false;
                    }
                }
            }
        }
        if (canBeeMoved){
            for (int i = fieldWidth - 1; i >= 0; i--){
                for (int h = 0; h < fieldHeight; h++){
                    WhatCanBeeMoved[i][h] = false;
                }
            }
            for (int i = fieldWidth - 1; i >= 0; i--){
                for (int h = 0; h < fieldHeight; h++){
                    if (field[i][h].isFlying && field[i][h].isFilled){
                        if (i == 0){
                            WhatCanBeeMoved[fieldWidth-1][h] = true;
                        }
                        else{
                            WhatCanBeeMoved[i-1][h] = true;
                        }
                    }
                }
            }
            for (int i = fieldWidth - 1; i >= 0; i--){
                for (int h = 0; h < fieldHeight; h++){
                    if (field[i][h].isFlying && field[i][h].isFilled){
                        field[i][h].isFlying = false;
                        field[i][h].isFilled = false;
                    }
                }
            }
            for (int i = fieldWidth - 1; i >= 0; i--){
                for (int h = 0; h < fieldHeight; h++){
                    if (WhatCanBeeMoved[i][h]){
                        field[i][h].isFlying = true;
                        field[i][h].isFilled = true;
                    }
                }
            }
        }
        isNeedToUpdate = true;
    }

    public boolean isEmpty(int x, int y){
        return !field[x][y].isFilled;
    }

    public boolean checkIndexes(int x, int y){
        return x < fieldWidth && y < fieldHeight && x >= 0 && y >= 0;
    }

    public String printField(){
        String ret = "";
        for (int i = fieldHeight - 1; i >= 0; i--){
            for (int h = 0; h < fieldWidth; h++){
                if (field[h][i].isFilled){
                    ret += "8 ";
                }
                else {
                    ret += "' ";
                }
            }
            ret += "\n";
        }
        return ret;
    }

    public void turnClockwise(){

    }

    public void turnAntiClockwise(){

    }

    public void drop(){
        while (isNeedToAdvance()){
            advance();
        }
    }



}
