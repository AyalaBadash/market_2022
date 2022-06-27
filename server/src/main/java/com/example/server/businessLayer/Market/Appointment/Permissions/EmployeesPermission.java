package com.example.server.businessLayer.Market.Appointment.Permissions;

import com.example.server.businessLayer.Market.Shop;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Map;
@Entity
@DiscriminatorValue(value = "EmployeesPermission")
public class EmployeesPermission extends IPermission<Map<String, Appointment>> {
    public EmployeesPermission() {
        this.name = "EmployeesPermission";
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