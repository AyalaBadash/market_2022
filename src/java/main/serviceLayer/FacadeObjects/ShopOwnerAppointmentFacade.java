package main.serviceLayer.FacadeObjects;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Appointment.Permissions.IPermission;
import main.businessLayer.Appointment.ShopManagerAppointment;
import main.businessLayer.Appointment.ShopOwnerAppointment;
import main.businessLayer.Shop;
import main.businessLayer.users.Member;

import java.util.ArrayList;
import java.util.List;

public class ShopOwnerAppointmentFacade extends AppointmentFacade {
    private boolean isShopFounder;

    public ShopOwnerAppointmentFacade(Member appointed, Member superVisor,
                                      Shop relatedShop, List<PermissionFacade> permissions, boolean isShopFounder) {
        super(appointed, superVisor, relatedShop, permissions);
        this.isShopFounder = isShopFounder;
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


}
