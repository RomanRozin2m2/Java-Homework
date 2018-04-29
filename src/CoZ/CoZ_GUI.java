package CoZ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CoZ_GUI extends JFrame{

    private class KListener implements KeyListener {

        public void keyTyped(KeyEvent ke){

        }

        public void keyPressed(KeyEvent ke){
            System.out.println(ke.getKeyChar() + " | " + ke.getKeyCode());
            if(ke.getKeyCode() == 10){

            }
        }

        public void keyReleased(KeyEvent ke){
            if(ke.getKeyCode() == 10) {

            }
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

    private class Zero extends JComponent{
        public void paint(Graphics g){
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
        public void paint(Graphics g){
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
    private JTextArea field;
    private Line[] verticalLines;
    private Line[] horizontalLines;

    public CoZ_GUI(int fieldSize){
        super();
        setPreferredSize(new Dimension(20*fieldSize + 100, 20*fieldSize + 100));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        this.fieldSize = fieldSize;
        setTitle("CoZ");
        setSize(20*fieldSize + 100, 20*fieldSize + 100);
        this.fieldSize = fieldSize;
        verticalLines = new Line[fieldSize + 1];
        horizontalLines =  new Line[fieldSize + 1];
        initGUI();
        setVisible(true);
    }

    private void doLines(){
        for (int h = 0; h < verticalLines.length; h++){
            verticalLines[h] = new Line(1, fieldSize);
            verticalLines[h].setBounds(50 + h * 20, 50, 1, fieldSize * 20);
            add(verticalLines[h]);
        }
        for (int h = 0; h < horizontalLines.length; h++){
            horizontalLines[h] = new Line(fieldSize, 1);
            horizontalLines[h].setBounds(50, 50 + h * 20, fieldSize * 20, 1);
            add(horizontalLines[h]);
        }
    }

    public void setSomething(int y, int x, CoZ_Node what){
        int trY = horizontalLines[y - 1].getY();
        int trX = verticalLines[x - 1].getX();
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
    }

    private void initGUI(){
        createField();
        doLines();
    }

    private void createField(){
        field = new JTextArea("", fieldSize, fieldSize);
        field.setBounds(50, 50, 20*fieldSize-17/2, 20*fieldSize+17/2);
        field.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
        for (int h = 0; h < fieldSize; h++){
            for (int i = 0; i < fieldSize; i++){
                field.setText(field.getText() + "* ");
            }
            field.setText(field.getText() + "\n");
        }
        field.setEditable(false);
        field.setVisible(false);
        field.setTabSize(4);
        field.setLineWrap(false);
        add(field);
    }
}
