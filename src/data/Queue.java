package data;

public class Queue<T> {
    private int length;
    private Node<T> last;
    private Node<T> first;

    public Queue(){
         last = null;
         first = null;
         length = 0;
    }

    public void add(T data){
        Node<T> node = new Node<T>(data);
        if(length == 0){
            first = node;
            last = node;
            length = 1;
        }
        else if(length > 0){
            last.setNext(node);
            last = node;
            length++;
        }
    }

    public T get(){
        if(length == 0){
            return null;
        }
        else if(length == 1){
            Node<T> lat = last;
            last = null;
            first = null;
            length = 0;
            return lat.getValue();
        }
        else if(length > 1){
            Node<T> fst = first;
            first = first.getNext();
            length--;
            return fst.getValue();
        }
        return null;
    }

    public int getLength(){
        return length;
    }
}
