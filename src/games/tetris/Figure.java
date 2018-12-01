package games.tetris;

import java.util.Random;

public class Figure {
    Random random = new Random();
    int width;
    int height;
    Cell[][] cells;

    static int[][][] predefinedFigures = new int[][][] {
            {
                {0, 1, 0},
                {1, 1, 1}
            },
            {
                {1, 1, 0},
                {0, 1, 1}
            },
            {
                {1, 1},
                {1, 1}
            },
            {
                {1, 1, 1},
                {1, 0, 0}
            },
            {
                {0, 1, 1},
                {1, 1, 0}
            },
            {
                {1, 0, 0},
                {1, 1, 1}
            },
            {
                {1, 1, 1, 1}
            },
    };

    public Figure (){
        int t = random.nextInt(predefinedFigures.length);
        int[][] currFigure = predefinedFigures[t];

        width = currFigure[0].length;
        height = currFigure.length;
        cells = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell();
                if (currFigure[j][i] == 1) {
                    cells[i][j].setFilled(true);
                }
            }
        }



    }
}