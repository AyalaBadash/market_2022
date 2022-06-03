package com.example.server.businessLayer.Market.Appointment.Permissions;

import com.example.server.businessLayer.Market.Shop;

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
