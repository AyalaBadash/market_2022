package com.example.server.businessLayer.Market.Appointment.Permissions;

import com.example.server.businessLayer.Market.Shop;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PurchaseHistoryPermission")
public class PurchaseHistoryPermission extends IPermission<String> {

    public PurchaseHistoryPermission() { this.name = "PurchaseHistoryPermission"; }

    @Override
    public String apply(Shop relatedShop) {
        return null;
    }

    @Override
    public boolean isPermission(String permission) {
        return permission == name;
    }
}
