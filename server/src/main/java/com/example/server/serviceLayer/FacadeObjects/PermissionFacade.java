package com.example.server.serviceLayer.FacadeObjects;


import com.example.server.businessLayer.Market.Appointment.Permissions.EmployeesPermission;
import com.example.server.businessLayer.Market.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Market.Appointment.Permissions.PurchaseHistoryPermission;

public class PermissionFacade implements FacadeObject{
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
        if(name.equals ( "get_employees_info" ))
            return new EmployeesPermission ();
        return new PurchaseHistoryPermission ();
    }
}