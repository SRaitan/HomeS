package com.company;

import Key.Key;

/**
 * Created by hackeru on 3/21/2017.
 */
public interface CipherInterface<T> {
    int encrypt(int oneByte, T key);
    int decrypt(int oneByte, T key);
}
