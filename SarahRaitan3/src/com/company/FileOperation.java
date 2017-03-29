package com.company;
import java.io.File;


public interface FileOperation {
    //void action(File original) throws Exception;
    void encrypt (File original, int key);
    void decrypt (File original, int key);
}
