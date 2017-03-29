package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Menu {
    static Input myInput;
    static Output myOutput;

    /*public Menu() {
        myInput = new Input() {
            @Override
            public String input() {
                return Menu.scan();
            }
        };
        myOutput = new Output() {
            @Override
            public void output(String s) {
                    System.out.println(s);
            }
        };
    }*/
    static String scan() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    static void runProg() {
        String input;
        myOutput.output("Welcome! Press 1 to encrypt the file, 2 to decrypt the file, 0 to exit: ");
        if(!(input = myInput.input()).equals("0")){
            mainMenu(input);
            runProg();
            return;
        }
        myOutput.output("Exiting application");
    }

    static void mainMenu(String input) {
        int key;
        FileOperation fileOperation = new CaesarCipher();
        try {
            switch (input) {
                case "1":
                    Random random = new Random(System.currentTimeMillis());
                    key = random.nextInt() % 255;
                    myOutput.output("Your Caesar Cipher key is: " + key);
                    fileOperation.encrypt(getFileFromUser(), key);
                    myOutput.output("Encrypted");
                    //answer = "Encrypted";
                    break;
                case "2":
                    myOutput.output("Enter your Caesar Cipher key:");
                    key = Integer.parseInt(myInput.input());
                    fileOperation.decrypt(getFileFromUser(),key);
                    myOutput.output("Decrypted");
                    //answer = "Decrypted";
                    break;
                default:
                    myOutput.output("Your choice seems to be invalid. Please try again: ");
                    return;
            }
            //myOutput.output("Done");
        } catch (Exception e) {
            myOutput.output("Something went wrong... Please try again");
        }
    }

    static File getFileFromUser() {
        myOutput.output("Enter the path of the file you want to work with: ");
        String filePath = myInput.input();
        while (!FileManipulator.isValidFile(filePath) || filePath.isEmpty()) {
           myOutput.output("The path you entered seems to be invalid or nonexistent. Please retry: ");
           filePath = myInput.input();
        }
        return new File(filePath);
    }
}
