package data;

public class TextBox extends DoubleLinkedList<String>{

    public TextBox() {
        //String empty = "";
        //addToStart(empty);
    }

    public TextBox(String text){
        String[] taxta = text.split("\n");
        for (int i = taxta.length - 1; i >= 0; i--) {
            addToStart(taxta[i]);
        }
    }

    public void editString(int charPosition){

    }

    public void addChar(int charPosition, char ch){
        if(charPosition >= curNode.getValue().length() || charPosition < 0){
            return;
        }
        String curValue = curNode.getValue();
        String newString = curValue.substring(0, charPosition);
        String newStringTwo = curValue.substring(charPosition, curValue.length());
        newString += ch + newStringTwo;
        curNode.setValue(newString);
    }

    public void addToMiddle(int id, int curPos){
        Node<String> noda = getNode(id);
        Node<String> nextNoda = noda.getNext();
        String stayData = noda.getValue().substring(0, curPos);
        String data = noda.getValue().substring(curPos, noda.getValue().length());
        noda.setValue(stayData);
        Node<String> newNoda = new Node<String>(data);
        noda.setNext(newNoda);
        nextNoda.setPrev(newNoda);
        newNoda.setPrev(noda);
        newNoda.setNext(nextNoda);
        length++;
    }

    public String pureToString() {
        String s = "";
        Node<String> currentNode = startNode;
        for(int foest = 0; foest != length; foest++) {
            if (foest == length - 1) {
                s += currentNode.getValue().trim();
            }
            else {
                s += currentNode.getValue().trim() + "\n";
            }
            currentNode = currentNode.getNext();
        }
        return s;
    }
}
