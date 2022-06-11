package com.example.server.dataLayer.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Table(name = "owner_appointments")
@IdClass(AppointmentID.class)
public class DalOwnerApp extends DalAppointment{
    @Id
    private String supervisor;
    @Id
    private String appointed;
    @Id
    private String shop;
    private boolean employeePerm;
    private boolean purchaseHistoryPerm;
    private boolean isFounder;
    public DalOwnerApp(){}

    public DalOwnerApp(String supervisor, String appointed, String shop, boolean employeePerm, boolean purchaseHistoryPerm, boolean isFounder) {
        this.supervisor = supervisor;
        this.appointed = appointed;
        this.shop = shop;
        this.employeePerm = employeePerm;
        this.purchaseHistoryPerm = purchaseHistoryPerm;
        this.isFounder = isFounder;
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

    public boolean isEmployeePerm() {
        return employeePerm;
    }

    public void setEmployeePerm(boolean employeePerm) {
        this.employeePerm = employeePerm;
    }

    public boolean isPurchaseHistoryPerm() {
        return purchaseHistoryPerm;
    }

    public void setPurchaseHistoryPerm(boolean purchaseHistoryPerm) {
        this.purchaseHistoryPerm = purchaseHistoryPerm;
    }

    public boolean isFounder() {
        return isFounder;
    }

    public void setFounder(boolean founder) {
        isFounder = founder;
    }
}
