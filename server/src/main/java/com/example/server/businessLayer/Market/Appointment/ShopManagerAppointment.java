package com.example.server.businessLayer.Market.Appointment;

import com.example.server.businessLayer.Market.Appointment.Permissions.EmployeesPermission;
import com.example.server.businessLayer.Market.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Market.Shop;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.dataLayer.entities.DalAppointment;
import com.example.server.dataLayer.entities.DalManagerApp;
import com.example.server.dataLayer.repositories.ShopManagerAppointmentRep;
import com.example.server.serviceLayer.FacadeObjects.AppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopManagerAppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopOwnerAppointmentFacade;

import javax.persistence.*;
import java.util.List;
@Entity
@DiscriminatorValue(value = "ShopManagerAppointment")
public class ShopManagerAppointment extends Appointment {
    private static ShopManagerAppointmentRep shopManagerAppointmentRep;
    public ShopManagerAppointment(Member appointed, Member appoint, Shop relatedShop) {
        super(appointed, appoint, relatedShop );
        shopManagerAppointmentRep.save(this);
    }

    public ShopManagerAppointment(Member appointed, Member superVisor, Shop relatedShop, List<IPermission> permissions) {
        super(appointed, superVisor, relatedShop, permissions);
        shopManagerAppointmentRep.save(this);
    }
    public ShopManagerAppointment(){}

    @Override
    public boolean isManager() {
        return true;
    }

    @Override
    public boolean isOwner() {
        return false;
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

    public static void setShopManagerAppointmentRep(ShopManagerAppointmentRep shopManagerAppointmentRep) {
        ShopManagerAppointment.shopManagerAppointmentRep = shopManagerAppointmentRep;
    }
}
