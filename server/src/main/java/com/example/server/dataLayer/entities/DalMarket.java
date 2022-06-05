package com.example.server.dataLayer.entities;

import javax.persistence.*;

@Entity
@Table(name = "market")
public class DalMarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int marketID;
    private String SysManagerName;

    public DalMarket(){}

    public DalMarket(int marketID, String sysManagerName) {
        this.marketID = marketID;
        SysManagerName = sysManagerName;
    }

    public int getMarketID() {
        return marketID;
    }

    public void setMarketID(int marketID) {
        this.marketID = marketID;
    }

    public String getSysManagerName() {
        return SysManagerName;
    }

    public void setSysManagerName(String sysManagerName) {
        SysManagerName = sysManagerName;
    }
}
