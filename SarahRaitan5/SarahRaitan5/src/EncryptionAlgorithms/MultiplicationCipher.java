package EncryptionAlgorithms;

import Key.Key;
import com.company.Cipher;

public class MultiplicationCipher extends Cipher<Integer>  {
    int decKey;

    public int encrypt(int oneByte, Integer key) {
        return  (key * oneByte) & 0x000000FF;
    }

    public int decrypt(int oneByte, Integer key){
        if(decKey == 0)
            decKey = getDecKey(key);
        return decKey * oneByte;
    }

    private int getDecKey(int key) {
        int decKey = 0;
        for (int i = 0; i < 256; i++) {
            if(((i * key) & 0x000000FF) == 1) {
                decKey = i;
                break;
            }
        }
       return decKey;
    }

}
