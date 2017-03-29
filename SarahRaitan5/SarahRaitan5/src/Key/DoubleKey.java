package Key;

import java.io.Serializable;

/**
 * Created by Raitan on 3/26/2017.
 */
public class DoubleKey <T,S> extends Key<Couple<T,S>> implements Serializable {

    public DoubleKey(T key1, S key2) {
        this.key = new Couple <T, S>(key1,key2);
    }

    @Override
    public Couple<T, S> getKey() {

        return key;
    }

    @Override
    public void setKey(Couple<T, S> key) {
        this.key=key;

    }
}
