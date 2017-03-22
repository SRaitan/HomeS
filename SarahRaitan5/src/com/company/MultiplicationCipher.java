package com.company;

/**
 * Created by hackeru on 3/19/2017.
 */
public class MultiplicationCipher extends Cipher {
    private int decKey;
    MultiplicationCipher() {decKey=0;}

    @Override
    public int encrypt(int oneByte, int key) {
        return  (key * oneByte) & 0x000000FF;
    }

    @Override
    public int decrypt(int oneByte, int key){
        if(decKey == 0)
            setDecKey(key);
        return decKey * oneByte;
    }

    private void setDecKey(int key) {
        int decKey = 0;
        for (int i = 0; i < 256; i++) {
            if(((i * key)& 0x000000FF) == 1) {
                decKey = i;
                break;
            }
        }
        this.decKey = decKey;
    }
}
