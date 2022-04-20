package main.businessLayer.Appointment;

import main.businessLayer.Shop;
import main.businessLayer.users.Member;

public class ShopOwnerAppointment extends Appointment {
    public ShopOwnerAppointment(Member appointed, Member appoint, Shop relatedShop) {
        super(appointed, appoint, relatedShop);

    }

    @Override
    public boolean isManager() {
        return false;
    }

    @Override
    public boolean isOwner() {
        return true;
    }


}
