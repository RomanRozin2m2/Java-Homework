package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TestGUI extends JFrame{

    public class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent ee){
            if(ee.getActionCommand().equals("STATE_1")) {
                jbutton.setText("НЕ ТРОЖ");
                jbutton.setActionCommand("НЕ ТРОЖ");
            }
            else if(ee.getActionCommand().equals("НЕ ТРОЖ")){
                jbutton.setText("Велокнопка");
                jbutton.setActionCommand("STATE_1");
            }
        }
    }

    public class KListener implements KeyListener{

        public void keyTyped(KeyEvent ke){

        }

        public void keyPressed(KeyEvent ke){
            System.out.println(ke.getKeyChar() + " | " + ke.getKeyCode());
            if(ke.getKeyCode() == 10){

                jtextarea.setText(jtextarea.getText() + "\n" + console.getText());

            }
        }

        public void keyReleased(KeyEvent ke){
            if(ke.getKeyCode() == 10) {
                console.setText("");
            }
        }
    }

    private JButton jbutton;
    private JTextArea jtextarea;
    private JLabel jlabel;
    private JTextField console;

    public TestGUI(){
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Великое окошко фараона");
        setSize(650, 450);
        setMinimumSize(new Dimension(650, 450));
        setMaximumSize(new Dimension(660, 360));
        initGUI();
        setVisible(true);
    }

    private void initGUI(){
        createJButton();
        createJTextArea();
        createJLabel();
        createConsole();
    }

    private void createJButton(){
        jbutton = new JButton();
        jbutton.setBounds(10, 10, 150, 50);
        jbutton.setText("Не менее великая кнопка фараона");
        jbutton.setActionCommand("STATE_1");
        jbutton.addActionListener(new ButtonListener());
        add(jbutton);
    }

    private void createJTextArea(){
        jtextarea = new JTextArea("Великий текстбокс фараона",200, 150);
        jtextarea.setBounds(250, 10, 350, 250);
        jtextarea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        jtextarea.setTabSize(4);
        jtextarea.setLineWrap(true);
        add(jtextarea);
    }

    private void createConsole(){
        console = new JTextField(50);
        console.setBounds(250, 270, 350, 25);
        console.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        console.addKeyListener(new KListener());
        add(console);
    }

    private void createJLabel(){
        jlabel = new JLabel();
        jlabel.setBounds(10, 70, 150, 50);
        jlabel.setText("У меня закончилась фантазия");
        add(jlabel);
    }
}
