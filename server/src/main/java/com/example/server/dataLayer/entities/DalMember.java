//package com.example.server.dataLayer.entities;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import java.util.List;
//
//@Entity
//@Table(name="Members")
//public class DalMember {
//    @Id
//    private String name;
//
//    @OneToMany
//    private List <DalAcquisitionHistory> purchaseHistory;
//
//    //TODO - Appointments
//    public DalMember(String name, List<DalAcquisitionHistory> purchaseHistory) {
//        this.name = name;
//        this.purchaseHistory = purchaseHistory;
//    }
//    public DalMember(){}
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<DalAcquisitionHistory> getPurchaseHistory() {
//        return purchaseHistory;
//    }
//
//    public void setPurchaseHistory(List<DalAcquisitionHistory> purchaseHistory) {
//        this.purchaseHistory = purchaseHistory;
//    }
//}
