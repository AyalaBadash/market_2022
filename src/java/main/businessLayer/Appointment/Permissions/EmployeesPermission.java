package main.businessLayer.Appointment.Permissions;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Shop;

import java.util.List;

public class EmployeesPermission extends IPermission<List<Appointment>> {


    @Override
    public List<Appointment> apply(Shop relatedShop) {
        return null;
    }
}
