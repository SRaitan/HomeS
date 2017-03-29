package com.company;
import java.io.File;

public class Menu {
    static Input input = new UserInput();
    static Output output = new UserOutput();

    static void runProg() {
        String choice;
        while (!(choice = readInput("Welcome! Press 1 to encrypt the file, 2 to decrypt the file, 0 to exit: ")).equals("0")) {
            output.output(mainMenu(choice));
        }
        output.output("Exiting application");
    }

    static String readInput(String instructions) {
        output.output(instructions);
        return input.input();
        /*System.out.println(instructions);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;*/
    }

    static String mainMenu(String input) {
        String answer;
        FileOperation fileOperation;
        try {
            Integer choice = Integer.parseInt(input);
            switch (choice) {
                case 1:
                    fileOperation = new Encryptor();
                    answer = "Encrypted";
                    break;
                case 2:
                    fileOperation = new Decryptor();
                    answer = "Decrypted";
                    break;
                default:
                    return "Your choice seems to be invalid. Please try again: ";
            }
            fileOperation.action(getFileFromUser(readInput("Enter the path of the file you want to work with: ")));
            return answer;
        } catch (Exception e) {
            return "Something went wrong. Please try again";
        }
    }

    static File getFileFromUser(String filePath) {
       /* System.out.println("Enter the path of the file you want to work with: ");
        Scanner scanner = new Scanner(System.in);
        filePath = scanner.next();*/
        while (!FileManipulator.isValidFile(filePath) || filePath.isEmpty()) {
            filePath = readInput("The path you entered seems to be invalid or nonexistent. Please retry: ");
        }
        return new File(filePath);
    }
}
