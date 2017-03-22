package com.company;

import java.io.FileNotFoundException;

/**
 * Created by Raitan on 3/22/2017.
 */
public class InvalidFileException extends Exception {
    public InvalidFileException(String s) {
        super("There was a problem with your file:" + s);
    }
}
