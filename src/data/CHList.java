package data;

import network.ClientHandler;

public class CHList extends DoubleLinkedList<ClientHandler> {
    public ClientHandler[] toClientsArray(){
        ClientHandler[] array = new ClientHandler[length];
        Node<ClientHandler> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            array[foest] = currentNode.getValue();
            currentNode = currentNode.getNext();
        }
        return array;
    }
}
