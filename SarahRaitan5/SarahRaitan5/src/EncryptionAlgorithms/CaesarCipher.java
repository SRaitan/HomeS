package EncryptionAlgorithms;

import Key.Key;
import com.company.Cipher;
import com.company.InvalidFileException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hackeru on 3/19/2017.
 */
public class CaesarCipher extends Cipher<Integer> {

    @Override
    public int encrypt(int oneByte, Integer key) {
        return oneByte + key;
    }

    @Override
    public int decrypt(int oneByte, Integer key) {
        return oneByte - key;
    }


}
