package games.Project2048;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;
import java.awt.*;

public class GUI_2048 extends JFrame{

    private class Listener implements KeyListener{

        public void keyTyped(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == 38) {
                ((RealPlayer)(game.player2048)).setDirection(Direction.UP);
            }
            else if (e.getKeyCode() == 40) {
                ((RealPlayer)(game.player2048)).setDirection(Direction.DOWN);
            }
            else if (e.getKeyCode() == 37) {
                ((RealPlayer)(game.player2048)).setDirection(Direction.LEFT);
            }
            else if (e.getKeyCode() == 39) {
                ((RealPlayer)(game.player2048)).setDirection(Direction.RIGHT);
            }
        }

        public void keyReleased(KeyEvent e) {

        }
    }

    private class Line extends JComponent{

        public void paint(Graphics g){
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        public Line(int lineWidth, int lineHeight){
            setSize(lineWidth,  lineHeight);
            setVisible(true);
        }

    }

    private class Rectangle extends JComponent{

        Color color;

        public void paint(Graphics g){
            Color old = g.getColor();
            g.setColor(color);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(old);
        }

        public Rectangle(int width, int height){
            color = new Color(0xFFFE00);
            setSize(width, height);
            setVisible(true);
        }

        public void setColor(Color c){
            color = c;
        }

    }

    private class GameThread implements Runnable {

        public void run() {
            game.startGame();
        }
    }

    private class Cell extends JComponent{

        int x;
        int y;
        int lw;
        int lh;
        JLabel display;
        Rectangle background;

        private int value = 0;

        public void refresh(){
            value = game.field.field[y][x].getValue();
            display.setText(value + "");
            if (value <= 64){
                display.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 50*4*maxCellSize/fieldSize/300));
            }
            else if (value <= 512){
                display.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 30*4*maxCellSize/fieldSize/300));
            }
            else if (value <= 8192){
                display.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 18*4*maxCellSize/fieldSize/300));
            }
            else {
                display.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 14*4*maxCellSize/fieldSize/300));
            }

            int color = value == 0? 255: 255 - (int) (Math.log(value) / Math.log(2) / 12 * 255);
            background.setColor(new Color(255, 255, color));

        }

        public void drawValue(){
            display = new JLabel();
            display.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 50*4*maxCellSize/fieldSize/300));
            display.setBounds(x, y, lw, lh);
            display.setText(value + "");
            add(display);
            background.setBounds(x, y,60,60);

            int color;
            if (value == 0){
                color = 255;
            }
            else {
                double log2 = Math.log(value) / Math.log(2);
                color = 255 - (int) (log2 / 12 * 255);
                System.out.println(value + " " + log2 + " " + color);
            }

            background.setColor(new Color(255, 255, color));
            add(background);
        }

        public void setValue(int value) {
            this.value = value;
            drawValue();
            repaint();
        }

        //xx1

        public Cell(int x, int y, int lineWidth, int lineHeight){
            setSize(lineWidth,  lineHeight);
            lw = lineWidth;
            lh = lineHeight;
            setVisible(true);
            this.y = y;
            this.x = x;
            background = new Rectangle(10,10);
        }
    }

    private int fieldSize;
    private final int maxCellSize = 600;
    private Line[] verticalLines;
    private Line[] horizontalLines;
    AbstractPlayer2048 player;
    private int winnum;
    private Cell[][] field;
    private Game2048 game;
    private Random random;
    private Listener klistener;

    public GUI_2048(int fieldSize, int winnum){
        setPreferredSize(new Dimension(20*fieldSize + 100, 20*fieldSize + 100));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        this.fieldSize = fieldSize;
        setTitle("2048");
        setSize(maxCellSize + 120, maxCellSize + 128);
        this.fieldSize = fieldSize;
        verticalLines = new Line[fieldSize + 1];
        horizontalLines =  new Line[fieldSize + 1];
        klistener = new Listener();
        field = new Cell[fieldSize][fieldSize];
        this.winnum = winnum;
        setVisible(true);
        player = new RealPlayer();
        game = new Game2048(player, this, winnum);
        this.addKeyListener(klistener);
        GameThread gameThread = new GameThread();
        Thread thread = new Thread(gameThread);
        thread.start();
        initGUI();
    }

    public int getFieldSize(){
        return fieldSize;
    }

    private void arrowInput(){

    }

    public void refreshCells(){
        for (int h = 0; h < field.length; h++){
            for (int i = 0; i < field.length; i++){
                if (field[h][i] != null){
                    field[h][i].refresh();
                }
            }
        }
    }

    private void doCells(){
        for (int h = 0; h < field.length; h++){
            for (int g = 0; g < field.length; g++){
                field[h][g] = new Cell(h, g,maxCellSize / fieldSize, maxCellSize / fieldSize);
                field[h][g].setBounds(verticalLines[h].getX() - h,
                        horizontalLines[g].getY() - g,
                        field[h][g].getWidth() + h,
                        field[h][g].getHeight() + g);
                add(field[h][g]);
                field[h][g].setValue(game.field.field[h][g].getValue());
            }
        }
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
