package main.serviceLayer.FacadeObjects;

import main.businessLayer.Appointment.Permissions.IPermission;

public class PermissionFacade {
    private String name;

    public PermissionFacade(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PermissionFacade(IPermission perm){
        this.name = perm.getName();
    }
}
