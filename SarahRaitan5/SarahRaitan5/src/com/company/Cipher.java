package com.company;
import java.io.*;

public abstract class Cipher<T>  {
    CipherListener listener;

    void setListener(CipherListener listener) {this.listener = listener;}

    void started (){
        if(listener != null)
            listener.onStarted();
    }

    void finished (){
        if(listener != null)
            listener.onFinished();
    }

    public void encryptOrDecrypt(OutputStream outputStream, InputStream inputStream, T key, boolean encryptFile) throws InvalidFileException {
        started();
        try {
            int oneByte;
            if(encryptFile) {
                while ((oneByte = inputStream.read()) != -1)
                    outputStream.write(encrypt(oneByte, key));
            }
            else{
                while ((oneByte = inputStream.read()) != -1)
                    outputStream.write(decrypt(oneByte, key));
            }
            finished();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract public int encrypt(int oneByte, T key);

    abstract public int decrypt(int oneByte, T key);

    interface CipherListener{
        void onStarted();
        void onFinished();
    }
}
