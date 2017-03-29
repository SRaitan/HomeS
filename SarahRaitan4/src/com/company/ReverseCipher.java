package com.company;
import java.io.File;

/**
 * Created by hackeru on 3/19/2017.
 */
public class ReverseCipher extends Cipher {

    private Cipher algToReverse;

    ReverseCipher(Cipher cipher) {
        algToReverse = cipher;
    }

    @Override
    public void action(File original, int key, boolean encrypt) {
        algToReverse.action(original, key, !encrypt);
        new File(returnFile(original,!encrypt).getAbsolutePath()).renameTo(returnFile(original,encrypt));
    }
    @Override
    public int encrypt(int oneByte, int key) {
        return algToReverse.decrypt(oneByte, key);
    }

    @Override
    public int decrypt(int oneByte, int key) {
        return algToReverse.encrypt(oneByte, key);
    }

}

