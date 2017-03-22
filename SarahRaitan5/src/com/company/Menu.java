package com.company;
import java.io.*;


public class Menu implements Cipher.CipherListener, Input,Output{
    //region constValues
    static final String EXIT= "0";
    static final String ENCRYPT = "1";
    static final String DECRYPT = "2";
    static final int XorReverse = 1;
    static final int CaesarMult = 2;

    boolean mult;
    private Input myInput;
    private Output myOutput;

    public Menu(Input myInput, Output myOutput) {
        this.mult = false;
        this.myInput = myInput;
        this.myOutput = myOutput;
    }

    //endregion
    private int DoubleOption(){
        myOutput.output("Choose your algorithms:" +
                "   1 - XOR Cipher & ReverseCipher" +
                "   2 - CaesarCipher & MultiplicationCipher");
        return Integer.valueOf(myInput.input());
    }
    private Cipher chooseRevOption(){
        boolean returnToSwitch = false;
            myOutput.output("Choose your algorithm to reverse:" +
                    "   1-Caesar Cipher " +
                    "   2-XOR Cipher" +
                    "   3-Multiplication Cipher");
            int chooser;
            switch (chooser = Integer.valueOf(myInput.input())) {
                case 1: case 2: case 3:
                    return CipherFactory.getReverseCipher(chooser);
                default: {
                    myOutput.output("Invalid algorithm choice");
                    return chooseRevOption();
                }
            }
    }
    void mainMenu() {
        String action;
        Cipher cipher = null;
        boolean returnToSwitch = false;
        while (true) {
            myOutput.output("Welcome! Press 1 to encrypt a file, 2 to decrypt a file, 0 to exit: ");
            if ((action = myInput.input()).equals(EXIT))
                return;
            try {
            do {
                switch (action) {
                    case ENCRYPT:
                        switch (DoubleOption()) {
                            case XorReverse:
                                myOutput.output("You chose XOR & Reverse");
                                Cipher forReverse = chooseRevOption();
                                cipher = new DoubleCipher(new XORCipher(), new ReverseCipher(forReverse), setKey(false), setKey(false));
                                break;
                            case CaesarMult:
                                myOutput.output("You chose Caesar & Multiply");
                                mult = true;
                                cipher = new DoubleCipher(new CaesarCipher(), new MultiplicationCipher(), setKey(false),setKey(true));
                                break;
                            default: {
                                myOutput.output("Incorrect choice");
                                returnToSwitch = true;
                            }
                        }
                        if (cipher != null) {
                            cipher.action(getFileFromUser(),0,true);
                            returnToSwitch = false;
                        }
                        break;

                    case DECRYPT:
                        switch (DoubleOption()) {
                            case XorReverse:
                                myOutput.output("You chose XOR & Reverse");
                                Cipher forReverse = chooseRevOption();
                                cipher = new DoubleCipher(new XORCipher(), new ReverseCipher(forReverse),getKey(false), getKey(false));
                                break;
                            case CaesarMult:
                                myOutput.output("You chose Caesar & Multiply");
                                cipher = new DoubleCipher(new CaesarCipher(), new MultiplicationCipher(), getKey(false), getKey(true));
                                mult = true;
                                break;
                            default: {
                                myOutput.output("Incorrect cipher choice");
                                returnToSwitch = true;
                            }
                        }
                        if (cipher != null) {
                            cipher.action(getFileFromUser(),0,false);
                            cipher.setListener(this);
                            returnToSwitch = false;
                        }
                        break;
                }
            } while (returnToSwitch);
        } catch (Exception e) {
            myOutput.output("Something went wrong... " + e.getMessage() + " Please try again");
        }
            }
        }

    private int setKey (boolean mult) throws InvalidFileException {
        int key=makeIntKey(getFileFromUser(true),mult);
        myOutput.output(key+"key");
        return key;
    }
    private int getKey(boolean mult) throws InvalidFileException {
        Serializable key = FileManipulator.readObjectFromFile (getFileFromUser(true));
        if(key instanceof IntKey) {
            IntKey temp = (IntKey) key;
            int value = temp.getKey();
            if (mult && (value % 2 == 0))
                throw new InvalidCipherKeyException("Key for MultiplicationCipher must be odd");
            myOutput.output(value+" decKey");
            return value;
        }
        throw new InvalidFileException("Problem reading cipher key from file");
    }
    private int makeIntKey (File file, boolean mult) throws InvalidCipherKeyException, InvalidFileException {
        IntKey writeableKey = new IntKey();
        int key = writeableKey.makeKey();
            if (mult && (key % 2 == 0))
                writeableKey.setKey(key + 1);
            FileManipulator.writeObjectToFile(file, writeableKey);
            return key;
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
