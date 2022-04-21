package main.serviceLayer.FacadeObjects;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Appointment.ShopManagerAppointment;
import main.businessLayer.Shop;
import main.businessLayer.users.Member;

import java.util.List;

public abstract class AppointmentFacade {
    private Member appointed;       //  the actual appointed member
    private Member superVisor;      //  member appointedMe
    private Shop relatedShop;
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
}
