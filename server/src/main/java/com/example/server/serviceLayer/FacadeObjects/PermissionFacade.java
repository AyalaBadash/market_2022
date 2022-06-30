package com.example.server.serviceLayer.FacadeObjects;


import com.example.server.businessLayer.Market.Appointment.Permissions.*;

public class PermissionFacade implements FacadeObject{

    public PermissionFacade(){}
    public PermissionFacade(IPermission permission){
        this.name = permission.getName();
    }
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

    @Override
    public IPermission toBusinessObject() {
        if(name.equals ( new EmployeesPermission().getName() )) {
            return new EmployeesPermission ( );
        }else if (name.equals ( new  PurchaseHistoryPermission ().getName ())){
            return new PurchaseHistoryPermission ();
        }else if (name.equals ( new EditInventoryPermission ().getName ())){
            return new EditInventoryPermission ();
        }
        else{
            return new ApproveBidPermission ();
        }

    }
}