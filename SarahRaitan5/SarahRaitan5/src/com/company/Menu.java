package com.company;
import EncryptionAlgorithms.*;
import Key.*;
import Key.InvalidCipherKeyException;
import com.company.InputOutput.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Menu implements Cipher.CipherListener{
    //region constValues
    private static final String EXIT= "0";
    private static final String ENCRYPT = "1";
    private static final String DECRYPT = "2";
    //endregion
    private Input myInput;
    private Output myOutput;
    public FileEncryptor <DoubleKey<DoubleKey<Integer, Integer>, Integer>> fileEncryptor
            =new FileEncryptor<DoubleKey<DoubleKey<Integer, Integer>, Integer>>();

    Menu(Input myInput, Output myOutput) {
        this.myInput = myInput;
        this.myOutput = myOutput;
    }

    void DoubleMenu() {
        String action;
        Cipher<Couple> cipher1 = null;
        while (true) {
            myOutput.output("Welcome! Press 1 to encrypt a file, 2 to decrypt a file, 0 to exit: ");
            if ((action = myInput.input()).equals(EXIT))
                return;
            try {
                File originalFile=getFileFromUser();
                if (action.equals(ENCRYPT)) {
                    cipher1 = getAlgorithms();
                    DoubleKey<DoubleKey<Integer, Integer>, Integer> key = setKey(originalFile);
                    fileEncryptor.encryptFile(originalFile,cipher1,key);
                } else if (action.equals(DECRYPT)){
                    cipher1 = getAlgorithms();
                    DoubleKey <DoubleKey<Integer, Integer>, Integer> key = getKeyFromFile();
                    fileEncryptor.decryptFile(originalFile, cipher1, key);
                    break;
                }
            } catch (InvalidFileException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                // myOutput.output("Something went wrong... " + e.getMessage() + " Please try again");
            }
        }
    }
    public Cipher<Couple> getAlgorithms () throws InvalidFileException {
        Cipher<Couple> forReverse = new DoubleCipher(new CaesarCipher(), new MultiplicationCipher());
        Cipher<Couple> cipher = new DoubleCipher(forReverse,new XORCipher());
        cipher.setListener(this);//TODO: listener class
        return cipher;
    }
    DoubleKey<DoubleKey<Integer, Integer>, Integer> setKey(File original) throws InvalidFileException {
        DoubleKey<DoubleKey<Integer, Integer>, Integer> finalKey = makeMasterKey();
        fileEncryptor.writeObjectToFile(fileEncryptor.createKeyFile(original), finalKey);
        return finalKey;
    }

    private DoubleKey<DoubleKey<Integer, Integer>, Integer> makeMasterKey() {
        RandomKey randomKeyMaker = new RandomKey();
        MultKey multKey = new MultKey(randomKeyMaker.getKey());
        DoubleKey <Integer, Integer> forRev = new DoubleKey<Integer, Integer>(randomKeyMaker.newKey(), multKey.getKey());
        return new  DoubleKey<DoubleKey<Integer, Integer>, Integer>(forRev, randomKeyMaker.newKey());
    }

    private DoubleKey<DoubleKey<Integer, Integer>, Integer> getKeyFromFile () throws Exception{ //TODO: remove flags
        Serializable key = fileEncryptor.readObjectFromFile (getFileFromUser(true));
        if(key instanceof DoubleKey){
            DoubleKey<DoubleKey<Integer, Integer>, Integer> newKey =
                    (DoubleKey<DoubleKey<Integer, Integer>, Integer>) key;
            return newKey;
        }
        throw new InvalidCipherKeyException("Key could not be read from file");
    }
    private RandomKey makeIntKey (File file, boolean mult) throws InvalidCipherKeyException, InvalidFileException {
        RandomKey toWrite = new RandomKey();
        int key = toWrite.getKey();
            if (mult &&(key % 2 == 0)) {
                toWrite.setKey(key + 1);
            }
            fileEncryptor.writeObjectToFile(file, toWrite);
            return toWrite;
    }
    private File getFileFromUser() {
        return getFileFromUser(false);
    }
    private File getFileFromUser(boolean key) {
        if(!key)
            myOutput.output("Enter the path of your file: ");
        else
            myOutput.output("Enter the path of the file of your key: ");
        String filePath = myInput.input();
        while (!fileEncryptor.isValidFile(filePath) || filePath.isEmpty()) {
            myOutput.output("The path you entered seems to be invalid or nonexistent. Please retry: ");
            filePath = myInput.input();// TODO: make separate func in fileenc and throw exceptions

        }
        return new File(filePath);
    }

    @Override
    public void started() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        myOutput.output("Started at: "+ dateFormat.format(date));
    }

    @Override
    public void finished() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        myOutput.output("Finished at: "+ dateFormat.format(date));
    }

}