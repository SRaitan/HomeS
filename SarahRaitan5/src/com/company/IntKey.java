package com.company;
import java.util.Random;

public class IntKey implements Key {
    private Integer key;
    private transient Random random = new Random(System.nanoTime());
    public Integer getKey() {
        key = random.nextInt(255) + 1;
        return key;
    }
    @Override
    public void setKey(Object key) {
        this.key = (Integer) key;
    }
}
