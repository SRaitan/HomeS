package Key;

/**
 * Created by hackeru on 3/27/2017.
 */
public class MultKey extends Key<Integer> {
    @Override
    public Integer getKey() {
        return key;
    }

    public MultKey(int key) {setKey(key);}

    public MultKey() {}

    @Override
    public void setKey(Integer key) {
        this.key = (key % 2 == 0)? key + 1 : key;
    }
}
