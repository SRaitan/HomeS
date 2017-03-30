package EncryptionAlgorithms;
import com.company.Cipher;
import com.company.FileEncryptor;

public class ReverseCipher<T> extends Cipher<T> {
    private Cipher algToReverse;

    ReverseCipher(Cipher cipher) {
        algToReverse = cipher;
    }
    // T key because we can't be sure of what type the key will be
    // contrary to the other algorithms
    @Override
    public int encrypt(int oneByte, T key) {
        return algToReverse.decrypt(oneByte, key);
    }

    @Override
    public int decrypt(int oneByte, T key) {
        return algToReverse.encrypt(oneByte, key);
    }
}

