package main.resources;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WarningLogger {
    private Logger logger ;
    File loggerFile;

    private static WarningLogger instance;

    private WarningLogger(){
        logger = LogManager.getLogger("warning logger");
        loggerFile = new File(System.getProperty("user.dir")+"/myLog.txt");
    }

    public static WarningLogger getInstance() {
        if (instance == null)
            instance = new WarningLogger();
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
