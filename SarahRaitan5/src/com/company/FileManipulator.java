package com.company;

import java.io.*;

class FileManipulator {

   static boolean isValidFile(String filepath){
        File file = new File (filepath);
        return (file.exists() && file.isFile() && file.canWrite() && file.canRead());
    }
    static File returnFile(File original, boolean encrypt) throws InvalidFileException {
       if(original == null)
           throw new InvalidFileException("No file found");
        String fname = original.getAbsolutePath();
        int pos = fname.lastIndexOf(".");
        if (pos > 0)
            fname = fname.substring(0, pos);
        if(encrypt)
            return new File(fname + "_encrypted.txt");
        return new File(fname + "_decrypted.txt");
    }
    static void writeObjectToFile (File file, Serializable toWrite) throws InvalidFileException {
        ObjectOutputStream objectOutputStream = null;
        OutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(toWrite);
        } catch (FileNotFoundException e) {
            throw new InvalidFileException("No write file found");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(objectOutputStream != null)
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    static Serializable readObjectFromFile (File file) throws InvalidFileException {
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        Serializable toRead = null;
        try{
            inputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);
            toRead = (Serializable) objectInputStream.readObject();
            //return toRead;
        } catch (FileNotFoundException e) {
            throw new InvalidFileException("No read file found");
        } catch (IOException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }finally {
            if(objectInputStream != null)
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return toRead;
    }
    static void closeFile(InputStream inputStream, OutputStream outputStream){
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
