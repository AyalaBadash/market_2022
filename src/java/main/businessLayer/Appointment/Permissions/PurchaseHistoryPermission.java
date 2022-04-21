package main.businessLayer.Appointment.Permissions;

import main.businessLayer.Shop;

public class PurchaseHistoryPermission extends IPermission<String> {

    public PurchaseHistoryPermission() {
    }

    @Override
    public String apply(Shop relatedShop) {
        return null;
    }
}
