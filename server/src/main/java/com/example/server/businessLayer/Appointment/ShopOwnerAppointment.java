package com.example.server.businessLayer.Appointment;

import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.Users.Member;

public class ShopOwnerAppointment extends Appointment {
    private boolean isShopFounder;
    public ShopOwnerAppointment(Member appointed, Member appoint, Shop relatedShop,
                                boolean isShopFounder) {
        super(appointed, appoint, relatedShop);
        this.isShopFounder = isShopFounder;

    }

    @Override
    public boolean isManager() {
        return false;
    }

    @Override
    public boolean isOwner() {
        return true;
    }

    public boolean isShopFounder() {
        return isShopFounder;
    }

}
