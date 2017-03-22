package com.company;
import java.io.File;
import java.io.Serializable;
import java.util.Random;

public class IntKey implements Key {
    Integer key;
    private Random random = new Random(System.nanoTime());
    public Integer getKey() {
        return key;
    }

    @Override
    public void setKey(Object key) {
        this.key = (Integer) key;
    }

    IntKey() {
        key = random.nextInt(255) + 1;
    }
}
