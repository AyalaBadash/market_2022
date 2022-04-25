package main.businessLayer.Appointment;

import main.businessLayer.MarketException;
import main.businessLayer.Shop;
import main.businessLayer.users.Member;
import main.businessLayer.Appointment.Permissions.EmployeesPermission;
import main.businessLayer.Appointment.Permissions.IPermission;
import main.businessLayer.Appointment.Permissions.PurchaseHistoryPermission;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Appointment {
    private Member appointed;       //  the actual appointed member
    private Member superVisor;      //  member appointedMe
    private Shop relatedShop;
    List<IPermission> permissions;

    public Appointment(Member appointed, Member appoint, Shop relatedShop) {
        this.appointed = appointed;
        this.superVisor = appoint;
        this.relatedShop = relatedShop;
        permissions= new CopyOnWriteArrayList<>();
        addAllPermissions();
    }

    public Appointment(Member appointed, Member superVisor, Shop relatedShop, List<IPermission> permissions) {
        this.appointed = appointed;
        this.superVisor = superVisor;
        this.relatedShop = relatedShop;
        this.permissions = permissions;
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


    public Map<String, Appointment> getShopEmployeesInfo() throws MarketException {
        EmployeesPermission permission = new EmployeesPermission();
        if (!permissions.contains(permission))
            throw new MarketException("no permission");
        return permission.apply(relatedShop);
    }

    public Shop getShopInfo() throws MarketException {
        if (relatedShop.isClosed() && !isOwner())
            throw new MarketException("only shop owners and founders can get close shop info");
        return relatedShop;
    }

    public boolean hasPermission(String permission) {
        for( IPermission p : permissions ){
            if (p.isPermission ( permission ))
                return true;
        }
        return false;
    }
}
