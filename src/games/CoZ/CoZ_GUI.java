package games.CoZ;

import misc.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    private class Cell extends JComponent{

        private class ClickListener implements MouseListener{

            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                if (game.getField().cellIsEmpty(y, x)) {
                    if (game.getState() == CoZ_States.CROSSES_MOVE) {
                        if(crosses instanceof GUIPlayer){
                            GUIPlayer crossesPlayer = (GUIPlayer) crosses;
                            crossesPlayer.x = x;
                            crossesPlayer.y = y;
                            crossesPlayer.isReady = true;
                        }
                    }
                    else if (game.getState() == CoZ_States.ZEROS_MOVE) {
                        if(zeros instanceof GUIPlayer){
                            GUIPlayer zerosPlayer = (GUIPlayer) zeros;
                            zerosPlayer.x = x;
                            zerosPlayer.y = y;
                            zerosPlayer.isReady = true;
                        }
                    }
                    else {
                        throw new IllegalArgumentException("Как так-то?");
                    }
                }
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

        private CoZ_Node what = CoZ_Node.NOTHING;

        public void setWhat(CoZ_Node what) {
            this.what = what;
            repaint();
        }

        public void paintComponent(Graphics g){
            if (what == CoZ_Node.ZERO){
                paintZero(g);
            }
            else if (what == CoZ_Node.CROSS){
                paintCross(g);
            }
        }

        public void paintZero(Graphics g){

            double ext = 0.9;
            double inn = 0.6;

            double startExt = (1 - ext) / 2;
            double startInn = (1 - inn) / 2;


            g.fillOval((int) (startExt * getWidth()),
                    (int) (startExt * getHeight()),
                    (int) (getWidth() * ext),
                    (int) (getHeight() * ext));

            g.setColor(new Color(238, 238, 238));
            g.fillOval((int) (startInn * getWidth()),
                    (int) (startInn * getHeight()),
                    (int) (getWidth() * inn),
                    (int) (getHeight() * inn));
        }

        public void paintCross(Graphics g){
            //g.fillOval(0, 0, this.getWidth(), this.getHeight());
            //g.fillRect(2, 2, getWidth()-3, getHeight()-3);


            // Коэффициент k - на сколько должны быть шировкие линии у крестика
            // маленикий k - тонкие линии
            // большой k - толстые линии
            double k = 0.13;

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

        public Cell(int x, int y, int lineWidth, int lineHeight){
            setSize(lineWidth,  lineHeight);
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
    AbstractPlayer crosses;
    AbstractPlayer zeros = new GUIPlayer();
    private Cell[][] field;
    private Game game;

    public CoZ_GUI(int fieldSize){
        setPreferredSize(new Dimension(20*fieldSize + 100, 20*fieldSize + 100));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        this.fieldSize = fieldSize;
        setTitle("games/CoZ");
        setSize(maxCellSize + 120, maxCellSize + 128);
        this.fieldSize = fieldSize;
        verticalLines = new Line[fieldSize + 1];
        horizontalLines =  new Line[fieldSize + 1];
        field = new Cell[fieldSize][fieldSize];
        initGUI();
        setVisible(true);
        // todo тут надо иф
        crosses = new GUIPlayer();
        zeros = new ComputerPlayer( FileHandler.loadObject("Neurals/Нейросеть_от_бога"));
        game = new Game(crosses, zeros, this);
        GameThread gameThread = new GameThread();
        Thread thread = new Thread(gameThread);
        thread.start();
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

    public void setSomething(int y, int x, CoZ_Node what){
        System.out.println("setsomething" + x + " " + y + " " + field[x][y].getBounds());
        field[x][y].setWhat(what);
        repaint();
    }

    private void initGUI(){
        doLines();
        doCells();
    }
}
