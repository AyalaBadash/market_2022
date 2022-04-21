package main.serviceLayer.FacadeObjects;

import main.businessLayer.Shop;
import main.businessLayer.users.Member;

import java.util.List;

public class ShopManagerAppointmentFacade extends AppointmentFacade{
    public ShopManagerAppointmentFacade(Member appointed, Member superVisor, Shop relatedShop, List<PermissionFacade> permissions) {
        super(appointed, superVisor, relatedShop, permissions);
    }
}
