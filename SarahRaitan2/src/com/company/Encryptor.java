package com.company;

import java.io.File;

public class Encryptor implements FileOperation {
    @Override
    public void action(File original) throws Exception {
            System.out.println("Encrypted file successfully");
    }
}
