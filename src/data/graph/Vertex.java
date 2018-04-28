package data.graph;

import data.DoubleLinkedList;

import java.io.Serializable;

public class Vertex<V,E> implements Serializable {
    protected V value;
    protected DoubleLinkedList<Edge<V,E>> edges;
    boolean isChecked;

     public Vertex(V value) {
         this.value = value;
         edges = new DoubleLinkedList<>();
         isChecked = false;
     }

     public Vertex(){
         this.value = null;
         edges = new DoubleLinkedList<>();
         isChecked = false;
     }

     public V getValue() {
         return value;
     }

     public DoubleLinkedList<Edge<V, E>> getEdges() {
         return edges;
     }

     public boolean hasEdgeTo(Vertex<V,E> vert){
         for(int h = 0; h < edges.getLength(); h++){
             if(edges.getValue(h).getVertex() == vert){
                 return true;
             }
         }
         return false;
     }

     public void addEdgeTo(Vertex<V,E> vert, E edVal){
         if (hasEdgeTo(vert)){
             throw new IllegalArgumentException("Уже есть такое ребро! Ошибка в Vertex::addEdgeTo!");
         }
         Edge<V,E> ed = new Edge<>(edVal, vert);
         edges.addToEnd(ed);
     }

     public Edge<V,E> getEdgeTo(Vertex<V,E> vert){
         for(int h = 0; h < edges.getLength(); h++){
             if(edges.getValue(h).getVertex() == vert){
                 return edges.getValue(h);
             }
         }
         throw new IllegalArgumentException("Такого ребра нет! Ошибка в Vertex::getEdgeTo!");
     }

     public void removeEdgeTo(Vertex<V,E> vert){
         if (!hasEdgeTo(vert)){
             throw new IllegalArgumentException("Такого ребра нет! Ошибка в Vertex::removeEdgeTo!");
         }
         for(int h = 0; h < edges.getLength(); h++){
             if(edges.getValue(h).getVertex() == vert){
                 edges.delete(h);
                 return;
             }
         }
     }

     public Vertex<V,E>[] getConnectedVertices(){
         Vertex<V,E>[] forReturn = new Vertex[edges.getLength()];
         for(int h = 0; h < edges.getLength(); h++){
             forReturn[h] = edges.getValue(h).getVertex();
         }
         return forReturn;
     }

 }
