package com.company;
import EncryptionAlgorithms.*;
import Key.*;
import Key.InvalidCipherKeyException;
import com.company.InputOutput.*;
import java.io.*;

public class Menu {
    //region constValues
    private static final String EXIT= "0";
    private static final String ENCRYPT = "1";
    private static final String DECRYPT = "2";
    //endregion
    //region singleton
    private static Menu theInstance;
    private Menu(Input myInput, Output myOutput) {
        this.myInput = myInput;
        this.myOutput = myOutput;
    }
    public static Menu getInstance(){
        if(theInstance == null)
            theInstance = new Menu(new UserInput(), new UserOutput());
        return theInstance;
    }
    //endregion
    private Input myInput;
    private Output myOutput;
    FileEncryptor <DoubleKey<DoubleKey<Integer, Integer>, Integer>> fileEncryptor
            = new FileEncryptor<DoubleKey<DoubleKey<Integer, Integer>, Integer>>();

    void DoubleMenu() {
        String action;
        while (true) {
            myOutput.output("Welcome! Press 1 to encrypt a file, 2 to decrypt a file, 0 to exit: ");
            if ((action = myInput.input()).equals(EXIT))
                return;
            try {
                File originalFile = getFileFromUser();
                DoubleKey <DoubleKey<Integer, Integer>, Integer> cipherKey;
                if (action.equals(ENCRYPT))
                    cipherKey = setKey(originalFile);
                else
                    cipherKey = getKeyFromFile();
                fileEncryptor.encryptOrDecryptFile(originalFile, getAlgorithms() ,cipherKey, action.equals(ENCRYPT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Cipher<Couple> getAlgorithms () throws InvalidFileException {
        Cipher<Couple> forReverse = new DoubleCipher(new CaesarCipher(), new MultiplicationCipher());
        Cipher<Couple> cipher = new DoubleCipher(new ReverseCipher(forReverse), new XORCipher());
        cipher.setListener(new EncryptionListener(myOutput));
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

    private DoubleKey<DoubleKey<Integer, Integer>, Integer> getKeyFromFile () throws Exception{
        Serializable key = fileEncryptor.readObjectFromFile (getFileFromUser(true));
        if(key instanceof DoubleKey){
            DoubleKey<DoubleKey<Integer, Integer>, Integer> newKey =
                    (DoubleKey<DoubleKey<Integer, Integer>, Integer>) key;
            return newKey;
        }
        throw new InvalidCipherKeyException("Key could not be read from file");
    }

    private File getFileFromUser() {
        return getFileFromUser(false);
    }
    private File getFileFromUser(boolean isKeyFile) {
        if(!isKeyFile)
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

}
