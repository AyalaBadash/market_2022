package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Publisher.NotificationHandler;
import com.google.gson.Gson;
import org.eclipse.jetty.util.ajax.JSON;

public class Statistics {

    public class StatisticsData{

        private int numOfVisitors;
        private int numOfMembers;
        private int numOfAcquisitions;
        private int numOfShops;
        private int shopClosed;
        private String systemManager;
    }
    private NotificationHandler notificationHandler;
    private StatisticsData data;
    private static Statistics instance=null;

    public static Statistics getInstance(){
        if(instance==null){
            instance=new Statistics();
        }
        return instance;
    }

    private Statistics( ){
        data=new StatisticsData();
        notificationHandler= NotificationHandler.getInstance();
    }
    public static void setInstance(Statistics instance) {
        Statistics.instance = instance;
    }

    public int getNumOfVisitors() {
        return data.numOfVisitors;
    }

    public void incNumOfVisitors() {
        this.data.numOfVisitors ++;
        if(hasManager() && managerLogged()) {
            notificationHandler.sendStatistics(this, data.systemManager);
        }
    }
    public void decNumOfVisitors() {
        this.data.numOfVisitors --;
        if(hasManager() && managerLogged()) {
            notificationHandler.sendStatistics(this, data.systemManager);
        }
    }
    public int getNumOfMembers() {
        return data.numOfMembers;
    }

    public void incNumOfMembers() {
        this.data.numOfMembers ++;
        if(hasManager() && managerLogged()) {
            notificationHandler.sendStatistics(this, data.systemManager);
        }
    }
    public void decNumOfMembers() {
        this.data.numOfMembers --;
        if(hasManager() && managerLogged()) {
            notificationHandler.sendStatistics(this, data.systemManager);
        }
    }
    public int getNumOfAcquisitions() {
        return data.numOfAcquisitions;
    }

    public void incNumOfAcquisitions() {
        this.data.numOfAcquisitions++;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, data.systemManager);
            }
        }catch (Exception e){}
    }
    public void decNumOfAcquisitions() {
        this.data.numOfAcquisitions--;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, data.systemManager);
            }
        }catch (Exception e){}
    }
    public int getNumOfShops() {
        return data.numOfShops;
    }

    public void incNumOfShops() {
        this.data.numOfShops++;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, data.systemManager);
            }
        }catch (Exception e){}
    }
    public void decNumOfShops() {
        this.data.numOfShops--;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, data.systemManager);
            }
        }catch (Exception e){}
    }
    public int getShopClosed() {
        return data.shopClosed;
    }

    public void incShopClosed() {
        this.data.shopClosed ++;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, data.systemManager);
            }
        }catch (Exception e){}
    }
    public void decShopClosed() {
        this.data.shopClosed --;
        try {
            if (hasManager() && managerLogged()) {
                notificationHandler.sendStatistics(this, data.systemManager);
            }
        }catch (Exception e){}
    }

    private boolean hasManager(){
        return (data.systemManager!=null && !data.systemManager.isEmpty());
    }
    private boolean managerLogged(){
        return notificationHandler.isConnected(data.systemManager);
    }
    public void setSystemManager(String manager){
        if(manager!=null && !manager.isEmpty()){
            data.systemManager=manager;
        }
    }

    @Override
    public String toString() {

        Gson gson =new Gson();
        return gson.toJson(data);
    }
}
