package com.company;

import java.io.*;

/**
 * Created by hackeru on 3/19/2017.
 */
public class CaesarCipher implements FileOperation {

    private void caesar(File original, int key, boolean encrypt) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        File newFile = returnFile(original.getAbsolutePath(), encrypt);
        try {
            outputStream = new FileOutputStream(newFile);
            inputStream = new FileInputStream(original);
            int oneByte;
            while ((oneByte = inputStream.read()) != -1) {
                if(encrypt)
                    outputStream.write(oneByte + key);
                else
                    outputStream.write(oneByte - key);
            }
        // friendly & more explained exceptions
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } finally {
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.getMessage();
                }
        }
    }

    @Override
    public void encrypt(File original, int key) {
        caesar(original,key,true);
    }

    @Override
    public void decrypt(File original, int key) {
        caesar(original,key,false);
    }

    public File returnFile(String fname, boolean encrypt){
        int pos = fname.lastIndexOf(".");
        if (pos > 0)
            fname = fname.substring(0, pos);
        if(encrypt)
            return new File(fname + "_encrypted.txt");
        return new File(fname + "_decrypted.txt");
    }

}
