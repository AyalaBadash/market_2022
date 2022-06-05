package com.example.server.businessLayer.Publisher;

import com.example.server.serviceLayer.Notifications.Notification;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        if(!messages.containsKey(sessionId)){
            return false;
        }
        if( writeToText(dir, notification.getMessage(), sessionId+messages.get(sessionId)+".txt")){
            int ret=messages.get(sessionId);
            messages.remove(sessionId);
            messages.put(sessionId,ret+1);
            return true;
        }
        else{
            return false;
        }

    }

    private boolean writeToText(String path,String message, String name){
        try {
            FileWriter myWriter = new FileWriter(name);
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
}
