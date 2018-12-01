package games.CoZ;

import data.NeuralNetwork;

import java.util.Arrays;

public class ComputerPlayer implements AbstractPlayer {
    NeuralNetwork net;

    public void endGame(Field field) {
        //do nothing();
    }

    public ComputerPlayer(){
        int[] layers = new int[2];
        layers[0] = 9;
        layers[1] = 9;
        net = new NeuralNetwork(9, 9, layers);
    }

    public ComputerPlayer(NeuralNetwork nn){
        net = nn;
    }

    public void decide(Field field, CoZ_Node side) {
        double[] inputs = new double[9];
        for (int h = 0; h < 3; h++){
            for (int i = 0; i < 3; i++){
                if (field.field[h][i] == side){
                    inputs[3 * h + i] = 1;
                }
                else if (field.field[h][i] == CoZ_Node.NOTHING){
                    inputs[3 * h + i] = 0;
                }
                else {
                    inputs[3 * h + i] = -1;
                }
            }
        }
        double[] outputs = net.calculate(inputs);
        double[] sortedOutputs = new double[9];
        for (int h = 0; h < 9; h++){
            sortedOutputs[h] = outputs[h];
        }
        Arrays.sort(sortedOutputs);
        for (int h = 8; h >= 0; h--){
            double biggest = sortedOutputs[h];
            for (int i = 0; i < 9; i++){
                if (outputs[i] == biggest){
                    if (field.cellIsEmpty(i%3, i/3)){
                        if (side == CoZ_Node.CROSS) {
                            field.setCross(i%3, i/3);
                        } else if (side == CoZ_Node.ZERO) {
                            field.setZero(i%3, i/3);
                        }
                        return;
                    }
                }
            }
        }
    }

}
