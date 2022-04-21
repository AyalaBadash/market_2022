package main.businessLayer.Appointment;

import main.businessLayer.Shop;
import main.businessLayer.users.Member;
import main.businessLayer.Appointment.Permissions.EmployeesPermission;
import main.businessLayer.Appointment.Permissions.IPermission;
import main.businessLayer.Appointment.Permissions.PurchaseHistoryPermission;

import java.util.List;

public abstract class Appointment {
    private Member appointed;       //  the actual appointed member
    private Member superVisor;      //  member appointedMe
    private Shop relatedShop;
    List<IPermission> permissions;

    public Appointment(Member appointed, Member appoint, Shop relatedShop) {
        this.appointed = appointed;
        this.superVisor = appoint;
        this.relatedShop = relatedShop;
        addAllPermissions();
    }

    public void addAllPermissions(){
        permissions.add(new PurchaseHistoryPermission());
        permissions.add(new EmployeesPermission());
    }

    public List<IPermission> getPermissions() {
        return permissions;
    }


    public Shop getRelatedShop() {
        return relatedShop;
    }

    public void setRelatedShop(Shop relatedShop) {
        this.relatedShop = relatedShop;
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

    public abstract boolean isManager();
    public abstract boolean isOwner();


}
