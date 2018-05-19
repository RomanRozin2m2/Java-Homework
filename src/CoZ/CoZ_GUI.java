package CoZ;

import graphics.BaseGUI;
import misc.Games;

import javax.swing.*;
import java.awt.*;

public class CoZ_GUI extends JFrame{

    private class Line extends JComponent{

        public void paint(Graphics g){
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        public Line(int lineWidth, int lineHeight){
            setSize(lineWidth,  lineHeight);
            setVisible(true);
        }

    }

    private class GameThread implements Runnable {

        public void run() {
            game.startGame();
        }
    }

    private class Zero extends JComponent{
        public void paintComponent(Graphics g){
            g.fillOval(2, 2, getWidth()-5, getHeight()-5);
            g.setColor(new Color(238, 238, 238));
            g.fillOval(5, 5, getWidth()-11, getHeight()-11);
        }

        public Zero(int lineWidth, int lineHeight){
            setSize(lineWidth,  lineHeight);
            setVisible(true);
        }
    }

    private class Cross extends JComponent{
        public void paintComponent(Graphics g){
            //g.fillOval(0, 0, this.getWidth(), this.getHeight());
            //g.fillRect(2, 2, getWidth()-3, getHeight()-3);


            // Коэффициент k - на сколько должны быть шировкие линии у крестика
            // маленикий k - тонкие линии
            // большой k - толстые линии
            double k = 0.2;

            int startX = 2;
            int startY = 2;
            int endX = getWidth() - 3;
            int endY = getWidth() - 3;

            int width = endX - startX;
            int height = endY - startY;


            // Рисуем первый прямоугольник у крестика по этим 4-м точкам

            /*
            ____________________
            |    1              |
            |                   |
            |4                  |
            |                   |
            |                  2|
            |                   |
            |______________3____|
             */

            Polygon p = new Polygon();

            p.addPoint((int)(startX + width * k), startY);
            p.addPoint(endX, (int)(endY - height * k));
            p.addPoint((int)(endX - width * k), endY);
            p.addPoint(startX, (int)(startY + height * k));

            g.fillPolygon(p);


            // Рисуем второй пямоугольник у крестика по этим 4-м точкам

            /*
            ____________________
            |            1      |
            |                   |
            |                  2|
            |                   |
            |4                  |
            |                   |
            |_____3_____________|
             */

            p = new Polygon();

            p.addPoint((int)(endX - width * k), startY);
            p.addPoint(endX, (int)(startY + height * k));
            p.addPoint((int)(startX + width * k), endY);
            p.addPoint(startX, (int)(endY - height * k));

            g.fillPolygon(p);

        }

        public Cross(int lineWidth, int lineHeight){
            setSize(lineWidth,  lineHeight);
            setVisible(true);
        }
    }

    private int fieldSize;
    private final int maxCellSize = 300;
    private Line[] verticalLines;
    private Line[] horizontalLines;
    private Game game;

    public CoZ_GUI(int fieldSize){
        setPreferredSize(new Dimension(20*fieldSize + 100, 20*fieldSize + 100));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        this.fieldSize = fieldSize;
        setTitle("CoZ");
        setSize(maxCellSize + 120, maxCellSize + 128);
        this.fieldSize = fieldSize;
        verticalLines = new Line[fieldSize + 1];
        horizontalLines =  new Line[fieldSize + 1];
        initGUI();
        setVisible(true);
        game = new Game(new RealPlayer(), new RealPlayer(), this);
        GameThread gameThread = new GameThread();
        Thread thread = new Thread(gameThread);
        thread.start();
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

    public void setSomething(int y, int x, CoZ_Node what){
        System.out.println("setsomething");
        int trY = horizontalLines[y].getY();
        int trX = verticalLines[x].getX();
        if (what == CoZ_Node.CROSS){
            Cross cross = new Cross(20, 20);
            cross.setBounds(trX, trY, cross.getWidth(), cross.getHeight());
            add(cross);
        }
        else if (what == CoZ_Node.ZERO){
            Zero zero = new Zero(20, 20);
            zero.setBounds(trX, trY, zero.getWidth(), zero.getHeight());
            add(zero);
        }
        else {
            throw new IllegalArgumentException("Что ты творишь?");
        }
        repaint();
    }

    private void initGUI(){
        doLines();
    }
}
