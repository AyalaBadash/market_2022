package com.example.server.businessLayer.Market.Appointment.Permissions;

import com.example.server.businessLayer.Market.Shop;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "permission_type",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class IPermission<T> {
    @Id
    @GeneratedValue
    private long id;
    protected String name;

    public String getName() {
        return name;
    }

    public abstract T apply(Shop relatedShop);

    public abstract boolean isPermission(String permission);

    public boolean equals(Object o){
        if (o.getClass()==this.getClass())
            return (((IPermission) o).name).equals(this.name);
        return false;
    }
}
