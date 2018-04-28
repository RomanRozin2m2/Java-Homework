package data;

import java.io.Serializable;

class Node<T> implements Serializable {
    private T value;
    private Node<T> next;
    private Node<T> prev;

    public Node(T in){
        value = in;
        next = null;
    }

    public void setNext(Node<T> nxt){
        next = nxt;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getNext(){
        return next;
    }

    public T getValue(){
        return value;
    }

    public boolean hasNext(){
        return next != null;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prv) {
        prev = prv;
    }
}
