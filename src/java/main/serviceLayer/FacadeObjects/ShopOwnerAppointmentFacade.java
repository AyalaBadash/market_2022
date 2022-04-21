package main.serviceLayer.FacadeObjects;

import main.businessLayer.Appointment.Appointment;
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


    @Override
    public Appointment toBusinessObject() {
        return new ShopOwnerAppointment(this.appointed,this.superVisor, this.relatedShop,this.isShopFounder);
    }
}
