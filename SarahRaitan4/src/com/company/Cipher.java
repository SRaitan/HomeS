package com.company;
import java.io.*;

public abstract class Cipher implements CipherInterface{
    CipherListener listener;
    void setListener(CipherListener listener) {this.listener = listener;}

    void started (){
        if(listener != null)
            listener.started();
    }

    void finished (){
        if(listener != null)
            listener.finished();
    }

    @Override
    public abstract int encrypt(int oneByte, int key);

    @Override
    public abstract int decrypt(int oneByte, int key);

    public void action(File original, int key, boolean encrypt) {
        started();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        File newFile = returnFile(original, encrypt);
        try {
            outputStream = new FileOutputStream(newFile);
            inputStream = new FileInputStream(original);
            int oneByte;
            if (encrypt) {
                while ((oneByte = inputStream.read()) != -1)
                    outputStream.write(encrypt(oneByte, key));
            } else {
                while ((oneByte = inputStream.read()) != -1)
                    outputStream.write(decrypt(oneByte, key));
            }
            finished();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } finally {
            FileManipulator.closeFile(inputStream,outputStream);
        }
    }
    File returnFile(File original, boolean encrypt){
        String fname = original.getAbsolutePath();
        int pos = fname.lastIndexOf(".");
        if (pos > 0)
            fname = fname.substring(0, pos);
        if(encrypt)
            return new File(fname + "_encrypted.txt");
        return new File(fname + "_decrypted.txt");
    }

    interface CipherListener{
        void started();
        void finished();
    }

}
