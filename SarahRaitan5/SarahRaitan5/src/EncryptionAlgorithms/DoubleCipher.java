package EncryptionAlgorithms;
import Key.*;
import com.company.Cipher;

public class DoubleCipher <T,K> extends Cipher<DoubleKey<T,K>>{
    Cipher firstAlgorithm;
    Cipher secondAlgorithm;

    @Override
    public int encrypt(int oneByte, DoubleKey<T,K> key) {
        return secondAlgorithm.encrypt((firstAlgorithm.encrypt(oneByte, key.getKey().getFirst())), key.getKey().getSecond());
    }

    @Override
    public int decrypt(int oneByte, DoubleKey<T, K> key) {
        return firstAlgorithm.decrypt(secondAlgorithm.decrypt(oneByte, key.getKey().getSecond()), key.getKey().getFirst());
    }

    public DoubleCipher(Cipher firstAlgorithm, Cipher secondAlgorithm) {
        this.firstAlgorithm = firstAlgorithm;
        this.secondAlgorithm = secondAlgorithm;
    }

}
