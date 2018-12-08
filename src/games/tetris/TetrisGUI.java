package games.tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisGUI extends JFrame{

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
        MCThread mcThread = new MCThread(field, controls);
    }

    private void initGUI(){
        createJTextArea();
    }

    private void createJTextArea(){
        Gfield = new JTextArea();
        Gfield.setBounds(10, 10, fieldWidth, fieldHeight);
        Gfield.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        Gfield.setTabSize(4);
        Gfield.setEditable(false);
        Gfield.setLineWrap(true);
        Gfield.addKeyListener(controls);
        setField();
        add(Gfield);
    }

    public void setField(){
        Gfield.setText(field.printField());
    }
}
