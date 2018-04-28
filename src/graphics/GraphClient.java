package graphics;

import network.ClientListener;
import network.MessageClient;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphClient extends JFrame{

    public class BListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("AUTH")){
                authorize(login.getText(), passKeeper);
            }
            else if(e.getActionCommand().equals("REGISTER")){
                register(login.getText(), passKeeper);
            }
            else if(e.getActionCommand().equals("ERROR")){
                errframe.setVisible(false);
            }
        }
    }

    public class KListener implements KeyListener{

        public void keyTyped(KeyEvent ke){

        }

        public void keyPressed(KeyEvent ke){
            if (ke.getKeyCode() == 8){
                passKeeper = "";
                password.setText("");
            }
            else if (ke.getKeyCode() >= 32){
                char len = ke.getKeyChar();
                passKeeper += len;
            }
        }

        public void keyReleased(KeyEvent ke){
            if (ke.getKeyCode() == 10) {
                if(!listener.sendToServer("msg", console.getText())){
                    sendAlert();
                }
                console.setText("");
            }
            else {
                String st = "";
                for (int foest = 0; foest < passKeeper.length(); foest++){
                    st += "*";
                }
                password.setText(st);
            }
        }
    }

    private JTextArea jtextarea;
    private JTextField console;
    private JTextArea emgConsole;
    private ClientListener listener;
    private JScrollPane pane;
    private JTextField login;
    private JButton reg;
    private JPopupMenu popup;
    private String passKeeper = "";
    private JButton OK;
    private JButton log;
    private JTextField password;
    private JLabel errdisp;
    private JFrame errframe;

    public GraphClient(){
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("ZEDmsg");
        setLocationRelativeTo(null);
        setSize(240, 170);
        initGUI();
        this.getContentPane().add(pane);
        setVisible(true);
        MessageClient msg = new MessageClient();
        listener = msg.getListener();
        if(listener == null){
            JSONObject jsn = new JSONObject();
            jsn.put("emg", "Отсутствует подключение к серверу.");
            receiveMessage(jsn.toString());
        }
        else {
            listener.setGraphClient(this);
        }
    }

    private void initGUI(){
        createJTextArea();
        createConsole();
        emgConsole();
        pane();
        setPassword();
        setLogin();
        clog();
        creg();
        errorInit();
    }

    public void receiveMessage(String msg){
        JSONObject decrypter = new JSONObject(msg);
        String type = decrypter.get("type").toString();
        String message = decrypter.get("message").toString();

        if(type.equals("msg")) {
            if (jtextarea.getText().equals("")) {
                jtextarea.setText(message);
            } else {
                jtextarea.setText(jtextarea.getText() + "\n" + message);
            }
            jtextarea.setCaretPosition(jtextarea.getDocument().getLength());
        }

        else if(type.equals("emg")){
            emgConsole.setText(message);
        }

        else if(type.equals("rem")){
            emgConsole.setText("");
        }

        else if(type.equals("ping")){
            if(!listener.sendPing()){
                sendAlert();
            }
        }

        else if(type.equals("err")){
            displayError(message);
        }

        else if(type.equals("cfg")){
            reConfigure();
        }
    }

    private void createJTextArea(){
        jtextarea = new JTextArea("",1, 150);
        jtextarea.setBounds(10, 45, 350, 250);
        jtextarea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        jtextarea.setTabSize(4);
        jtextarea.setLineWrap(true);
        jtextarea.setEditable(false);
        jtextarea.setVisible(false);
        add(jtextarea);
    }

    private void emgConsole(){
        emgConsole = new JTextArea("",1, 150);
        emgConsole.setBounds(10, 10, 350, 25);
        emgConsole.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        emgConsole.setEditable(false);
        emgConsole.setTabSize(4);
        emgConsole.setVisible(false);
        add(emgConsole);
    }

    private void errorInit(){
        errframe = new JFrame();
        errframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        errframe.setLayout(null);
        errframe.setTitle("Ошибка");
        errframe.setLocationRelativeTo(null);
        errframe.setSize(265, 120);
        setErrdisp();
        setOKbutton();
        errframe.add(errdisp);
        errframe.add(OK);
        setVisible(false);
    }

    private void setErrdisp(){
        errdisp = new JLabel();
        errdisp.setBounds(10,15, 250, 25);
    }

    private void setOKbutton(){
        OK = new JButton();
        OK.setBounds(10, 45, 230, 25);
        OK.setActionCommand("ERROR");
        OK.addActionListener(new BListener());
        OK.setText("Продолжить");
    }

    public void displayError(String errorMsg){
        errframe.setVisible(true);
        login.setText("");
        password.setText("");
        errdisp.setText(errorMsg);
    }

    public void sendAlert(){
        emgConsole.setText("Подключение прервано, перезапустите приложение.");
    }

    private void createConsole(){
        console = new JTextField(50);
        console.setBounds(10, 305, 350, 25);
        console.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        console.addKeyListener(new KListener());
        console.setVisible(false);
        add(console);
    }

    private void pane(){
        pane = new JScrollPane(jtextarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setBounds(10, 45, 350, 250);
        pane.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        pane.setVisible(false);
    }

    private void reConfigure(){
        setSize(390, 385);
        jtextarea.setVisible(true);
        console.setVisible(true);
        emgConsole.setVisible(true);
        pane.setVisible(true);
        password.setVisible(false);
        login.setVisible(false);
        reg.setVisible(false);
        log.setVisible(false);
    }

    private void setLogin(){
        login = new JTextField(25);
        login.setBounds(43, 10, 145, 25);
        login.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        add(login);
    }

    private void setPassword(){
        password = new JTextField(50);
        password.setBounds(43, 40, 145, 25);
        password.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        password.addKeyListener(new KListener());
        add(password);
    }

    private void clog(){
        log = new JButton();
        log.setBounds(30, 70, 165, 22);
        log.setText("Войти");
        log.setActionCommand("AUTH");
        log.addActionListener(new BListener());
        add(log);
    }

    private void creg(){
        reg = new JButton();
        reg.setBounds(30, 95, 165, 22);
        reg.setText("Зарегистрироваться");
        reg.setActionCommand("REGISTER");
        reg.addActionListener(new BListener());
        add(reg);
    }

    private void authorize(String login, String pass){
        listener.auth(login, pass);
    }

    private void register(String login, String pass){
        listener.register(login, pass);
    }

}
