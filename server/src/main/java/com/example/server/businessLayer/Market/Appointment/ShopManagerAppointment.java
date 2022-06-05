package com.example.server.businessLayer.Market.Appointment;

import com.example.server.businessLayer.Market.Appointment.Permissions.EmployeesPermission;
import com.example.server.businessLayer.Market.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Market.Shop;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.dataLayer.entities.DalAppointment;
import com.example.server.dataLayer.entities.DalManagerApp;
import com.example.server.serviceLayer.FacadeObjects.AppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopManagerAppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopOwnerAppointmentFacade;

import java.util.List;

public class ShopManagerAppointment extends Appointment {
    public ShopManagerAppointment(Member appointed, Member appoint, Shop relatedShop) {
        super(appointed, appoint, relatedShop );
    }

    public ShopManagerAppointment(Member appointed, Member superVisor, Shop relatedShop, List<IPermission> permissions) {
        super(appointed, superVisor, relatedShop, permissions);
    }

    @Override
    public boolean isManager() {
        return true;
    }

    @Override
    public boolean isOwner() {
        return false;
    }
    @Override
    public DalManagerApp toDalObject(){
        boolean employeesPerm;
        boolean purchaseHistoryPerm;
        if (permissions.size()==0)
        {
            employeesPerm = false;
            purchaseHistoryPerm = false;
        }
        else if (permissions.size()==2)
        {
            employeesPerm = true;
            purchaseHistoryPerm = true;
        }
        else {
            IPermission permission = permissions.get(0);
            if (permission.getName().equals("get_employees_info"))
            {
                employeesPerm = true;
                purchaseHistoryPerm = false;
            }
            else {
                employeesPerm = false;
                purchaseHistoryPerm = true;
            }

        }
        return new DalManagerApp(getSuperVisor().getName(),getAppointed().getName(),getRelatedShop().getShopName(),employeesPerm,purchaseHistoryPerm);
    }

    @Override
    public AppointmentFacade visitToFacade(ShopOwnerAppointmentFacade shopOwnerAppointmentFacade) {
        return shopOwnerAppointmentFacade.toFacade ( this );
    }

    @Override
    public AppointmentFacade visitToFacade(ShopManagerAppointmentFacade shopManagerAppointmentFacade) {
        return shopManagerAppointmentFacade.toFacade ( this );
    }


    public void removePermission(IPermission permission){
        if(permissions.contains ( permission ))
            permissions.remove ( permission );
    }

    public void addPermission(IPermission permission){
        permissions.add ( permission );
    }




}
