package com.company;

/**
 * Created by hackeru on 3/19/2017.
 */
public class CaesarCipher extends Cipher {

    @Override
    public int encrypt(int oneByte, int key){
        return oneByte + key;
    }

    @Override
    public int decrypt(int oneByte, int key) {
        return oneByte - key;
    }

}
