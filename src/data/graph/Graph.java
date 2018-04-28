package data.graph;

import data.*;

import java.io.Serializable;

public class Graph<V,E> implements Serializable {
    protected DoubleLinkedList<Vertex<V,E>> vertices;

    public Graph() {
        vertices = new DoubleLinkedList<>();
    }

    public boolean hasVertex(Vertex<V,E> vert){
        for(int h = 0; h < vertices.getLength(); h++){
            if (vertices.getValue(h) == vert){
                return true;
            }
        }
        return false;
    }

    public boolean hasEdge(Vertex<V,E> from, Vertex<V,E> to){
        if (!hasVertex(from) || !hasVertex(to)){
            throw new IllegalArgumentException("Таких вершин нет! Ошибка в Graph::hasEdge!");
        }
        else{
            return from.hasEdgeTo(to);
        }
    }

    public void removeVertex(Vertex<V,E> vert){
        if (!hasVertex(vert)){
            throw new IllegalArgumentException("Такой вершины не существует! Ошибка в Graph::removeVertex!");
        }
        for(int h = 0; h < vertices.getLength(); h++){
            if (vertices.getValue(h).hasEdgeTo(vert)){
                vertices.getValue(h).removeEdgeTo(vert);
            }
        }
        for(int h = 0; h < vertices.getLength(); h++){
            if (vertices.getValue(h) == vert){
                vertices.delete(h);
                return;
            }
        }
    }

    public void removeEdge(Vertex<V,E> from, Vertex<V,E> to){
        if(!hasVertex(from) || !hasVertex(to)){
            throw new IllegalArgumentException("Такой вершины не существует! Ошибка в Graph::removeEdge!");
        }
        else if(!hasEdge(from, to)){
            throw new IllegalArgumentException("Такого ребра не существует! Ошибка в Graph::removeEdge!");
        }
        from.removeEdgeTo(to);
    }

    public void addVertex(Vertex<V,E> vert){
        if(hasVertex(vert)){
            throw new IllegalArgumentException("Такая вершина уже есть! Ошибка в Graph::addVertex");
        }
        else {
            vertices.addToEnd(vert);
        }
    }

    public void addEdge(Vertex<V,E> from, Vertex<V,E> to){
        if (hasEdge(from, to)){
            throw new IllegalArgumentException("Такое ребро уже существует! Ошибка в Graph::addEdge");
        }
        from.addEdgeTo(to, null);
    }

    public void addEdge(Vertex<V,E> from, Vertex<V,E> to, E edVal){
        if (hasEdge(from, to)){
            throw new IllegalArgumentException("Такое ребро уже существует! Ошибка в Graph::addEdge");
        }
        from.addEdgeTo(to, edVal);
    }

    protected void clearChecked(){
        for (int h = 0; h < vertices.getLength(); h++){
            vertices.getValue(h).isChecked = false;
        }
    }

    public boolean isConnected(Vertex<V,E> from, Vertex<V,E> to) {
        clearChecked();
        Queue<Vertex<V,E>> vortex = new Queue<>();
        vortex.add(from);
        while (true){
            Vertex<V,E> next = searchByWidthIteration(vortex);
            System.out.println("Next number in isConnected search iteration - " + next.getValue());
            if(next == null){
                return false;
            }
            if (next == to){
                return true;
            }
        }
    }

    protected Vertex<V,E> searchByWidthIteration(Queue<Vertex<V,E>> vortex){
        if (vortex.getLength() == 0){
            return null;
        }
        Vertex<V,E> returnable = vortex.get();
        Vertex<V,E>[] connectedVertices = returnable.getConnectedVertices();
        for (int h = 0; h < connectedVertices.length; h++){
            if (!connectedVertices[h].isChecked){
                vortex.add(connectedVertices[h]);
                connectedVertices[h].isChecked = true;
            }
        }
        return returnable;
    }

    protected Vertex<V,E> searchByDepthIteration(Stack<Vertex<V,E>> vortex){
        if (vortex.getLength() == 0){
            return null;
        }
        if (!vortex.peek().isChecked){
            vortex.peek().isChecked = true;
            return vortex.peek();
        }
        while (true){
            Vertex<V,E>[] connectedVertices = vortex.peek().getConnectedVertices();
            for (int h = 0; h < connectedVertices.length; h++){
                if (!connectedVertices[h].isChecked){
                    vortex.push(connectedVertices[h]);
                    connectedVertices[h].isChecked = true;
                    return connectedVertices[h];
                }
            }
            if (vortex.getLength() >= 1){
                vortex.pop();
            }
            else {
                return null;
            }
        }
    }
}
