package games.tetris;

import java.util.Random;

public class Figure {
    Random random = new Random();
    int[][][] cells;

    class SingleFigure {

    }

    class FigureType {

    }

    static int[][][][] predefinedFigures = new int[][][][] {
            {
                {
                    {0, 1, 0},
                    {1, 1, 1},
                },
                {
                    {1, 0},
                    {1, 1},
                    {1, 0},
                },
                {
                    {1, 1, 1},
                    {0, 1, 0}
                },
                {
                    {0, 1},
                    {1, 1},
                    {0, 1},
                },
            },
            {
                {
                    {1, 1, 0},
                    {0, 1, 1},
                },
                {
                    {0, 1},
                    {1, 1},
                    {1, 0},
                },
            },
            {
                {
                    {1, 1},
                    {1, 1},
                }
            },
            {
                {
                    {1, 1, 1},
                    {1, 0, 0},
                },
                {
                    {1, 1},
                    {0, 1},
                    {0, 1},
                },
                {
                    {0, 0, 1},
                    {1, 1, 1}
                },
                {
                    {1, 0},
                    {1, 0},
                    {1, 1},
                },
            },
            {
                {
                    {0, 1, 1},
                    {1, 1, 0}
                },
                {
                    {1, 0},
                    {1, 1},
                    {0, 1},
                },
            },
            {
                {
                    {1, 0, 0},
                    {1, 1, 1},
                },
                {
                    {1, 1},
                    {1, 0},
                    {1, 0},
                },
                {
                    {1, 1, 1},
                    {0, 0, 1}
                },
                {
                    {0, 1},
                    {0, 1},
                    {1, 1},
                },
            },
            {
                {
                    {1, 1, 1, 1}
                },
                {
                    {1},
                    {1},
                    {1},
                    {1},
                }
            },
    };

    public Figure (){
        int t = random.nextInt(predefinedFigures.length);
        cells = predefinedFigures[t];
    }
}