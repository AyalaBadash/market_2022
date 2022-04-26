package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.Users.Member;

import java.util.ArrayList;
import java.util.List;

public class ShopManagerAppointmentFacade extends AppointmentFacade {


    public ShopManagerAppointmentFacade(){super();}
    public ShopManagerAppointmentFacade(Member appointed, Member superVisor, Shop relatedShop, List<PermissionFacade> permissions) {
        super(appointed, superVisor, relatedShop, permissions);
    }

    @Override
    public AppointmentFacade toFacade(ShopManagerAppointment appointment) {
        return new ShopManagerAppointmentFacade ( appointment );
//        List<IPermission> list = appointment.getPermissions();
//        List<PermissionFacade> facadeList = new ArrayList<>();
//        for (IPermission permission : list) {
//            facadeList.add(new PermissionFacade(permission));
//        }
//        ShopManagerAppointmentFacade shopManager = new ShopManagerAppointmentFacade(appointment.getAppointed(), appointment.getSuperVisor()
//                , appointment.getRelatedShop(), facadeList);
//        return shopManager;
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
        super(appointment.getAppointed(),appointment.getSuperVisor(),appointment.getRelatedShop(), new ArrayList<>());
        permissions.addAll(appointment.getPermissions().stream().map(PermissionFacade::new).toList());
    }

    @Override
    public Appointment toBusinessObject() {
        List<IPermission> busiPermissions = permissions.stream().map(PermissionFacade::toBusinessObject).toList();
        return new ShopManagerAppointment(this.appointed,this.superVisor,this.relatedShop,busiPermissions);
    }
}
