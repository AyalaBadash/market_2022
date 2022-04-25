package com.example.server.businessLayer.Appointment;

import com.example.server.businessLayer.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.Users.Member;

import java.util.List;

public class ShopManagerAppointment extends Appointment {


    public ShopManagerAppointment(Member appointed, Member appoint, Shop relatedShop) {
        super(appointed, appoint, relatedShop);
    }

    public ShopManagerAppointment(Member appointed, Member superVisor, Shop relatedShop, List<IPermission> permissions) {
        super(appointed, superVisor, relatedShop, permissions);
    }

    @Override
    public boolean isManager() {
        return true;
    }

    @Override
    public boolean isOwner() {
        return false;
    }


    public void removePermission(IPermission permission){
        throw new UnsupportedOperationException();
    }

    public void addPermission(IPermission permission){
        throw new UnsupportedOperationException();
    }




}