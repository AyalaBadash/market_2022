package com.example.server.ResourcesObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorLog {

    private Logger logger ;
    File loggerFile;

    private static ErrorLog instance;

    private ErrorLog(){
        logger = LogManager.getLogger("warning logger");
        loggerFile = new File(System.getProperty("user.dir")+"/myLog.txt");
    }

    public static ErrorLog getInstance() {
        if (instance == null)
            instance = new ErrorLog();
        return instance;
    }

    public void Log(String msg){
        try {
            FileWriter fileWriter = new FileWriter(loggerFile,true);
            fileWriter.write(("Level Error : "+ msg)+"\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
