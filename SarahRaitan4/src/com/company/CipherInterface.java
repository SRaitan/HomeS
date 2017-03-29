package com.company;

import java.io.File;

/**
 * Created by hackeru on 3/21/2017.
 */
public interface CipherInterface {
    int encrypt(int oneByte, int key);
    int decrypt(int oneByte, int key);
}
