package com.example.server.businessLayer.Appointment.Permissions;

import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Shop;

import java.util.Map;

public class EmployeesPermission extends IPermission<Map<String, Appointment>> {
    public EmployeesPermission() {
        this.name = "get_employees_info";
    }

    @Override
    public Map<String, Appointment> apply(Shop relatedShop) {
        return relatedShop.getEmployees ();
    }

    @Override
    public boolean isPermission(String permission) {
        return permission == name;
    }
}