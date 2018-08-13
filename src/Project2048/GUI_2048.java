package Project2048;

import misc.FileHandler;
import java.util.Random;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI_2048 extends JFrame{

    private class Line extends JComponent{

        public void paint(Graphics g){
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        public Line(int lineWidth, int lineHeight){
            setSize(lineWidth,  lineHeight);
            setVisible(true);
        }

    }

    private class RefreshThread implements Runnable {

        public void run() {
            refreshCells();
        }
    }

    private class GameThread implements Runnable {

        public void run() {
            game.startGame();
        }
    }

    private class Cell extends JComponent{

        private class ClickListener implements MouseListener{

            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        }

        int x;
        int y;
        int lw;
        int lh;
        TextArea display;

        private int value = 0;

        public void refresh(){
            value = game.field.field[x][y].getValue();
        }

        public void newValue(){
            value = 2;
            drawValue();
        }

        public void drawValue(){
            System.out.println("drawn");
            display = new TextArea();
            display.setBounds(x, y, lw, lh);
            display.setText(value + "");
            add(display);
        }

        public void setValue(int value) {
            this.value = value;
            drawValue();
            repaint();
        }

        //xx1

        public Cell(int x, int y, int lineWidth, int lineHeight){
            System.out.println("crated cell");
            setSize(lineWidth,  lineHeight);
            lw = lineWidth;
            lh = lineHeight;
            setVisible(true);
            this.y = y;
            this.x = x;
            this.addMouseListener(new ClickListener());
        }
    }

    private int fieldSize;
    private final int maxCellSize = 300;
    private Line[] verticalLines;
    private Line[] horizontalLines;
    AbstractPlayer2048 player;
    private Cell[][] field;
    private Game2048 game;
    private Random random;

    public GUI_2048(int fieldSize){
        setPreferredSize(new Dimension(20*fieldSize + 100, 20*fieldSize + 100));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        this.fieldSize = fieldSize;
        setTitle("2048");
        setSize(maxCellSize + 120, maxCellSize + 128);
        this.fieldSize = fieldSize;
        verticalLines = new Line[fieldSize + 1];
        horizontalLines =  new Line[fieldSize + 1];
        field = new Cell[fieldSize][fieldSize];
        setVisible(true);
        player = new RealPlayer();
        game = new Game2048(player);
        GameThread gameThread = new GameThread();
        Thread thread = new Thread(gameThread);
        thread.start();
        RefreshThread refreshThread = new RefreshThread();
        Thread rethread = new Thread(refreshThread);
        rethread.start();
        initGUI();
    }

    private void refreshCells(){
        for (int h = 0; h < field.length; h++){
            for (int i = 0; i < field.length; i++){
                field[h][i].refresh();
            }
        }
        System.out.println("refresh crated");
    }

    private void generaterandom(){
        field[random.nextInt(field.length)][random.nextInt(field.length)].setValue(2);
    }

    private void doCells(){
        for (int h = 0; h < field.length; h++){
            for (int g = 0; g < field.length; g++){
                field[h][g] = new Cell(h, g,maxCellSize / fieldSize, maxCellSize / fieldSize);
                field[h][g].setBounds(verticalLines[h].getX(),
                        horizontalLines[g].getY(),
                        field[h][g].getWidth(),
                        field[h][g].getHeight());
                add(field[h][g]);
                field[h][g].setValue(game.field.field[h][g].getValue());
            }
        }
    }

    public void afterMoveTo(){
        refreshCells();
        generaterandom();
    }

    private void doLines(){
        for (int h = 0; h < verticalLines.length; h++){
            verticalLines[h] = new Line(1, fieldSize);
            verticalLines[h].setBounds(50 + h * (maxCellSize / fieldSize), 50, 1, maxCellSize);
            add(verticalLines[h]);
        }
        for (int h = 0; h < horizontalLines.length; h++){
            horizontalLines[h] = new Line(fieldSize, 1);
            horizontalLines[h].setBounds(50, 50 + h * (maxCellSize / fieldSize), maxCellSize, 1);
            add(horizontalLines[h]);
        }
    }

    private void initGUI(){
        doLines();
        doCells();
    }
}
