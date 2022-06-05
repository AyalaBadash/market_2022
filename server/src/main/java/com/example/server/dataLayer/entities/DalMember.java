package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Members")
public class DalMember {
    @Id
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart", referencedColumnName = "id")
    private DalShoppingCart myCart;

    //TODO - Appointments

    @OneToMany(targetEntity = DalAcquisitionHistory.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_name", referencedColumnName = "name")
    private List<DalAcquisitionHistory> purchaseHistory;

    public DalMember(String name, List<DalAcquisitionHistory> purchaseHistory) {
        this.name = name;
        this.purchaseHistory = purchaseHistory;
    }
    public DalMember(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DalAcquisitionHistory> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<DalAcquisitionHistory> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }
}
