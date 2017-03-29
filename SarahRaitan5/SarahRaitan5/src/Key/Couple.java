package Key;

import java.io.Serializable;

/**
 * Created by hackeru on 3/27/2017.
 */
public class Couple <T,K> implements Serializable {
    private T first;
    private K  second;

    public T getFirst() {
        return first;
    }

    public Couple(T first, K second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public K getSecond() {
        return second;
    }

    public void setSecond(K second) {
        this.second = second;
    }
}
