package com.company;

import java.io.File;

class FileManipulator {

   static boolean isValidFile(String filepath){
        File file = new File (filepath);
        return (file.exists() && file.isFile() && file.canWrite() && file.canRead());
    }
}
