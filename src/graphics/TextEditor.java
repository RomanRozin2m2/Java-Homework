package graphics;

import data.TextBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class TextEditor extends JFrame {

    private class MListener implements MouseListener{

        public void mouseClicked(MouseEvent e) {
            int[] calc_ed = calculate(e.getX(), e.getY());
            setCursor(calc_ed[0], calc_ed[1],true);
        }

        public void mouseEntered(MouseEvent e) {
            requestFocus();
        }

        public void mouseExited(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }
    }

    public class MWListener implements MouseWheelListener{

        public void mouseWheelMoved(MouseWheelEvent ee) {
            if(ee.getWheelRotation() < 0){
                moveUp();
            }
            else{
                moveDown();
            }
        }
    }

    public class SaveButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent ee){
            saveFile(filePath.getText(), txt.pureToString());
            requestFocus();
        }
    }

    public class LoadButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent ee){
            String text = loadFile(filePath.getText());
            txt = new TextBox(text);
            for (int i = 0; i < lines; i++){
                setLabelText(txt.getValue(i), i, i);
            }
            linesScrolled = 0;
            requestFocus();
        }
    }

    private class KListener implements KeyListener{
        boolean ShiftPressed;
        boolean CTRLPressed;

        public void keyPressed(KeyEvent e) {

            System.out.println(e.getKeyCode());

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                createNewLine();
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                moveUp();
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                moveDown();
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                moveRight();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moveLeft();
            }
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                deleteCurSymbol();
            }

            if (e.getKeyCode() == KeyEvent.VK_SHIFT){
                ShiftPressed = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_CONTROL){
                System.out.println("CTRL pressed");
                CTRLPressed = true;
            }

            if (e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z'){
                if(ShiftPressed){
                    addSymbol(Character.toString(e.getKeyChar()));
                }
                else if(CTRLPressed){
                    if(e.getKeyCode() == KeyEvent.VK_O){
                        String text = loadFile(filePath.getText());
                        txt = new TextBox(text);
                        for (int i = 0; i < lines; i++){
                            setLabelText(txt.getValue(i), i, i);
                        }
                        linesScrolled = 0;
                    }
                    if(e.getKeyCode() == KeyEvent.VK_S){
                        saveFile(filePath.getText(), txt.pureToString());
                    }
                }
                else{
                    addSymbol(Character.toString(e.getKeyChar()));
                }

            }

            if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9'){
                addSymbol(Character.toString(e.getKeyChar()));
            }

            if (e.getKeyChar() == ' '){
                addSymbol(" ");
            }
        }


        public void keyTyped(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT){
                ShiftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_CONTROL){
                CTRLPressed = false;
            }
        }
    }

    private class Cursor extends JComponent{

        private final int timerdelay = 500;
        private Timer timer;

        class RepaintAction implements ActionListener{
            public void actionPerformed(ActionEvent ev) {
                c.setVisible(!c.isVisible());
                c.repaint();
            }
        }

        public Cursor(){
            setSize(1, lineSize - 6);
            setVisible(true);
            timer = new Timer(timerdelay, new RepaintAction());
            timer.start();
        }

        public void paint(Graphics g){
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        public void ResetTimer(){
            timer.stop();
            c.setVisible(true);
            timer = new Timer(timerdelay, new RepaintAction());
            timer.start();
        }
    }

    private final int lines = 26;
    private final int differenceBetweenLines = 0;
    private final int xOffset = 10;
    private final int yOffset = 50;
    private int linesScrolled = 0;
    private JButton loadButton;
    private Cursor c;
    private final int oneSymbolLength = 8;
    private final int lineSize = 25;
    private JLabel[] labels = new JLabel[lines];
    private int Bound = 7 * oneSymbolLength;
    private int curY = 0;
    private int curX = 0;
    private TextBox txt;
    private JTextField filePath;

    public TextEditor(){
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Текстовый редактор");
        addKeyListener(new KListener());
        addMouseListener(new MListener());
        addMouseWheelListener(new MWListener());
        initGUI();
        setSize(450, (lineSize + differenceBetweenLines) * (lines + 1) + yOffset);
        setVisible(true);
        setFocus();
    }

    private int[] calculate (int x, int y){
        int[] result = new int[2];
        int generatedX = (x - (Bound + xOffset)) / oneSymbolLength ;
        int generatedY = (y + 5 - yOffset) / (differenceBetweenLines + lineSize);
        result[0] = generatedX;
        result[1] = generatedY - 1;
        return result;
    }

    private void initGUI(){
        createLabels();
        createCursor();
        createLoadButton();
        createSaveButton();
        createFileName();
    }

    private void createLabels(){
        for(int i = 0; i < lines; i++){
            labels[i] = new JLabel();
            labels[i].setBounds(xOffset, (lineSize + differenceBetweenLines) * i + yOffset, 3000, lineSize);
            labels[i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            labels[i].setText(getStrLineNumber(i + 10));
            add(labels[i]);
        }
    }

    private String getStrLineNumber(int num){
        if(num < 10 && num > 0){
            return "[" + num + "]" + "    ";
        }
        if(num < 100 && num >= 10){
            return "[" + num + "]" + "   ";
        }
        if(num < 1000 && num >= 100){
            return "[" + num + "]" + "  ";
        }
        if(num < 10000 && num >= 1000){
            return "[" + num + "]" + " ";
        }
        return "[" + 9999 + "]" + " ";
    }

    private void deleteCurSymbol(){
        String tekst = labels[curY].getText();
        String genText = "";

        if(curX > 0) {
            for (int i = 0; i < tekst.length(); i++) {
                if (i != curX + 6) {
                    genText += tekst.charAt(i);
                }
            }
            labels[curY].setText(genText);
            moveLeft();
        }
    }

    private void addSymbol(String ch){

        String texst = labels[curY].getText();
        String genText = "";

        for (int i = 0; i < texst.length(); i++) {
            if (i != curX + 6) {
                genText += texst.charAt(i);
            }
            else {
                genText += texst.charAt(i) + ch;
            }
        }
        labels[curY].setText(genText);
        txt.changeValue(curY + linesScrolled, genText.substring(7, genText.length()));
        moveRight();
    }

    private void moveUp(){
        curY--;
        setCursor(curX, curY, true);
    }

    private void moveDown(){
        curY++;
        setCursor(curX, curY, true);
    }

    private void moveLeft(){
        curX--;
        setCursor(curX, curY, false);
    }

    private void moveRight(){
        curX++;
        setCursor(curX, curY, false);
    }

    private void setLabelText(String text, int index, int num){
        labels[index].setText(getStrLineNumber(num + 1) + text);
    }

    private void setFocus(){
        while(!hasFocus()){
            requestFocus();
        }
    }

    private void createNewLine(){
        txt.addToMiddle(curY + differenceBetweenLines, curX);
        setCursor(0, curY + 1, false);
        for(int i = 0; i < lines; i++){
            setLabelText(txt.getValue(linesScrolled + i), i, linesScrolled + i);
        }
    }

    private void setCursor(int x, int y, boolean isMouse){
        int generatedX = x * oneSymbolLength + Bound + xOffset;
        int generatedY = y * (differenceBetweenLines + lineSize) + yOffset;

        if(x >= 0 && y >= 0 && y < lines){
            curX = x;
            curY = y;
            if(labels[curY].getText().length() - 7 < curX){
                if(isMouse){
                    curX = labels[curY].getText().length() - 7;
                    generatedX = curX * oneSymbolLength + Bound + xOffset;
                }
                else{
                    if(txt.getValue(curY + linesScrolled + 1) != null){
                        curX = 0;
                        if(curY < lines - 1){
                            curY++;
                        }
                        else{
                            scrollDown();
                        }
                        generatedX = curX * oneSymbolLength + Bound + xOffset;
                        generatedY = curY * (differenceBetweenLines + lineSize) + yOffset;
                    }
                    else{
                        curX--;
                    }
                }
            }
            c.setBounds(generatedX, generatedY, c.getWidth(), c.getHeight());
            c.ResetTimer();
        }
        else{
            if(x < 0 && (curY + linesScrolled > 0)) {
                if(curY == 0){
                    curX = txt.getValue(curY + linesScrolled - 1).length();
                    scrollUp();
                }
                else{
                    curY--;
                    curX = txt.getValue(curY + linesScrolled).length();
                }
                generatedX = curX * oneSymbolLength + Bound + xOffset;
                generatedY = curY * (differenceBetweenLines + lineSize) + yOffset;
                c.setBounds(generatedX, generatedY, c.getWidth(), c.getHeight());
                c.ResetTimer();
            }
            else{
                curX = 0;
            }
            if(y >= lines){
                curY = lines - 1;
                scrollDown();
            }
            if(y < 0){
                curY = 0;
                scrollUp();
            }
        }
    }

    private void createCursor(){
        c = new Cursor();
        c.setVisible(true);
        c.setBounds(xOffset + Bound, 2 + yOffset, c.getWidth(), c.getHeight());
        add(c);
    }

    private void scrollDown(){
        if(txt.getValue(linesScrolled + lines) != null){
            linesScrolled++;
            for(int i = 0; i < lines; i++){
                setLabelText(txt.getValue(linesScrolled + i), i, linesScrolled + i);
            }
        }
    }

    private void scrollUp(){
        if(linesScrolled > 0){
            linesScrolled--;
            for(int i = 0; i < lines; i++){
                setLabelText(txt.getValue(linesScrolled + i), i, linesScrolled + i);
            }
        }
    }

    private String loadFile(String path){
        StringBuilder ContentBuilder = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine = br.readLine();
            while(sCurrentLine != null){
                ContentBuilder.append(sCurrentLine).append("\n");
                sCurrentLine = br.readLine();
            }
        }
        catch (IOException ex){
            System.out.println("Ошибка загрузки файла: " + ex.getMessage());
        }
        return ContentBuilder.toString();
    }

    private void saveFile(String path, String text){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(text);
            bw.close();
        }
        catch (IOException ex){
            System.out.println("Ошибка сохранения файла: " + ex.getMessage());
        }
    }

    private void createLoadButton(){
        loadButton = new JButton();
        loadButton.setBounds(xOffset, 15, 100, 25);
        loadButton.setText("Загрузить");
        loadButton.addActionListener(new LoadButtonListener());
        add(loadButton);
    }

    private void createFileName(){
        filePath = new JTextField();
        filePath.setBounds(xOffset + 100 + 10, 15, 100, 25);
        filePath.setText("Имя файла");
        add(filePath);
    }

    private void createSaveButton(){
        loadButton = new JButton();
        loadButton.setBounds(xOffset + 200 + 20, 15, 100, 25);
        loadButton.setText("Сохранить");
        loadButton.addActionListener(new SaveButtonListener());
        add(loadButton);
    }

}
