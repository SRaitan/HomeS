package com.company;

/**
 * Created by hackeru on 2/28/2017.
 */
public class Main {
    public static void main(String[] args) {
        //Menu menu = new Menu();
        Input input = new Input() {
            @Override
            public String input() {
                return Menu.scan();
            }
        };
        Output output = new Output() {
            @Override
            public void output(String s) {
                System.out.println(s);
            }
        };
        Menu.myInput = input;
        Menu.myOutput = output;
        Menu.runProg();
    }
}
