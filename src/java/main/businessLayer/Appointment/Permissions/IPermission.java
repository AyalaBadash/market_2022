package main.businessLayer.Appointment.Permissions;

import main.businessLayer.Shop;
import main.serviceLayer.FacadeObjects.PermissionFacade;

public abstract class IPermission<T> {
    private String name;

    public String getName() {
        return name;
    }

    public abstract T apply(Shop relatedShop);


}
