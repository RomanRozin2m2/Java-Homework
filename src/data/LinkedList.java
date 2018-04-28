package data;

import figures.Circle;
import misc.*;

public class LinkedList {
    private Node<Circle> startNode;
    private int length;

    public LinkedList(){
        startNode = null;
        length = 0;
    }

    public int getLength(){
        return length;
    }

    public boolean isInList(Circle circle){
        Node<Circle> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            if(currentNode.getValue().equals1(circle)) {
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }

    public Circle[] toArray(){
        Circle[] array = new Circle[length];
        Node<Circle> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            array[foest] = currentNode.getValue();
            currentNode = currentNode.getNext();
        }
        return array;
    }

    public void add(Circle circle){
        Node node = new Node(circle);
        if(startNode == null) {
            startNode = node;
        }
        else {
            Node currentNode = startNode;
            while(currentNode.hasNext()){
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(node);
        }

        length++;
    }

    public Circle[] filter(Parameter par, Equality eq, int num){
        LinkedList spisok = new LinkedList();
        Node<Circle> currentNode = startNode;
        Comparator comparator = new Comparator();
        for (int foest = 0; foest != length; foest++) {
            if(comparator.compare(eq, currentNode.getValue().getParameter(par), num)){
                spisok.add(currentNode.getValue());
            }
            currentNode = currentNode.getNext();
        }
        return spisok.toArray();
    }

    public Circle[] toArray(int first, int last){
        Circle[] array = new Circle[last - first];
        Node<Circle> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            if(foest >= first && foest < last) {
                array[foest - first] = currentNode.getValue();
            }
            currentNode = currentNode.getNext();
        }
        return array;
    }

    public Circle getValue(int index){

        if(index > length - 1 || index < 0){
            return null;
        }

        Node<Circle> currentNode = startNode;
        for(int foest = 0; foest != index; foest++){
            currentNode = currentNode.getNext();
        }
        return currentNode.getValue();
    }

    public void delete(int index){

        if(index > length - 1 || index < 0){
            return;
        }

        Node<Circle> previousNode = startNode;

        if(index == 0){
            startNode = startNode.getNext();
        }
        else {
            for(int foest = 0; foest != index - 1; foest++){
                previousNode = previousNode.getNext();
            }

            Node<Circle> nextNode = previousNode.getNext().getNext();

            previousNode.setNext(nextNode);
        }


        length--;
    }

    public String toString() {
        String s = "";
        Node<Circle> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            s += "Node: " + foest + ", Value: " + currentNode.getValue().toString() + "\n";
            currentNode = currentNode.getNext();
        }
        return s;
    }

}
