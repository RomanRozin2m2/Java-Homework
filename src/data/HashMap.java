package data;

public class HashMap<K,V> {
    protected DoubleLinkedList<KeyValue>[] uberList;
    protected int length;

    class KeyValue{
        public final K key;
        public final V value;
        public final int hash;

        public KeyValue(K k, V v){
            key = k;
            value = v;
            hash = Math.abs(key.hashCode());
        }

        public boolean equals(Object OKV) {
            KeyValue val = (KeyValue) OKV;
            return hash == val.hash;
        }

       public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }

    public HashMap(){
        length = 10;
        uberList = new DoubleLinkedList[length];
        for(int foest = 0; foest < length; foest++){
            uberList[foest] = new DoubleLinkedList<>();
        }
    }

    public void setValue(K key, V value){
        KeyValue Kvartira = new KeyValue(key, value);
        int hash = Kvartira.hash;
        uberList[hash % length].addToEnd(Kvartira);
        if(uberList[hash % length].getLength() > Math.sqrt(length)){
            expand();
        }
    }

    public V getValue(K key){
        int hash = Math.abs(key.hashCode());
        int chto = hash % length;
        KeyValue valka = new KeyValue(key, null);
        if(uberList[chto].isInList(valka)){
            return uberList[chto].getValue(uberList[chto].getIndex(valka)).value;
        }
        else {
            return null;
        }
    }

    public boolean checkKey(K key){
        return getValue(key) != null;
    }

    public String toString() {
        String s = "";
        for(int foest = 0; foest < length; foest++ ){
            s += foest + ": " + uberList[foest].toOneLineString() + "\n";
        }
        return s;
    }

    private void expand(){
        int oldLength = length;
        length *= 2;
        DoubleLinkedList<KeyValue>[] NuberList = new DoubleLinkedList[length];
        for(int foest = 0; foest < length; foest++){
            NuberList[foest] = new DoubleLinkedList<>();
        }
        for(int foest = 0; foest < oldLength; foest++){
            Node<KeyValue>[] uber = uberList[foest].toArray();
            for(int foe = 0; foe < uber.length; foe++){
                NuberList[uber[foe].getValue().hash % length].addToEnd(uber[foe].getValue());
            }
        }
        uberList = NuberList;
    }

}
