package main.businessLayer.Appointment.Permissions;

import main.businessLayer.Shop;

public abstract class IPermission<T> {
    private String name;

    public String getName() {
        return name;
    }

    public abstract T apply(Shop relatedShop);

}
