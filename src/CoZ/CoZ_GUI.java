package CoZ;

import graphics.TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;

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
            g.fillOval(0, 0, this.getWidth(), this.getHeight());
        }

        public Zero(int lineWidth, int lineHeight){
            setSize(lineWidth,  lineHeight);
            setVisible(true);
        }

    }

    private class Cross extends JTextArea{

        public Cross(int lineWidth, int lineHeight){
            setSize(lineWidth,  lineHeight);
            setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
            setText("X");
            setVisible(true);
            setEditable(false);
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
            Cross cross = new Cross(10, 10);
            cross.setVisible(true);
            cross.setBounds(trX + 2, trY + 2, 15, 15);
            add(cross);
        }
        else if (what == CoZ_Node.ZERO){
            Zero zero = new Zero(10, 10);
            zero.setVisible(true);
            zero.setBounds(trX + 5, trY + 5, 10, 10);
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
