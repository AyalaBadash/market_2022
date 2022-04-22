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

    @Override
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
