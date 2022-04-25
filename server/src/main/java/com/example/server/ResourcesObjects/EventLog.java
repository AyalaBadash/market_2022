package com.example.server.ResourcesObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EventLog {
    private Logger logger ;
    File loggerFile;

    private static EventLog instance;

    private EventLog(){
        logger = LogManager.getLogger("warning logger");
        loggerFile = new File(System.getProperty("user.dir")+"/myLog.txt");
    }

    public static EventLog getInstance() {
        if (instance == null)
            instance = new EventLog();
        return instance;
    }

    public void Log(String msg){
        try {
            FileWriter fileWriter = new FileWriter(loggerFile,true);
            fileWriter.write(("Level Info : "+ msg)+"\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
