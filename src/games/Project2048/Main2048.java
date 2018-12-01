package games.Project2048;

import graphics.BaseGUI;
import misc.Games;

import java.io.IOException;

public class Main2048 {

    public static void Main2048(String[] args) throws IOException {
        BaseGUI gui = new BaseGUI(new String[]{"2048"}, Games.G2048);
    }
}