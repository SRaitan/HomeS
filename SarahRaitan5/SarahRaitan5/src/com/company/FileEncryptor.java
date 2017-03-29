package com.company;

import EncryptionAlgorithms.ReverseCipher;

import java.io.*;

class FileEncryptor <T> {

    boolean isValidFile(String filepath) {
        File file = new File(filepath);
        return (file.exists() && file.isFile() && file.canWrite() && file.canRead());
    }

    static File returnFile(File original, boolean encrypt) throws InvalidFileException {
        if (original == null)
            throw new InvalidFileException("No return file found");
        String fname = original.getAbsolutePath();
        int pos = fname.lastIndexOf(".");
        if (pos > 0)
            fname = fname.substring(0, pos);
        if (encrypt)
            return new File(fname + "_encrypted.txt");
        return new File(fname + "_decrypted.txt");
    }

    void writeObjectToFile(File file, Serializable toWrite) throws InvalidFileException {
        ObjectOutputStream objectOutputStream = null;
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(toWrite);
        } catch (FileNotFoundException e) {
            throw new InvalidFileException("No write file found");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeObjectOutputStream(objectOutputStream, outputStream);
        }
    }

    void closeObjectOutputStream(ObjectOutputStream objectOutputStream, OutputStream outputStream) {
        if (objectOutputStream != null)
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (outputStream != null)
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    Serializable readObjectFromFile(File file) throws InvalidFileException {
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        Serializable toRead = null;
        try {
            inputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);
            toRead = (Serializable) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new InvalidFileException("No read file found");
        } catch (IOException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            e.getMessage();
        } finally {
            closeObjectInputStream(inputStream, objectInputStream);
        }
        return toRead;
    }

    void closeObjectInputStream(InputStream inputStream, ObjectInputStream objectInputStream) {
        if (objectInputStream != null)
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (inputStream != null)
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    void closeFile(InputStream inputStream, OutputStream outputStream) {
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

    void encryptFile(File original, Cipher cipher, T key) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            File newFile = FileEncryptor.returnFile(original, true);
            outputStream = new FileOutputStream(newFile);
            inputStream = new FileInputStream(original);
            cipher.encrypt(outputStream, inputStream, key);
            if(cipher instanceof ReverseCipher)
                renameReverseFile(original,true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeFile(inputStream, outputStream);
        }
    }

    void decryptFile(File original, Cipher cipher, T key) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            File newFile = FileEncryptor.returnFile(original, true);
            outputStream = new FileOutputStream(newFile);
            inputStream = new FileInputStream(original);
            cipher.decrypt(outputStream, inputStream, key);
            if(cipher instanceof ReverseCipher)
                renameReverseFile(original,false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeFile(inputStream, outputStream);
        }
    }

    static void renameReverseFile (File original, boolean encrypt){
        try {
            new File(returnFile(original,!encrypt).getAbsolutePath()).renameTo(returnFile(original,encrypt));
        } catch (InvalidFileException e) {
            e.printStackTrace();
        }
    }

    File createKeyFile(File file){
        int lastDot = file.getPath().lastIndexOf('.');
        String newPath;
        newPath = file.getPath().substring(0, lastDot)+ ".cipherKey.bin";
        return new File(newPath);
    }

}
