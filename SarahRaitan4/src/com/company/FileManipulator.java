package com.company;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class FileManipulator {

   static boolean isValidFile(String filepath){
        File file = new File (filepath);
        return (file.exists() && file.isFile() && file.canWrite() && file.canRead());
    }
    static void closeFile(InputStream inputStream, OutputStream outputStream){
        if (outputStream != null)
            try {
                outputStream.close();
            } catch (IOException e) {
                e.getMessage();
            }
        if (inputStream != null)
            try {
                inputStream.close();
            } catch (IOException e) {
                e.getMessage();
            }
    }
}
