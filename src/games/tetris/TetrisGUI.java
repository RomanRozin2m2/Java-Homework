package games.tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisGUI extends JFrame{

    private class MCThread implements Runnable{

        public void run() {
            monitorControls();
        }
    }

    private JTextArea Gfield;
    final int fieldWidth;
    final int fieldHeight;
    Controls controls;
    Field field;

    public TetrisGUI(int fw, int fh, boolean cyc) {
        super();
        controls = new Controls();
        this.addKeyListener(controls);
        field = new Field(fh,fw,cyc);
        fieldHeight = fh*35;
        fieldWidth = fw*35;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("TETRIS");
        setSize(fieldWidth + 20, fieldHeight + 20);
        initGUI();
        setVisible(true);
        MCThread mcThread = new MCThread();
        Thread thread = new Thread(mcThread);
        thread.start();
    }

    private void initGUI(){
        createJTextArea();
    }

    public void monitorControls(){
        if (controls.getKeyPressed() != Keys.NOTHING){
            if (controls.getKeyPressed() == Keys.GO_LEFT){
                field.moveLeft();
            }
            else if (controls.getKeyPressed() == Keys.GO_RIGHT){
                field.moveRight();
            }
            else if (controls.getKeyPressed() == Keys.CLOCKWISE){

            }
            else if (controls.getKeyPressed() == Keys.ANTI_CLOCKWISE){

            }
            else if (controls.getKeyPressed() == Keys.DROP){
                field.drop();
            }
        }
    }

    private void createJTextArea(){
        Gfield = new JTextArea();
        Gfield.setBounds(10, 10, fieldWidth, fieldHeight);
        Gfield.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        Gfield.setTabSize(4);
        Gfield.setEditable(false);
        Gfield.setLineWrap(true);
        setField();
        add(Gfield);
    }

    public void setField(){
        Gfield.setText(field.printField());
    }
}
