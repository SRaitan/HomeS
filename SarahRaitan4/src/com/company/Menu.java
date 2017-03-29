package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Menu implements Cipher.CipherListener, Input,Output{
    //region constValues
    static final String EXIT= "0";
    static final String ENCRYPT = "1";
    static final String DECRYPT = "2";
    static final int CAESAR = 1;
    static final int XOR = 2;
    static final int MULT= 3;
    static final int REV = 4;
    Input myInput;
    Output myOutput;
    long runningTime;
    //endregion
    void start() {
        String input;
        myOutput.output("Welcome! Press 1 to encrypt the file, 2 to decrypt the file, 0 to exit: ");
        if (!(input = myInput.input()).equals(EXIT)) {
            mainMenu(input);
            start();
            return;
        }
        myOutput.output("Exiting application");
    }
    private int chooseOption(){
        myOutput.output("Choose your algorithm:" +
                "   1-Caesar Cipher " +
                "   2-XOR Cipher" +
                "   3-Multiplication Cipher" +
                "   4-Reverse Cipher");
        return Integer.valueOf(myInput.input());
    }
    private Cipher chooseRevOption(){
        boolean returnToSwitch = false;
            myOutput.output("Choose your algorithm to reverse:" +
                    "   1-Caesar Cipher " +
                    "   2-XOR Cipher" +
                    "   3-Multiplication Cipher");
            int chooser = Integer.valueOf(myInput.input());
            switch (chooser) {
                case CAESAR:
                case XOR:
                case MULT:
                    return CipherFactory.getReverseCipher(chooser);
                default: {
                    myOutput.output("Invalid algorithm choice");
                    return chooseRevOption();
                }
            }
    }
    void mainMenu(String input) {
        int key;
        boolean returnToSwitch = false;
        Cipher cipher = null;
        int cipherOption = 0;
        try {
            do {
                switch (input) {
                    case ENCRYPT:
                        Random random = new Random(System.currentTimeMillis());
                        key = random.nextInt(255) + 1;
                        switch (cipherOption = chooseOption()) {
                            case MULT:
                                key = key % 2 == 0 ? key + 1 : key;
                            case CAESAR:
                            case XOR:
                                cipher = CipherFactory.getCipher(cipherOption);
                                break;
                            case REV:
                                cipher = chooseRevOption();
                                break;
                            default: {
                                myOutput.output("Incorrect cipher choice");
                                returnToSwitch = true;
                            }
                        }
                        if (cipher != null) {
                            cipher.setListener(this);
                            myOutput.output("Your cipher key is: " + key);
                            cipher.action(getFileFromUser(), key, true);
                            returnToSwitch = false;
                        }
                        break;

                    case DECRYPT:
                        switch (cipherOption = chooseOption()) {
                            case CAESAR:
                            case XOR:
                            case MULT:
                                cipher = CipherFactory.getCipher(cipherOption);
                                break;
                            case REV:
                                cipher = chooseRevOption();
                                break;
                            default: {
                                myOutput.output("Incorrect cipher choice");
                                returnToSwitch = true;
                            }
                        }
                        if (cipher != null) {
                            myOutput.output("Enter your decryption key");
                            key = getKey(cipherOption);
                            cipher.setListener(this);
                            cipher.action(getFileFromUser(), key, false);
                            returnToSwitch = false;
                        }
                        break;
                }
            } while (returnToSwitch);
        } catch (Exception e) {
            myOutput.output("Something went wrong... " + e.getMessage() + " Please try again");
        }
    }

    private int getKey(int chooser) throws InvalidCipherKeyException {
        try {
            int key;
            key = Integer.valueOf( myInput.input() );
            if (chooser == MULT && (key % 2 == 0))
                throw new InvalidCipherKeyException("Key for Multiplication Cipher must be an odd number");
            if(key == 0)
                throw new InvalidCipherKeyException("Key cannot be zero");
            return key;
        }
        catch (NumberFormatException e){
            throw new InvalidCipherKeyException(e.getMessage());
        }
    }

    private File getFileFromUser() {
        myOutput.output("Enter the path of the file you want to work with: ");
        String filePath = myInput.input();
        while (!FileManipulator.isValidFile(filePath) || filePath.isEmpty()) {
            myOutput.output("The path you entered seems to be invalid or nonexistent. Please retry: ");
            filePath = myInput.input();
        }
        return new File(filePath);
    }

    @Override
    public void started() {
        myOutput.output("Started at: "+ System.nanoTime());
    }

    @Override
    public void finished() {
        myOutput.output("Finished at: "+ System.nanoTime());
    }

    @Override
    public void output(String s) {
            System.out.println(s);
    }

    @Override
    public String input() {
        BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
