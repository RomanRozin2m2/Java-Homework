package data;

import data.graph.*;

import java.io.Serializable;
import java.util.Random;

public class NeuralNetwork extends BidirectionalGraph<Pair<Double,Double>, Double> implements Serializable {
    protected DoubleLinkedList<Vertex<Pair<Double,Double>, Double>>[] verticesByLayers;
    protected final int inputsCount;
    protected final int outputsCount;
    protected final int[] hiddenLayers;
    public int value;
    public final int ID;
    protected Random random;

    private static int nextId = 0;

    public NeuralNetwork(int inputsCount, int outputsCount, int[] hiddenLayers) {
        this.inputsCount = inputsCount;
        this.outputsCount = outputsCount;
        this.hiddenLayers = hiddenLayers;
        value = 0;
        random = new Random();
        generateGraph();
        ID = nextId;
        nextId++;
    }

    public NeuralNetwork(NeuralNetwork network){
        this(network.inputsCount, network.outputsCount, network.hiddenLayers);
        for (int h = 1; h < hiddenLayers.length + 2; h++){
            for (int i = 0; i < verticesByLayers[h].getLength(); i++){
                Vertex<Pair<Double, Double>, Double> currentVertex = verticesByLayers[h].getValue(i);
                Vertex<Pair<Double, Double>, Double> netCurrentVertex = network.verticesByLayers[h].getValue(i);
                if (ifCanCoefficientStay()){
                    currentVertex.getValue().koefficent = netCurrentVertex.getValue().koefficent;
                }
                for (int j = 0; j < verticesByLayers[h - 1].getLength(); j++){
                    Vertex<Pair<Double, Double>, Double> previousVertex = verticesByLayers[h - 1].getValue(j);
                    Vertex<Pair<Double, Double>, Double> netPreviousVertex = network.verticesByLayers[h - 1].getValue(j);
                    Edge<Pair<Double, Double>, Double> currentEdge = currentVertex.getEdgeTo(previousVertex);
                    Edge<Pair<Double, Double>, Double> netCurrentEdge = netCurrentVertex.getEdgeTo(netPreviousVertex);
                    if (ifCanCoefficientStay()){
                        currentEdge.setValue(netCurrentEdge.getValue());
                    }
                }
            }
        }
    }

    private void generateGraph() {
        int allLayersLength = hiddenLayers.length + 2;
        verticesByLayers = new DoubleLinkedList[allLayersLength];
        for (int h = 0; h < allLayersLength; h++) {
            verticesByLayers[h] = new DoubleLinkedList<>();
        }
        for (int h = 0; h < hiddenLayers.length; h++) {
            for (int i = 0; i < hiddenLayers[h]; i++) {
                double rve = getRandomValue();
                Vertex<Pair<Double,Double>, Double> vertex = new Vertex(new Pair<Double, Double>(rve, 0.0));
                addVertex(vertex);
                verticesByLayers[h + 1].addToEnd(vertex);
            }
        }
        for (int h = 0; h < inputsCount; h++) {
            Vertex<Pair<Double,Double>, Double> vertex = new Vertex(new Pair<Double, Double>(0.0, 0.0));
            addVertex(vertex);
            verticesByLayers[0].addToEnd(vertex);
        }
        for (int h = 0; h < outputsCount; h++){
            double rve = getRandomValue();
            Vertex<Pair<Double,Double>, Double> vertex = new Vertex(new Pair<Double, Double>(rve, 0.0));
            addVertex(vertex);
            verticesByLayers[verticesByLayers.length - 1].addToEnd(vertex);
        }
        for (int h = 1; h < allLayersLength; h++){
            for (int i = 0; i < verticesByLayers[h].getLength(); i++){
                for (int j = 0; j < verticesByLayers[h - 1].getLength(); j++){
                    double rve = getRandomValue();
                    addEdge(verticesByLayers[h - 1].getValue(j), verticesByLayers[h].getValue(i), rve);
                }
            }
        }
    }

    private double getRandomValue(){
        return random.nextDouble() * 2 - 1;
    }

    public double[] calculate(double[] input){
        double[] returnable = new double[outputsCount];
        if (input.length != inputsCount){
            throw new IllegalArgumentException("Количество параметров должно быть равно " + inputsCount + ", а пришло " + input.length + ".");
        }
        for (int h = 0; h < verticesByLayers[0].getLength(); h++){
            verticesByLayers[0].getValue(h).getValue().value = input[h];
        }
        for (int h = 1; h < hiddenLayers.length + 2; h++){
            for (int i = 0; i < verticesByLayers[h].getLength(); i++){
                Vertex<Pair<Double, Double>, Double> curVertex = verticesByLayers[h].getValue(i);
                Pair<Double,Double> para = curVertex.getValue();
                para.value = 0.0;
                for (int j = 0; j < verticesByLayers[h - 1].getLength(); j++){
                    double edgeValue = verticesByLayers[h - 1].getValue(j).getEdgeTo(curVertex).getValue();
                    double vertexValue = verticesByLayers[h - 1].getValue(j).getValue().value;
                    para.value += edgeValue * vertexValue;
                }
                para.value += para.koefficent;
                if (para.value < 0){
                    para.value = 0.0;
                }
            }
        }
        for (int h = 0; h < verticesByLayers[verticesByLayers.length - 1].getLength(); h++){
            returnable[h] = verticesByLayers[verticesByLayers.length - 1].getValue(h).getValue().value;
        }
        return returnable;
    }

    private boolean ifCanCoefficientStay(){
        return random.nextDouble() < 0.9;
    }
}
