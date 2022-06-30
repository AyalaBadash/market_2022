package com.example.server.businessLayer.Market.Appointment.Permissions;

import com.example.server.businessLayer.Market.Shop;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "EditInventoryPermission")
public class EditInventoryPermission extends IPermission{

    public EditInventoryPermission(){
        this.name = "EditInventoryPermission";
    }
    @Override
    public Object apply(Shop relatedShop) {
        return null;
    }

    @Override
    public boolean isPermission(String permission) {
        return permission.equals ( name );
    }
}
