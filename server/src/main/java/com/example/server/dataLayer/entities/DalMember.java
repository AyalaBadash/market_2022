package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Members")
public class DalMember {
    @Id
    @Column(name = "name")
    private String name;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "shopping_cart", referencedColumnName = "id")
//    private DalShoppingCart myCart;

    private int shoppingCart;

    @OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "appointedName",referencedColumnName = "name")
    private List<DalManagerApp> managerAppsByMe;

    @OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "appointedName",referencedColumnName = "name")
    private List<DalOwnerApp>   ownerAppsByMe;

    @OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "test1",referencedColumnName = "name")
    private List<DalManagerApp> myManagerApps;

    @OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "test2",referencedColumnName = "name")
    private List<DalOwnerApp> myOwnerApps;

    @OneToMany(targetEntity = DalAcquisitionHistory.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_name", referencedColumnName = "name")
    private List<DalAcquisitionHistory> purchaseHistory;

    public DalMember(String name, int myCart, List<DalManagerApp> managerAppsByMe, List<DalOwnerApp> ownerAppsByMe, List<DalManagerApp> myManagerApps, List<DalOwnerApp> myOwnerApps, List<DalAcquisitionHistory> purchaseHistory) {
        this.name = name;
        this.shoppingCart = myCart;
        this.managerAppsByMe = managerAppsByMe;
        this.ownerAppsByMe = ownerAppsByMe;
        this.myManagerApps = myManagerApps;
        this.myOwnerApps = myOwnerApps;
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
