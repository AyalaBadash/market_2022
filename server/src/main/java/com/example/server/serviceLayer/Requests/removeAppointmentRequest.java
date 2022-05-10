package com.example.server.serviceLayer.Requests;

public class removeAppointmentRequest {
    String boss;
    String firedAppointed;
    String shopName;


    public removeAppointmentRequest(){

    }

    public removeAppointmentRequest(String boss, String firedAppointed, String shopName){
        this.shopName = shopName;
        this.boss = boss;
        this.firedAppointed = firedAppointed;
    }

    public String getBoss() {
        return boss;
    }

    public String getFiredAppointed() {
        return firedAppointed;
    }

    public String getShopName() {
        return shopName;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public void setFiredAppointed(String firedAppointed) {
        this.firedAppointed = firedAppointed;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
