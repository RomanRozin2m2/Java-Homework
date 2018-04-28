package data.graph;

import java.io.Serializable;

public class BidirectionalGraph<V,E> extends Graph<V,E> implements Serializable {

    public void removeEdge(Vertex<V,E> from, Vertex<V,E> to){
        super.removeEdge(from, to);
        super.removeEdge(to, from);
    }

    public void addEdge(Vertex<V,E> from, Vertex<V,E> to, E edVal){
        super.addEdge(from, to, edVal);
        super.addEdge(to, from, edVal);
    }

    public void addEdge(Vertex<V,E> from, Vertex<V,E> to){
        addEdge(from, to, null);
    }
}
