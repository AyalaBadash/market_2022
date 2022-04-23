package main.businessLayer.Appointment.Permissions;

import main.businessLayer.Shop;
import main.serviceLayer.FacadeObjects.PermissionFacade;

public abstract class IPermission<T> {
    protected String name;

    public String getName() {
        return name;
    }

    public abstract T apply(Shop relatedShop);

    public boolean equals(Object o){
        if (o.getClass()==this.getClass())
            return (((IPermission) o).name).equals(this.name);
        return false;
    }


}
