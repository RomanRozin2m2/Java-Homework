package data;

import java.io.Serializable;

public class DoubleLinkedList<T> implements Serializable {
    protected Node<T> startNode;
    protected Node<T> curNode;
    protected Node<T> endNode;
    protected int length;

    public DoubleLinkedList(){
        startNode = null;
        curNode = null;
        endNode = null;
        length = 0;
    }

    public Node<T>[] toArray(){
        Node<T>[] array = new Node[length];
        Node<T> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            array[foest] = currentNode;
            currentNode = currentNode.getNext();
        }
        return array;
    }

    public int getLength(){
        return length;
    }

    public int getIndex(T value){
        for(int foest = 0; foest < length; foest++){
            if(getValue(foest).equals(value)){
                return foest;
            }
        }
        return -1;
    }

    public T getCurValue(){
        return curNode.getValue();
    }

    public void delCurValue() {

        if (length == 1) {
            startNode = endNode = curNode = null;
        } else if(length == 0) {
            return;
        } else if(curNode == startNode) {
                startNode = startNode.getNext();
                curNode = startNode;
                startNode.setPrev(null);
        } else if(curNode == endNode) {
            endNode = endNode.getPrev();
            curNode = endNode;
            endNode.setNext(null);
        } else {
            Node<T> previousNode = curNode.getPrev();
            Node<T> nextNode = curNode.getNext();
            previousNode.setNext(nextNode);
            nextNode.setPrev(previousNode);
            curNode = previousNode;
        }
        length--;
    }

    public boolean isInList(T input){
        Node<T> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            if(currentNode.getValue().equals(input)) {
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }

    public void addToEnd(T input){
        Node<T> node = new Node<>(input);
        if(startNode == null) {
            startNode = node;
            curNode = node;
            endNode = node;
        }
        else {
            endNode.setNext(node);
            node.setPrev(endNode);
            endNode = node;
        }
        length++;
    }

    public void addToStart(T input){
        Node<T> node = new Node<>(input);
        if(startNode == null) {
            startNode = node;
            curNode = node;
            endNode = node;
        }
        else {
            startNode.setPrev(node);
            node.setNext(startNode);
            startNode = node;
        }

        length++;
    }

    public T getValue(int index){

        if(index > length - 1 || index < 0){
            return null;
        }

        Node<T> currentNode = startNode;
        for(int foest = 0; foest != index; foest++){
            currentNode = currentNode.getNext();
        }
        curNode = currentNode;
        return currentNode.getValue();
    }

    public void setValue(int index, T value){

        if(index > length - 1 || index < 0){
            return;
        }

        Node<T> currentNode = startNode;
        for(int foest = 0; foest != index; foest++){
            currentNode = currentNode.getNext();
        }
        curNode = currentNode;
        currentNode.setValue(value);
    }

    public Node<T> getNode(int index){

        if(index > length - 1 || index < 0){
            return null;
        }

        Node<T> currentNode = startNode;
        for(int foest = 0; foest != index; foest++){
            currentNode = currentNode.getNext();
        }
        curNode = currentNode;
        return currentNode;
    }

    public void delete(int index){

        if(index > length - 1 || index < 0){
            return;
        }

        Node<T> previousNode = startNode;


        if(length == 1){
            startNode = endNode = curNode = null;
        }
        else if(index == 0){
            if(curNode == startNode) {
                startNode = startNode.getNext();
                curNode = startNode;
                startNode.setPrev(null);
            }
            else {
                startNode = startNode.getNext();
                startNode.setPrev(null);
            }
        }
        else if(index == length - 1){
            if(curNode == endNode) {
                endNode = endNode.getPrev();
                curNode = endNode;
                endNode.setNext(null);
            }
            else {
                endNode = endNode.getPrev();
                endNode.setNext(null);
            }
        }
        else {
            for(int foest = 0; foest != index - 1; foest++){
                previousNode = previousNode.getNext();
            }

            Node<T> nextNode = previousNode.getNext().getNext();

            if(curNode == previousNode.getNext()){
                curNode = previousNode;
            }
            previousNode.setNext(nextNode);
            nextNode.setPrev(previousNode);
        }


        length--;
    }

    public void changeValue(int index, T value){
        if(index > length - 1 || index < 0){
            return;
        }

        Node<T> currentNode = startNode;
        for(int foest = 0; foest != index; foest++){
            currentNode = currentNode.getNext();
        }
        currentNode.setValue(value);
        curNode = currentNode;
    }

    public String toOneLineString(){
        String s = "";
        Node<T> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            s += currentNode.getValue().toString();
            if(foest + 1 != length){
                s += ", " ;
            }
            currentNode = currentNode.getNext();
        }
        return s;
    }

    public String toString() {
        String s = "";
        Node<T> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            s += "Node: " + foest + ", Value: " + currentNode.getValue().toString() + "\n";
            currentNode = currentNode.getNext();
        }
        return s;
    }

}
