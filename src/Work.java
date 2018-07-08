import CoZ.*;
import data.NeuralNetwork;
import graphics.BaseGUI;
import misc.Games;

import java.io.IOException;

public class Work {

    public static void main(String[] args) throws IOException {

        int[] n = new int[3];
        n[0] = 3;
        n[1] = 4;
        n[2] = 1;
        NeuralNetwork net = new NeuralNetwork(3, 5, n);
        double[] inp = new double[3];
        inp[1] = 0;
        inp[0] = 5;
        inp[2] = 9;
        inp = net.calculate(inp);
        //for (int h = 0; h < inp.length; h++){
        //   System.out.println(inp[h]);
        //}
        //Game g = new Game(new RealPlayer(), new ComputerPlayer());
        //BaseGUI gui = new BaseGUI(new String[]{"Singleplayer as X", "Singleplayer as O","Multiplayer as X","Multiplayer as O","Evolution"}, Games.COZ);
        BaseGUI gui = new BaseGUI(new String[]{"Single2048"}, Games.G2048);

        // gui.setSomething(2, 2, CoZ_Node.ZERO);
        //gui.setSomething(4, 4, CoZ_Node.CROSS);


        //CoZ_Evolver ev = new CoZ_Evolver(50, 200, 20, true);
        //ev.evolve();





        // sc = new Scanner(System.in);

        //BidirectionalGraph<Integer, Integer> vortex = new BidirectionalGraph<>();
        //Vertex<Integer, Integer>[] graphite = new Vertex[13];
        //for(int h = 0; h < graphite.length; h++){
        //    graphite[h] = new Vertex<>(h);
        // vortex.addVertex(graphite[h]);
        //}
        //vortex.addEdge(graphite[6], graphite[3]);
        //vortex.addEdge(graphite[6], graphite[5]);
        ///vortex.addEdge(graphite[6], graphite[10]);
        //vortex.addEdge(graphite[10], graphite[8]);
        //vortex.addEdge(graphite[10], graphite[12]);
        //vortex.addEdge(graphite[10], graphite[11]);
        //vortex.addEdge(graphite[11], graphite[7]);
        //vortex.addEdge(graphite[5], graphite[9]);
        //vortex.addEdge(graphite[5], graphite[2]);
        //vortex.addEdge(graphite[2], graphite[1]);
        //vortex.addEdge(graphite[2], graphite[3]);
        //vortex.addEdge(graphite[1], graphite[4]);
        //vortex.addEdge(graphite[4], graphite[7]);
        //System.out.println(vortex.isConnected(graphite[11], graphite[12]));
    }
}