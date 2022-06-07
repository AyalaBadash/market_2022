package com.example.server.businessLayer.Market.Appointment;

import com.example.server.businessLayer.Market.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Market.Shop;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.dataLayer.entities.DalManagerApp;
import com.example.server.dataLayer.entities.DalOwnerApp;
import com.example.server.serviceLayer.FacadeObjects.AppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopManagerAppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopOwnerAppointmentFacade;

public class ShopOwnerAppointment extends Appointment {
    private boolean isShopFounder;
    private DalOwnerApp dalOwnerApp;

    //TODO - check appoint is not null unless it is founder
    public ShopOwnerAppointment(Member appointed, Member appoint, Shop relatedShop,
                                boolean isShopFounder) {
        super(appointed, appoint, relatedShop);
        this.isShopFounder = isShopFounder;
        this.dalOwnerApp = toDalObject();
    }

    @Override
    public boolean isManager() {
        return false;
    }

    @Override
    public boolean isOwner() {
        return true;
    }

    @Override
    public DalOwnerApp toDalObject(){
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
        return new DalOwnerApp(getSuperVisor().getName(),getAppointed().getName(),getRelatedShop().getShopName(),employeesPerm,purchaseHistoryPerm,isShopFounder);
    }

    @Override
    public AppointmentFacade visitToFacade(ShopOwnerAppointmentFacade shopOwnerAppointmentFacade) {
        return shopOwnerAppointmentFacade.toFacade ( this );
    }

    @Override
    public AppointmentFacade visitToFacade(ShopManagerAppointmentFacade shopManagerAppointmentFacade) {
        return shopManagerAppointmentFacade.toFacade ( this );
    }

    public boolean isShopFounder() {
        return isShopFounder;
    }


}
