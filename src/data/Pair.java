package data;

import java.io.Serializable;

public class Pair<T,N> implements Serializable {
    public T koefficent;
    public N value;

    public Pair(){

    }

    public Pair(T fir, N sec){
        koefficent = fir;
        value = sec;
    }
}
