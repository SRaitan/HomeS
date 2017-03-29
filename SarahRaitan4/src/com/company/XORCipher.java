package com.company;

import java.io.*;

/**
 * Created by hackeru on 3/19/2017.
 */
public class XORCipher extends Cipher {

    @Override
    public int encrypt(int oneByte, int key) {
        return oneByte ^ key;
    }

    @Override
    public int decrypt(int oneByte, int key) {
        return oneByte ^ key;
    }

}
