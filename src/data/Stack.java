package data;

public class Stack<T> {
    private Node<T> startNode;
    private int length;

    public Stack(){
        startNode = null;
        length = 0;
    }

    public void push(T pushable){
        Node<T> n = new Node<>(pushable);
        n.setNext(startNode);
        startNode = n;
        length++;
    }

    public int getLength() {
        return length;
    }

    public T pop(){
        T sav = startNode.getValue();
        startNode = startNode.getNext();
        length--;
        return sav;
    }

    public T peek(){
        return startNode.getValue();
    }
}
