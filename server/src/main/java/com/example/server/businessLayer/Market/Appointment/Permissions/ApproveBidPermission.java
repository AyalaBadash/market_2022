package com.example.server.businessLayer.Market.Appointment.Permissions;

import com.example.server.businessLayer.Market.Shop;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ApproveBidPermission")
public class ApproveBidPermission extends IPermission{

    public ApproveBidPermission(){
        this.name = "ApproveBidPermission";
    }
    @Override
    public Object apply(Shop relatedShop) {
        return null;
    }

    @Override
    public boolean isPermission(String permission) {
        return permission.equals(name);
    }
}
