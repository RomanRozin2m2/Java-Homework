package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class BaseGUI extends JFrame{
    public class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent ee){
            startGame();
        }
    }

    private JButton jbutton;
    private JTextField jtextarea;
    private JLabel jlabel;
    protected JComboBox<String> gamemode;
    String[] values;

    public BaseGUI(String[] values){
        super();
        this.values = values;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Запуск игры");
        setSize(300, 150);
        initGUI();
        setVisible(true);
    }

    private void initGUI(){
        createJButton();
        createJTextField();
        createJComboBox();
    }

    private void createJButton(){
        jbutton = new JButton();
        jbutton.setBounds(21, 75, 250, 25);
        jbutton.setText("НАЧАТЬ ИГРУ");
        jbutton.addActionListener(new ButtonListener());
        add(jbutton);
    }

    protected abstract void startGame();

    private void createJTextField(){
        jtextarea = new JTextField();
        jtextarea.setBounds(21, 27, 125, 25);
        jtextarea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        add(jtextarea);
    }

    private void createJComboBox(){
        gamemode = new JComboBox<String>();
        gamemode.setBounds(156, 27, 115, 25);
        for (int h = 0; h < values.length; h++){
            gamemode.addItem(values[h]);
        }
        add(gamemode);
    }
}
