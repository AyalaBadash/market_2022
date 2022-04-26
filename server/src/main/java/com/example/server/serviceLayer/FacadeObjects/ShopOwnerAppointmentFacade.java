package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.Users.Member;


import java.util.ArrayList;
import java.util.List;

public class ShopOwnerAppointmentFacade extends AppointmentFacade {
    private boolean isShopFounder;

    public ShopOwnerAppointmentFacade(Member appointed, Member superVisor,
                                      Shop relatedShop, List<PermissionFacade> permissions, boolean isShopFounder) {
        super(appointed, superVisor, relatedShop, permissions);
        this.isShopFounder = isShopFounder;
    }

    public ShopOwnerAppointmentFacade(ShopOwnerAppointment appointment) {
        super(appointment.getAppointed(),appointment.getSuperVisor(),appointment.getRelatedShop(), new ArrayList<>());
        permissions.addAll(appointment.getPermissions().stream().map(PermissionFacade::new).toList());
        this.isShopFounder = appointment.isShopFounder();
    }

    public boolean isShopFounder() {
        return isShopFounder;
    }

    public void setShopFounder(boolean shopFounder) {
        isShopFounder = shopFounder;
    }

    public AppointmentFacade toFacade(ShopManagerAppointment appointment) {
        return null;
    }

    public AppointmentFacade toFacade(ShopOwnerAppointment appointment) {
        List<IPermission> myPermissions = appointment.getPermissions ();
        List<PermissionFacade> facadePermissions = new ArrayList<> (  );
        for(IPermission permission : myPermissions){
            facadePermissions.add ( new PermissionFacade ( permission ) );
        }
        return new ShopOwnerAppointmentFacade (appointment.getAppointed (), appointment.getSuperVisor (), appointment.getRelatedShop (), facadePermissions, appointment.isShopFounder () );
    }

    @Override
    public AppointmentFacade toFacade(Appointment appointment) {
        return appointment.visit(this);
    }


    @Override
    public Appointment toBusinessObject() {
        return new ShopOwnerAppointment(this.appointed,this.superVisor, this.relatedShop,this.isShopFounder);
    }
}