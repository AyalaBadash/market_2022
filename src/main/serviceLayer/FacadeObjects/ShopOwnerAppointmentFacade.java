package main.serviceLayer.FacadeObjects;

import main.businessLayer.Shop;
import main.businessLayer.users.Member;

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
}
