package com.company;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuTest {
    static int outputCount = 0;
    static Input input;
    static Output output;

    @BeforeEach
    void setUp() {
        System.out.println("In setup");
        input = mock (Input.class);
        output = mock (Output.class);
        Menu.myOutput = output;
        Menu.myInput = input;
    }

    @AfterEach
    void tearDown() {
        System.out.println("In teardown");
    }

   @Test
    void forStringInput() {
        when(input.input()).thenReturn("a").thenReturn("0");
        Menu.runProg();
        verify (output).output("Your choice seems to be invalid. Please try again: ");
    }
   @Test
    void forBlankInput() {
        when(input.input()).thenReturn("").thenReturn("0");
        Menu.runProg();
        verify(output).output("Your choice seems to be invalid. Please try again: ");
    }

    @Test
    void checkCorrectInput() {
        when(input.input()).thenReturn("1").thenReturn("C:\\Users\\hackeru.HACKERU3\\Downloads\\ForCaesar\\myFile.txt").thenReturn("0");
        Menu.runProg();
        verify(output).output("Enter the path of the file you want to work with: ");
    }
     @Test
    void forIncorrectFilepath() {
        when(input.input()).thenReturn("2").thenReturn("ghfdfdhfdf").thenReturn("0");
        Menu.runProg();
        verify(output).output("Something went wrong... Please try again");
    }
    @Test
    void testExit() {
        when(input.input()).thenReturn("0");
        Menu.runProg();
        verify(output).output("Exiting application");
    }
    @Test
    void InvalidPath() {
        FileManipulator  file = new FileManipulator ();
        if (file.isValidFile("iAmAStringNotAPath"))
            Assertions.fail("Not a path");
    }

    @Test
    void NotFile() {
        FileManipulator  file = new FileManipulator ();
        if (file.isValidFile("C:\\Users\\hackeru\\Documents"))
            Assertions.fail("Not a file");
    }

    @Test
    void NonexistentPath() {
        FileManipulator  file = new FileManipulator ();
        if (file.isValidFile("C:\\Users\\hackeru\\Documents\\fghhj"))
            Assertions.fail("Not a file");
    }

    @Test
    void ForValidFile() {
        FileManipulator  file = new FileManipulator ();
        if ((file.isValidFile("C:\\Users\\hackeru.HACKERU3\\Downloads\\ExFeb19\\textT.txt")) == false)
            Assertions.fail("Returns false for valid file");
    }

    @Test
    void forEncryption() {
        when(input.input()).thenReturn("1").thenReturn("C:\\Users\\hackeru.HACKERU3\\Downloads\\ForCaesar\\myFile.txt").thenReturn("0");
        Menu.runProg();
        File file = new File("\"C:\\Users\\hackeru.HACKERU3\\Downloads\\ForCaesar\\myFile_encrypted.txt\"");
        if(file.exists())
            Assertions.fail("Encryption file was not created");
    }

/*    @Test
    void menuTest7() {
        when(input.input()).thenReturn("2").thenReturn("C:\\Users\\hackeru.HACKERU3\\Downloads\\ForCaesar\\myFile.txt").thenReturn("1").thenReturn("0");
        Menu.runProg();
        File file = new File("C:\\Users\\hackeru.HACKERU3\\Downloads\\ForCaesar\\myFile_encrypted_decrypted.txt");
        if(file.exists())
            Assertions.fail("Decryption file was not created");
    }*/

/*    @Test
    void menuTest8() {
        when(input.input()).thenReturn("2").thenReturn("C:\\Users\\hackeru.HACKERU3\\Downloads\\ForCaesar\\myFile.txt").thenReturn("a");
        Menu.runProg();
        File file = new File("C:\\Users\\hackeru.HACKERU3\\Downloads\\ForCaesar\\myFile_encrypted_decrypted.txt");
        verify(output).output("you not enter num" );
    }*/
}
//region oldtest
   /* @Test
    void testMain() {
        //for io testing
        Input mockInput = mock(Input.class);
        Output mockOutput = mock(Output.class);
        when(mockInput.input()).thenReturn("1").thenReturn("C:\\Users\\hackeru.HACKERU3\\Downloads\\ForCaesar\\myFile.txt").thenReturn("0");
        InOrder orderedOutput = inOrder(mockOutput);
        Menu menu = new Menu();
        menu.runProg();
        orderedOutput.verify(mockOutput).output("Welcome! Press 1 to encrypt the file, 2 to decrypt the file, 0 to exit: ");
        orderedOutput.verify(mockOutput).output(anyObject()); //"Your Caesar Cipher key is:  random_num"
        orderedOutput.verify(mockOutput).output("Encrypted");

       *//* Output fakeOutput = new Output() {
            @Override
            public void output(String s) {
                switch (outputCount){
                    case 0:
                        assertEquals("Welcome! Press 1 to encrypt the file, 2 to decrypt the file, 0 to exit: ", s);
                        break;
                    case 1:
                        assertEquals("Enter the path of the file you want to work with: ", s);
                        break;
                    case 2:
                        assertEquals("Enter the path of the file you want to work with: ", s);
                        break;
                }
                outputCount++;
            }
        };*//*
    }

    @Test
    void fileInputTesting() {
        //for filepath testing

    }*/
    //endregion
