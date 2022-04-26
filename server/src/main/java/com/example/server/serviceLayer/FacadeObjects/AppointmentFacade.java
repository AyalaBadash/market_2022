package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.Users.Member;

import java.util.List;

public abstract class AppointmentFacade implements FacadeObject<Appointment> {
    protected Member appointed;       //  the actual appointed member
    protected Member superVisor;      //  member appointedMe
    protected Shop relatedShop;
    List<PermissionFacade> permissions;

    public AppointmentFacade(Member appointed, Member superVisor, Shop relatedShop,
                             List<PermissionFacade> permissions) {
        this.appointed = appointed;
        this.superVisor = superVisor;
        this.relatedShop = relatedShop;
        this.permissions = permissions;
    }

    public Member getAppointed() {
        return appointed;
    }

    public void setAppointed(Member appointed) {
        this.appointed = appointed;
    }

    public Member getSuperVisor() {
        return superVisor;
    }

    public void setSuperVisor(Member superVisor) {
        this.superVisor = superVisor;
    }

    public Shop getRelatedShop() {
        return relatedShop;
    }

    public void setRelatedShop(Shop relatedShop) {
        this.relatedShop = relatedShop;
    }

    public List<PermissionFacade> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionFacade> permissions) {
        this.permissions = permissions;
    }

    public abstract AppointmentFacade toFacade(ShopManagerAppointment appointment);

    public abstract AppointmentFacade toFacade(ShopOwnerAppointment appointment);

    public abstract AppointmentFacade toFacade(Appointment appointment);
}