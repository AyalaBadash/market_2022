package main.serviceLayer.FacadeObjects;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Appointment.Permissions.IPermission;
import main.businessLayer.Appointment.ShopManagerAppointment;
import main.businessLayer.Shop;
import main.businessLayer.users.Member;

import java.awt.geom.IllegalPathStateException;
import java.util.ArrayList;
import java.util.List;

public class ShopManagerAppointmentFacade extends AppointmentFacade {


    public ShopManagerAppointmentFacade(Member appointed, Member superVisor, Shop relatedShop, List<PermissionFacade> permissions) {
        super(appointed, superVisor, relatedShop, permissions);
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
