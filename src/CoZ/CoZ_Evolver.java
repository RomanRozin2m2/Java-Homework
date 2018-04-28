package CoZ;

import data.Evolver;
import data.NeuralNetwork;
import misc.FileHandler;

public class CoZ_Evolver extends Evolver{
    private int rounds;
    private int generationsAlreadyDone = 0;

    public CoZ_Evolver(int generations, int exemplars, int roundNum, boolean fromFile) {
        super(9, 9, new int[]{9, 9}, generations, exemplars);
        rounds = roundNum;
        if(fromFile){
            loadFromFile();
        }
    }

    private void loadFromFile() {
        for (int h = 0; h < networks.length; h++){
            networks[h] = FileHandler.loadObject("LastGeneration/NN-" + h);
        }
        generationsAlreadyDone = FileHandler.loadObject("LastGeneration/GenerationsDone");
    }

    private void printPlaces(int generation){
        System.out.println("ПОКОЛЕНИЕ " + generation + ". (" + (generationsAlreadyDone + generation) +
                ")_____________________________________________________________________");
        for (int h = 0; h < 5; h++){
            System.out.println("Нейросеть " + networks[h].ID + ", результат " + networks[h].value + ".");
        }
    }

    private void saveNetworks(int ID, String path){
        FileHandler.saveObject(networks[ID], path);
    }

    private void saveNetworks(){
        FileHandler.saveObject(networks[0], "Neurals/BestNeural" + networks[0].ID);
    }

    public void evolve(){
        for (int h = 1; h <= generations; h++){
            oneEvolve();
            printPlaces(h);
            generateNewExemplars();
            if (h % 10 == 0){
                saveNetworks();
            }
        }
        for (int h = 0; h < networks.length; h++){
            saveNetworks(h, "LastGeneration/NN-" + h);
        }
        FileHandler.saveObject(generationsAlreadyDone + generations, "LastGeneration/GenerationsDone");
        Game vsHuman = new Game(new ComputerPlayer(networks[0]), new RealPlayer());
        vsHuman.startGame();
    }

    private void oneEvolve(){
        for (NeuralNetwork h : networks){
            h.value = 0;
        }
        for (int h = 0; h < rounds; h++){
            oneRound();
        }
    }

    private void oneRound(){
        ComputerPlayer[] players = new ComputerPlayer[networks.length];
        sortNetworks();
        for (int h = 0; h < players.length; h++){
            players[h] = new ComputerPlayer(networks[h]);
        }
        for (int h = 0; h < players.length - 1; h += 2){
            Game game = new Game(players[h], players[h + 1]);
            CoZ_Winners winner = game.startGame();
            if (winner == CoZ_Winners.CROSSES){
                players[h].net.value += 2;
            }
            else if (winner == CoZ_Winners.DRAW){
                players[h].net.value += 1;
                players[h + 1].net.value += 1;
            }
            else {
                players[h + 1].net.value += 2;
            }
            Game game2 = new Game(players[h + 1], players[h]);
            CoZ_Winners winner2 = game2.startGame();
            if (winner2 == CoZ_Winners.CROSSES){
                players[h + 1].net.value += 2;
            }
            else if (winner2 == CoZ_Winners.DRAW){
                players[h + 1].net.value += 1;
                players[h].net.value += 1;
            }
            else {
                players[h].net.value += 2;
            }
        }
    }
}
