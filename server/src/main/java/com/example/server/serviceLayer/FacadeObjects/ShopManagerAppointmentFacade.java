package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.UserController;

import java.util.ArrayList;
import java.util.List;

public class ShopManagerAppointmentFacade extends AppointmentFacade {


    public ShopManagerAppointmentFacade(){super();}
    public ShopManagerAppointmentFacade(Member appointed, Member superVisor, Shop relatedShop, List<PermissionFacade> permissions) {
        super(appointed, relatedShop, permissions, "ShopManagerAppointmentFacade");
        this.superVisor = superVisor.getName();
    }

    @Override
    public AppointmentFacade toFacade(ShopManagerAppointment appointment) {
        return new ShopManagerAppointmentFacade ( appointment );
    }

    @Override
    public AppointmentFacade toFacade(ShopOwnerAppointment appointment) {
        return null;
    }

    @Override
    public AppointmentFacade toFacade(Appointment appointment) {
        return appointment.visitToFacade ( this );
    }

    public ShopManagerAppointmentFacade(ShopManagerAppointment appointment) {
        super(appointment.getAppointed(), appointment.getRelatedShop(), new ArrayList<>(), "ShopManagerAppointmentFacade");
        this.superVisor = appointment.getSuperVisor().getName();
        permissions.addAll(appointment.getPermissions().stream().map(PermissionFacade::new).toList());
    }

    @Override
    public Appointment toBusinessObject() {
        List<IPermission> busiPermissions = permissions.stream().map(PermissionFacade::toBusinessObject).toList();
        Member appointed = UserController.getInstance().getMember(this.appointed);
        Member superVisor = UserController.getInstance().getMember(this.superVisor);
        Shop relatedShop = Market.getInstance().getShopByName(this.relatedShop);
        return new ShopManagerAppointment(appointed,superVisor,relatedShop,busiPermissions);
    }
}
