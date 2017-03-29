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

    public void encrypt(OutputStream outputStream, InputStream inputStream, T key) throws InvalidFileException {
        started();
        try {
            int oneByte;
            while ((oneByte = inputStream.read()) != -1)
                outputStream.write(encrypt(oneByte,key));
            finished();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt(OutputStream outputStream, InputStream inputStream, T key) throws InvalidFileException {
        try{
            started();
            int oneByte;
            while ((oneByte = inputStream.read()) != -1)
                outputStream.write(decrypt(oneByte, key));
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
