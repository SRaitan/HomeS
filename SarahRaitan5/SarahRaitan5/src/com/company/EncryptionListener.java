package com.company;

import com.company.InputOutput.UserInput;
import com.company.InputOutput.UserOutput;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raitan on 3/29/2017.
 */
public class EncryptionListener implements Cipher.CipherListener {
    private long begin;

    @Override
        public void onStarted() {
        begin = System.nanoTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Menu.getInstance().sendToUser("Started: " + dateFormat.format(date));
    }

    @Override
    public void onFinished() {
        Menu.getInstance().sendToUser("Total operation runtime "+ (System.nanoTime() - begin));
    }
}
