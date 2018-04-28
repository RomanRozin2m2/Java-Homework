package data;

public abstract class Evolver {
    protected NeuralNetwork[] networks;
    protected int generations;
    protected int exemplars;
    private int inputsCount;
    private int outputsCount;
    private int[] layers;

    public Evolver(int inputsCount, int outputsCoint, int[] layers, int generations, int exemplars){
        this.exemplars = exemplars;
        this.generations = generations;
        this.outputsCount = outputsCoint;
        this.inputsCount = inputsCount;
        this.layers = layers;
        networks = new NeuralNetwork[exemplars];
        for (int h = 0; h < exemplars; h++){
            networks[h] = new NeuralNetwork(inputsCount, outputsCoint, layers);
        }
    }

    public abstract void evolve();

    protected void generateNewExemplars(){
        sortNetworks();
        NeuralNetwork[] newGenerations = new NeuralNetwork[exemplars];
        int exemplarsToMutate = (int) (exemplars * 0.35);
        for (int h = 0; h < exemplarsToMutate; h++){
            newGenerations[h] = networks[h];
        }
        for (int h = 0; h < exemplarsToMutate; h++){
            newGenerations[h + exemplarsToMutate] = new NeuralNetwork(networks[h]);
        }
        for (int h = 2 * exemplarsToMutate; h < exemplars; h++){
            newGenerations[h] = new NeuralNetwork(inputsCount, outputsCount, layers);
        }
        networks = newGenerations;
    }
    
    protected void sortNetworks(){
        for (int i = 0; i < networks.length - 1; i++) {
            for (int j = 0; j < networks.length - 1; j++) {
                if (networks[j].value < networks[j + 1].value) {
                    NeuralNetwork tw = networks[j + 1];
                    NeuralNetwork on = networks[j];
                    networks[j] = tw;
                    networks[j + 1] = on;
                }
            }
        }
    }

}
