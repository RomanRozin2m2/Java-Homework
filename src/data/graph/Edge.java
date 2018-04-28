package data.graph;

import java.io.Serializable;

public class Edge<V,E> implements Serializable {
    protected E value;
    protected Vertex<V,E> vertex;

    public Edge(E value, Vertex<V, E> vertex) {
        this.value = value;
        this.vertex = vertex;
    }

    public Edge(Vertex<V, E> vertex) {
        this.value = null;
        this.vertex = vertex;
    }


    public E getValue() {
         return value;
    }

    public void setValue(E value){
        this.value = value;
    }

    public Vertex<V, E> getVertex() {
         return vertex;
     }
 }
