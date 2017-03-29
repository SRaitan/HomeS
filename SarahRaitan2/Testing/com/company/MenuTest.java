package com.company;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class MenuTest {
    static Input input;
    static Output output;
    @BeforeEach
    void setUp() {
        System.out.println("In setup");
        input = mock (Input.class);
        output = mock (Output.class);
        Menu.output = output;
        Menu.input = input;
    }

    @AfterEach
    void tearDown() {
        System.out.println("In teardown");
    }

    @Test
    void testSimpleIO() {
        String testChoice = " ";
        Assertions.assertEquals(Menu.mainMenu(testChoice), "Something went wrong. Please try again", "assertion failed for space");
        testChoice = "a";
        Assertions.assertEquals(Menu.mainMenu(testChoice), "Something went wrong. Please try again", "assertion failed for letter");
        testChoice = "6";
        Assertions.assertEquals(Menu.mainMenu(testChoice), "Your choice seems to be invalid. Please try again: ", "assertion failed for number except 1, 2");
        testChoice = "\n";
        Assertions.assertEquals(Menu.mainMenu(testChoice), "Something went wrong. Please try again", "assertion failed for 0");

        /*FileOperation o;
        File tester = new File("C:\\Users\\hackeru.HACKERU3\\Downloads\\ExFeb19\\textT.txt");
        Assertions.f(o = new Decryptor().action(tester)), );*/
    }
    @Test
    void InvalidPath_Fail() {
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

   /* @Test
    void validOptionTest() {
        Input input = mock(Input.class);
        when(input.input()).thenReturn("1").thenReturn("C:\\Users\\hackeru.HACKERU3\\Downloads\\ExFeb19\\textT.txt").thenReturn("0");
        Output output = new Output() {
            @Override
            public void output(String s) {
                switch (outputCount) {
                    case 0:
                        assertEquals("Welcome! Press 1 to encrypt the file, 2 to decrypt the file, 0 to exit: ", s);
                        break;
                    case 1:
                        assertEquals("Enter the path of the file you want to work with: ", s);
                        break;
                    case 2:
                        assertEquals("Encrypted", s);
                        break;
                }
                outputCount++;
            }
        };
        Menu.runProg();
    }*/

}