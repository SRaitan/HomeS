package com.company;

import java.io.File;

import static javafx.scene.input.KeyCode.K;

/**
 * Created by hackeru on 3/22/2017.
 */
public class DoubleCipher extends Cipher {
    int key1;
    int key2;
    Cipher firstAlgorithm;
    Cipher secondAlgorithm;


    public DoubleCipher(Cipher firstAlgorithm, Cipher secondAlgorithm,int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
        this.firstAlgorithm = firstAlgorithm;
        this.secondAlgorithm = secondAlgorithm;
    }

    public void encrypt(File original) {
        firstAlgorithm.action(original, key1,true);
        secondAlgorithm.action(FileManipulator.returnFile(original,true), key2,true);
    }

    public void decrypt(File original) {
        secondAlgorithm.action(original, key2,false);
        firstAlgorithm.action(FileManipulator.returnFile(original,false), key1,false);
    }

    @Override
    public int encrypt(int oneByte, int key) {
        return secondAlgorithm.encrypt(firstAlgorithm.encrypt(oneByte, key1), key2);
    }

    @Override
    public int decrypt(int oneByte, int key) {
        return firstAlgorithm.decrypt(secondAlgorithm.decrypt(oneByte,key2), key1);
    }
}
