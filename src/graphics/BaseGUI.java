package graphics;

import CoZ.CoZ_GUI;
import Project2048.GUI_2048;
import misc.Games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseGUI extends JFrame{
    public class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent ee){
            System.out.println("action crated");
            startGame();
            setVisible(false);
            setEnabled(false);
        }
    }

    private JButton jbutton;
    private JTextField jtextarea;
    private JLabel jlabel;
    private Games gtype;
    protected JComboBox<String> gamemode;
    String[] values;

    public BaseGUI(String[] values, Games gameType){
        super();
        this.values = values;
        gtype = gameType;
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

    private void startGame(){
        System.out.println("startgame crated.");
        if (gtype == Games.COZ){
            System.out.println("if crated.");
            CoZ_GUI game = new CoZ_GUI(3);
        }
        else if (gtype == Games.G2048){
            GUI_2048 gui = new GUI_2048(4);
        }
        else if (gtype == Games.TETRIS){
            //
        }
    }

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
