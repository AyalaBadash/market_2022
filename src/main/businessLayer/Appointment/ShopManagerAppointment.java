package main.businessLayer.Appointment;

import main.businessLayer.Appointment.Permissions.EmployeesPermission;
import main.businessLayer.Appointment.Permissions.IPermission;
import main.businessLayer.Shop;
import main.businessLayer.users.Member;

public class ShopManagerAppointment extends Appointment {


    public ShopManagerAppointment(Member appointed, Member appoint, Shop relatedShop) {
        super(appointed, appoint, relatedShop);
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
