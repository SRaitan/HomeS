package EncryptionAlgorithms;

import com.company.Cipher;

/**
 * Created by hackeru on 3/19/2017.
 */
public class XORCipher extends Cipher<Integer> {

    @Override
    public int encrypt(int oneByte, Integer key) {
        return oneByte ^ key;
    }

    @Override
    public int decrypt(int oneByte, Integer key) {
        return encrypt(oneByte,key);
    }
}
