package com.company;
import java.io.*;
import java.util.Random;


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
            if (action.equals(ENCRYPT) || action.equals(DECRYPT)) {
                try {
                    do {
                        switch (DoubleOption()) {
                            case XorReverse:
                                cipher = new DoubleCipher(new XORCipher(), new ReverseCipher(chooseRevOption()),
                                        setKey(false), setKey(false));
                                break;
                            case CaesarMult:
                                cipher = new DoubleCipher(new CaesarCipher(), new MultiplicationCipher(),
                                        setKey(false), setKey(true));
                                break;
                            default: {
                                myOutput.output("Invalid option");
                                returnToSwitch = true;
                            }
                        }
                        if (cipher != null) {
                            cipher.action(getFileFromUser(), 0, action.equals(ENCRYPT));
                            returnToSwitch = false;
                        }
                    } while (returnToSwitch);
                } catch (Exception e) {
                    myOutput.output("Something went wrong... " + e.getMessage() + " Please try again");
                }
            }
        }
    }
    //private int setKey () throws InvalidFileException {return setKey(false);}
    private int setKey (boolean mult) throws InvalidFileException {
        return makeIntKey(getFileFromUser(true),mult);
    }
    private int getKey1() throws InvalidFileException {
        Serializable key = FileManipulator.readObjectFromFile (new File("C:\\Users\\hackeru.HACKERU3\\Desktop\\Keys\\key1.bin"));
        return (Integer) key;
    }

    private int getKey2() throws InvalidFileException {
        Serializable key = FileManipulator.readObjectFromFile (new File("C:\\Users\\hackeru.HACKERU3\\Desktop\\Keys\\key2.bin"));
        return (Integer) key;
    }

    private int makeIntKey (File file, boolean mult) throws InvalidCipherKeyException, InvalidFileException {
        int key = new IntKey().getKey();
            if (mult && (key % 2 == 0))
                key = key + 1;
            FileManipulator.writeObjectToFile(file, key);
            return key;
    }
    private File getFileFromUser() {
        return getFileFromUser(false);
    }
    private File getFileFromUser(boolean key) {
        if(!key)
            myOutput.output("Enter the path of your file: ");
        else
            myOutput.output("Enter the path of the file you want to save your key in: ");
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
