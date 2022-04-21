package main.serviceLayer.FacadeObjects;

import main.businessLayer.Appointment.Permissions.IPermission;

public class PermissionFacade implements FacadeObject<IPermission> {

    private String name;
    public PermissionFacade(IPermission permission){
        this.name = permission.getName();
    }

    public PermissionFacade(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public IPermission toBusinessObject() {
        return null;
    }
}
