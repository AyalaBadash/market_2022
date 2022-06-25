package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Publisher.NotificationHandler;
import com.google.gson.Gson;
import org.eclipse.jetty.util.ajax.JSON;

public class Statistics {

    private NotificationHandler notificationHandler;
    private static Statistics instance=null;
    private int numOfVisitors;
    private int numOfMembers;
    private int numOfAcquisitions;
    private int numOfShops;
    private int shopClosed;

    private String systemManager;

    public static Statistics getInstance(){
        if(instance==null){
            instance=new Statistics();
        }
        return instance;
    }

    private Statistics( ){
        numOfVisitors=0;
        numOfMembers=0;
        numOfShops=0;
        shopClosed=0;
        numOfAcquisitions =0;
        notificationHandler= NotificationHandler.getInstance();
    }
    public static void setInstance(Statistics instance) {
        Statistics.instance = instance;
    }

    public int getNumOfVisitors() {
        return numOfVisitors;
    }

    public void incNumOfVisitors() {
        this.numOfVisitors ++;
        if(hasManager() && managerLogged()) {
            notificationHandler.sendStatistics(this, systemManager);
        }
    }
    public void decNumOfVisitors() {
        this.numOfVisitors --;
        if(hasManager() && managerLogged()) {
            notificationHandler.sendStatistics(this, systemManager);
        }
    }
    public int getNumOfMembers() {
        return numOfMembers;
    }

    public void incNumOfMembers() {
        this.numOfMembers ++;
        if(hasManager() && managerLogged()) {
            notificationHandler.sendStatistics(this, systemManager);
        }
    }
    public void decNumOfMembers() {
        this.numOfMembers --;
        if(hasManager() && managerLogged()) {
            notificationHandler.sendStatistics(this, systemManager);
        }
    }
    public int getNumOfAcquisitions() {
        return numOfAcquisitions;
    }

    public void incNumOfAcquisitions() {
        this.numOfAcquisitions++;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, systemManager);
            }
        }catch (Exception e){}
    }
    public void decNumOfAcquisitions() {
        this.numOfAcquisitions--;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, systemManager);
            }
        }catch (Exception e){}
    }
    public int getNumOfShops() {
        return numOfShops;
    }

    public void incNumOfShops() {
        this.numOfShops++;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, systemManager);
            }
        }catch (Exception e){}
    }
    public void decNumOfShops() {
        this.numOfShops--;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, systemManager);
            }
        }catch (Exception e){}
    }
    public int getShopClosed() {
        return shopClosed;
    }

    public void incShopClosed() {
        this.shopClosed ++;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, systemManager);
            }
        }catch (Exception e){}
    }
    public void decShopClosed() {
        this.shopClosed --;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, systemManager);
            }
        }catch (Exception e){}
    }

    private boolean hasManager(){
        return (systemManager!=null && !systemManager.isEmpty());
    }
    private boolean managerLogged(){
        return notificationHandler.isConnected(systemManager);
    }
    public void setSystemManager(String manager){
        if(manager!=null && !manager.isEmpty()){
            systemManager=manager;
        }
    }

    @Override
    public String toString() {

        Gson gson =new Gson();
        return gson.toJson(Statistics.getInstance());
    }
}
