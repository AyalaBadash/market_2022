package com.example.server.dataLayer.entities;

import java.io.Serializable;

public class AppointmentID implements Serializable {
    private String supervisor;
    private String appointed;
    private String shop;

    public AppointmentID(){}

    public AppointmentID(String supervisor, String appointed, String shop) {
        this.supervisor = supervisor;
        this.appointed = appointed;
        this.shop = shop;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getAppointed() {
        return appointed;
    }

    public void setAppointed(String appointed) {
        this.appointed = appointed;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}
