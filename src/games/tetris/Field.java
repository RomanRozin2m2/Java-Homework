package games.tetris;

public class Field {
    Cell[][] field;
    public final int fieldHeight;
    public final int fieldWidth;
    boolean isCycled;
    boolean isNeedToUpdate;
    int currRotation;
    int possibleRotations;
    int[][][] currFigure;

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
        Figure block = new Figure();

        // Y is first index, X is second index!!!
        currFigure = block.cells;

        possibleRotations = block.cells.length;
        currRotation = 0;

        int[][] currFigure = this.currFigure[0];

        int width, height;
        width = currFigure[0].length;
        height = currFigure.length;

        int topLeftX = (fieldWidth - width) / 2;
        int topLeftY = fieldHeight - 1;

        int downLeftX = topLeftX;
        int downLeftY = topLeftY - height + 1;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int currFieldX = downLeftX + x;
                int currFieldY = downLeftY + y;

                if (field[currFieldX][currFieldY].isFilled && currFigure[y][x] == 1) {
                    return false;
                }
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int currFieldX = downLeftX + x;
                int currFieldY = downLeftY + y;

                if (currFigure[y][x] == 1) {
                    field[currFieldX][currFieldY].setFilled(true);
                    field[currFieldX][currFieldY].setFlying(true);
                }
            }
        }

        isNeedToUpdate = true;

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

    public void rotate(boolean doRotateNext){
        //SEARCH
        int topLeftX = fieldWidth - 1;
        int topLeftY = 0;
        int alreadyFound = 0;

        for (int x = 0; x < fieldWidth; x++){
            for (int y = fieldHeight - 1; y >= 0; y--){
                if (field[x][y].isFilled && field[x][y].isFlying) {
                    if (x < topLeftX){
                        topLeftX = x;
                    }
                    if (y > topLeftY){
                        topLeftY = y;
                    }
                    alreadyFound++;
                }
            }
        }

        if (alreadyFound == 0) {
            return;
        }

        //NEW FIGURE
        int[][] rotatedFigure;
        int oldRotation = currRotation;

        if (doRotateNext) {
            currRotation++;
            if (currRotation >= possibleRotations) {
                currRotation = 0;
            }
        }
        else {
            currRotation--;
            if (currRotation < 0) {
                currRotation = possibleRotations - 1;
            }
        }

        rotatedFigure = currFigure[currRotation];

        //CHECK
        int width, height;
        width = rotatedFigure[0].length;
        height = rotatedFigure.length;

        int downLeftX = topLeftX;
        int downLeftY = topLeftY - height + 1;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int currFieldX = downLeftX + x;
                int currFieldY = downLeftY + y;

                if (field[currFieldX][currFieldY].isFilled &&
                    !field[currFieldX][currFieldY].isFlying &&
                    rotatedFigure[y][x] == 1) {

                    currRotation = oldRotation;
                    return;
                }
            }
        }

        //DELETE OLD
        for (int x = 0; x < fieldWidth; x++) {
            for (int y = 0; y < fieldHeight; y++) {
                if (field[x][y].isFlying && field[x][y].isFilled){
                    field[x][y].isFilled = false;
                    field[x][y].isFlying = false;
                }
            }
        }

        //CREATE NEW
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int currFieldX = downLeftX + x;
                int currFieldY = downLeftY + y;

                if (rotatedFigure[y][x] == 1) {
                    field[currFieldX][currFieldY].isFlying = true;
                    field[currFieldX][currFieldY].isFilled = true;
                }
            }
        }

        isNeedToUpdate = true;
    }

    public void drop(){
        while (isNeedToAdvance()){
            advance();
        }
    }
}
