package com.example.server.businessLayer.Publisher;

import com.example.server.serviceLayer.Notifications.Notification;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TextDispatcher extends Publisher{

    private final String dir;
    private static TextDispatcher textDispatcher=null;

    //Set of the sessions-messages;
    private Map<String, Integer> messages;


    public static TextDispatcher getInstance(){
        if (textDispatcher==null){
            textDispatcher=new TextDispatcher();
        }
        return textDispatcher;
    }
    private TextDispatcher() {
        String dir1;
        try {
            dir1 =new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            dir1 ="\\messages";
        }
        dir = dir1+"\\Users\\Notifications\\";
        messages=new ConcurrentHashMap<>();
    }


    @Override
    public boolean add(String sessionId) {

        if(messages.containsKey(sessionId)){
            return false;
        }
        messages.put(sessionId,1);
        return true;
    }

    @Override
    public List<Notification> remove(String sessionId) {

        messages.remove(sessionId);
         return new ArrayList<>();

    }

    @Override
    public boolean addMessgae(String sessionId, Notification notification) {


        if( writeToText(notification.getMessage()+"\n\n", sessionId+".txt")){
            int ret=messages.get(sessionId);
            messages.remove(sessionId);
            messages.put(sessionId,ret+1);
            return true;
        }
        else{
            return false;
        }

    }

    private boolean writeToText(String message, String name){
        try {
            final File parentDir = new File(getConfigDir());
            parentDir.mkdir();
            final File file = new File(parentDir, name);
            file.createNewFile(); // Creates file crawl_html/abc.txt
            FileWriter myWriter = new FileWriter(file,true);
            myWriter.write(message);
            myWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    public int getSessionNum(){
        return messages.size();
    }
    public void clean(){
        messages.clear();
    }

    private String getConfigDir() {
        String dir = System.getProperty("user.dir").split("/market_2022")[0];
        dir += "/market_2022/server/notifications";
        return dir;
    }
}
