package com.company;

import com.company.InputOutput.UserInput;
import com.company.InputOutput.UserOutput;

/**
 * Created by hackeru on 2/28/2017.
 */
public class Main {
    public static void main(String[] args) {
        new Menu(new UserInput(), new UserOutput()).DoubleMenu();
    }
}
