package graphics;

import games.CoZ.CoZ_GUI;
import games.Project2048.GUI_2048;
import misc.FileHandler;
import misc.Games;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class BaseGUI extends JFrame{

    private JTextField fieldSize;
    private JTextField winnum;
    private JLabel highscoreDisplay;
    private int highscore;
    private int win;
    private int fsz;
    private JButton jbutton;
    private JTextField jtextarea;
    private JLabel jlabel;
    private Games gtype;
    protected JComboBox<String> gamemode;
    String[] values;

    public BaseGUI(String[] values, Games gameType) throws NumberFormatException{
        super();
        this.values = values;
        gtype = gameType;
        highscore = Integer.parseInt(FileHandler.load("C:/Users/roman/IdeaProjects/Main/2048/highscore.txt"));
        fsz = Integer.parseInt(FileHandler.load("C:/Users/roman/IdeaProjects/Main/2048/fieldSize.txt"));
        win = Integer.parseInt(FileHandler.load("C:/Users/roman/IdeaProjects/Main/2048/winnum.txt"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Запуск игры");
        setSize(300, 150);
        initGUI();
        winnum.setText(win + "");
        fieldSize.setText(fsz + "");
        highscoreDisplay.setText(highscore + "");
        setVisible(true);
        startGame();
    }

    private void initGUI(){
        createJButton();
        createJTextField();
        createJComboBox();
        createWinnumInput();
        createFieldSizeInput();
        createHighScoreLabel();
    }

    private void createJButton(){
        jbutton = new JButton();
        jbutton.setBounds(21, 75, 250, 25);
        jbutton.setText("НАЧАТЬ ИГРУ");
        jbutton.addActionListener(ee -> {
            win = Integer.parseInt(winnum.getText());
            fsz = Integer.parseInt(winnum.getText());
            FileHandler.save("C:/Users/roman/IdeaProjects/Main/2048/winnum.txt", win + "");
            FileHandler.save("C:/Users/roman/IdeaProjects/Main/2048/highscore.txt", highscore + "");
            FileHandler.save("C:/Users/roman/IdeaProjects/Main/2048/fieldSize.txt", fsz + "");
            System.out.println("action crated");
            startGame();
            setVisible(false);
            setEnabled(false);
            new GUI_2048(fsz, win);
        });
        add(jbutton);
    }

    private void startGame(){
        if (gtype == Games.COZ){
            CoZ_GUI game = new CoZ_GUI(3);
        }
        else if (gtype == Games.G2048){
            openHidden2048Context();
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
        gamemode.addActionListener(ee -> openHidden2048Context());
        for (int h = 0; h < values.length; h++){
            gamemode.addItem(values[h]);
        }
        add(gamemode);
    }

    private void hideHidden2048Context(){
        if (gtype != Games.G2048){
            this.setSize(300, 150);
            winnum.setVisible(false);
            fieldSize.setVisible(false);
        }
    }

    private void openHidden2048Context() {
        if (gtype == Games.G2048){
            this.setSize(300, 210);
            if (winnum != null) {
                winnum.setVisible(true);
            }
            if (fieldSize != null) {
                fieldSize.setVisible(true);
            }
            if (highscoreDisplay != null) {
                highscoreDisplay.setVisible(true);
            }
        }
        else {
            hideHidden2048Context();
        }
    }

    private void createWinnumInput(){
        winnum = new JTextField();
        winnum.setBounds(10, 110, 130, 25);
        winnum.setText("Game goal");
        winnum.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        add(winnum);
        winnum.setVisible(false);
    }

    private void createFieldSizeInput(){
        fieldSize = new JTextField();
        fieldSize.setBounds(150, 110, 130, 25);
        fieldSize.setText("Field size");
        fieldSize.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        add(fieldSize);
        fieldSize.setVisible(false);
    }

    private void createHighScoreLabel(){
        highscoreDisplay = new JLabel();
        highscoreDisplay.setBounds(10, 140, 280, 25);
        highscoreDisplay.setText("Your highscore: " + highscore + ".");
        highscoreDisplay.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        add(highscoreDisplay);
        highscoreDisplay.setVisible(false);
    }
}
