package EncryptionAlgorithms;

import com.company.Cipher;

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
