package com.example.server.businessLayer.Market.Appointment;

import com.example.server.businessLayer.Market.Appointment.Permissions.ApproveBidPermission;
import com.example.server.businessLayer.Market.Appointment.Permissions.EmployeesPermission;
import com.example.server.businessLayer.Market.Appointment.Permissions.IPermission;
import com.example.server.businessLayer.Market.Appointment.Permissions.PurchaseHistoryPermission;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Shop;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.serviceLayer.FacadeObjects.AppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopManagerAppointmentFacade;
import com.example.server.serviceLayer.FacadeObjects.ShopOwnerAppointmentFacade;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "appointment_type",
        discriminatorType = DiscriminatorType.STRING
)
@SequenceGenerator(name="seq", initialValue=1, allocationSize=100)
public abstract class
Appointment {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long id;
    @OneToOne (cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Member appointed;       //  the actual appointed member
    @OneToOne (cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Member superVisor;      //  member appointedMe
//    @OneToOne (cascade = {CascadeType.MERGE})
    @Transient
    private Shop relatedShop;
    //TODO - needs to be not an object? :O
    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<IPermission> permissions;

    public Appointment(Member appointed, Member appoint, Shop relatedShop) {
        this.appointed = appointed;
        this.superVisor = appoint;
        this.relatedShop = relatedShop;
        permissions= new CopyOnWriteArrayList<>();
        addAllPermissions();
    }

    public Appointment(){}
    public Appointment(Member appointed, Member superVisor, Shop relatedShop, List<IPermission> permissions) {
        this.appointed = appointed;
        this.superVisor = superVisor;
        this.relatedShop = relatedShop;
        this.permissions = permissions;
    }

    public void addAllPermissions(){
        permissions.add(new PurchaseHistoryPermission());
        permissions.add(new EmployeesPermission());
        permissions.add ( new ApproveBidPermission() );
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

    public StringBuilder getShopInfo() throws MarketException {
        if (relatedShop.isClosed() && !isOwner())
            throw new MarketException("only shop owners and founders can get close shop info");
        return relatedShop.getReview ();
    }

    public boolean hasPermission(String permission) {
        for( IPermission p : permissions ){
            if (p.isPermission ( permission ))
                return true;
        }
        return false;
    }



    public abstract void setPermissions(List<IPermission> permissions) ;
    public abstract AppointmentFacade visitToFacade(ShopOwnerAppointmentFacade shopOwnerAppointmentFacade);
    public abstract  AppointmentFacade visitToFacade(ShopManagerAppointmentFacade shopManagerAppointmentFacade);
}