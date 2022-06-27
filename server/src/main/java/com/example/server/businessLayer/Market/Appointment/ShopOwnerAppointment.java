package com.example.server.businessLayer.Market.Appointment;

import com.example.server.businessLayer.Market.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.Shop;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.dataLayer.entities.DalManagerApp;
import com.example.server.dataLayer.entities.DalOwnerApp;
import com.example.server.dataLayer.repositories.ShopOwnerAppointmentRep;
import com.example.server.serviceLayer.FacadeObjects.AppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopManagerAppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopOwnerAppointmentFacade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ShopOwnerAppointment")
public class ShopOwnerAppointment extends Appointment {
    private boolean isShopFounder;
    private static ShopOwnerAppointmentRep shopOwnerAppointmentRep;
    //TODO - check appoint is not null unless it is founder
    public ShopOwnerAppointment(Member appointed, Member appoint, Shop relatedShop,
                                boolean isShopFounder) {
        super(appointed, appoint, relatedShop);
        this.isShopFounder = isShopFounder;
        if (!MarketConfig.IS_TEST_MODE) {
            shopOwnerAppointmentRep.save(this);
        }
    }

    public ShopOwnerAppointment(){}

    @Override
    public boolean isManager() {
        return false;
    }

    @Override
    public boolean isOwner() {
        return true;
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

    public static void setShopOwnerAppointmentRep(ShopOwnerAppointmentRep shopOwnerAppointmentRep) {
        ShopOwnerAppointment.shopOwnerAppointmentRep = shopOwnerAppointmentRep;
    }

    public static ShopOwnerAppointmentRep getShopOwnerAppointmentRep() {
        return shopOwnerAppointmentRep;
    }
}
