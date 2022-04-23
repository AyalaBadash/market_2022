package main.businessLayer.Appointment.Permissions;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Shop;

import java.util.List;

public class EmployeesPermission extends IPermission<List<Appointment>> {
    public EmployeesPermission() {
        this.name = "get_employees_info";
    }

    @Override
    public List<Appointment> apply(Shop relatedShop) {
        return relatedShop.getEmployeesList();
    }
}
