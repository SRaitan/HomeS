package com.company;

public class CipherFactory {
    public static final int CAESAR = 1;
    public static final int XOR = 2;
    public static final int MULT = 3;

    public static Cipher getCipher(int type){
        Cipher cipher = null;
        switch (type) {
            case CAESAR:
                cipher =  new CaesarCipher();
                break;
            case XOR:
                cipher = new XORCipher();
                break;
            case MULT:
                cipher = new MultiplicationCipher();
        }
        return cipher;
    }

    public static Cipher getReverseCipher(int type) {
        return new ReverseCipher(CipherFactory.getCipher(type));
    }
}
