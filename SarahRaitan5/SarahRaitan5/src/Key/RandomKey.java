package Key;
import java.util.Random;

public class RandomKey extends Key <Integer> {

    private transient Random random = new Random(System.nanoTime());

    public RandomKey() {
        this.key = random.nextInt(255) + 1;
    }

    @Override
    public Integer getKey() {
        return key;
    }  
    public Integer newKey() {
        this.key = random.nextInt(255) + 1;
        return key;
    }

    @Override
    public void setKey(Integer key) {
        this.key = (Integer) key;
    }

}
