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
        List<IPermission> list = appointment.getPermissions();
        List<PermissionFacade> facadeList = new ArrayList<>();
        for (IPermission permission : list) {
            facadeList.add(new PermissionFacade(permission));
        }
        ShopManagerAppointmentFacade test = new ShopManagerAppointmentFacade(appointment.getAppointed(), appointment.getSuperVisor()
                , appointment.getRelatedShop(), facadeList);
        return test;
    }




    @Override
    public Appointment toBusinessObject() {
        return new ShopOwnerAppointment(this.appointed,this.superVisor, this.relatedShop,this.isShopFounder);
    }
}