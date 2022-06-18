package com.example.server.businessLayer.Market.Users;
import com.example.server.businessLayer.Market.ResourcesObjects.EventLog;
import com.example.server.businessLayer.Market.AcquisitionHistory;
import com.example.server.businessLayer.Market.Appointment.Appointment;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.IHistory;
import com.example.server.businessLayer.Market.ShoppingCart;
import com.example.server.dataLayer.repositories.MemberRep;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@Entity
public class Member implements IHistory {
    @Id
    private String name;
    @OneToOne (cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private ShoppingCart myCart;
    @Transient //todo
    private List<Appointment> appointedByMe;
//    @ManyToMany (cascade = {CascadeType.MERGE})
    @Transient
    private List<Appointment> myAppointments;
    @OneToMany(targetEntity =  AcquisitionHistory.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_name", referencedColumnName = "name")
    private List<AcquisitionHistory> purchaseHistory;
    private static MemberRep memberRep;

    public Member(String name) throws MarketException {
        if(name.charAt ( 0 ) == '@')
            throw new MarketException ( "cannot create a member with a username starts with @" );
        this.name = name;
        myCart = new ShoppingCart();
        ShoppingCart.getShoppingCartRep().save(myCart);
        appointedByMe = new CopyOnWriteArrayList<>();
        myAppointments = new CopyOnWriteArrayList<>();
        purchaseHistory = new ArrayList<> (  );
        memberRep.save(this);
    }

    public Member(String name, ShoppingCart shoppingCart, List<Appointment> appointmentedByME, List<Appointment> myAppointments, List<AcquisitionHistory> purchaseHistory ){
        this.name = name;
        myCart = shoppingCart;
        this.appointedByMe = appointmentedByME;
        this.myAppointments = myAppointments;
        this.purchaseHistory = purchaseHistory;
    }

    public Member(){
    }


    public String getName() {
        return name;
    }

    public void setName(String name) throws MarketException {
        if(name.charAt ( 0 ) == '@')
            throw new MarketException ( "cannot create a member with a username starts with @" );
        this.name = name;
    }

    public ShoppingCart getMyCart() {
        return myCart;
    }

    public void setMyCart(ShoppingCart myCart) {
        this.myCart = myCart;
    }

    public List<Appointment> getAppointedByMe() {
        return appointedByMe;
    }

    public void setAppointedByMe(List<Appointment> appointedByMe) {
        this.appointedByMe = appointedByMe;
    }

    public List<Appointment> getMyAppointments() {
        return myAppointments;
    }

    public void setMyAppointments(List<Appointment> myAppointments) {
        this.myAppointments = myAppointments;
    }

    public void addAppointmentByMe(Appointment app){ this.appointedByMe.add(app);}

    public void addAppointmentToMe(Appointment app){
        this.myAppointments.add(app);
        memberRep.save(this);
    }

    public StringBuilder getPurchaseHistoryString() {
        StringBuilder history = new StringBuilder ( String.format ( "%s:\n", name ) );
        int i = 1;
        for(AcquisitionHistory acquisitionHistory : purchaseHistory){
            history.append ( String.format ( "purchase %d:\n%s", i, acquisitionHistory.toString () ));//TODO - Check if shoppingCart.getReview is same as acq.tostring
            i++;
        }
        EventLog eventLog = EventLog.getInstance();
        eventLog.Log("Pulled "+this.getName()+" history");
        return history;
    }

    public List<AcquisitionHistory> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void savePurchase(AcquisitionHistory acquisitionHistory) {
        purchaseHistory.add (acquisitionHistory);
        memberRep.save(this);
    }

    @Override
    public StringBuilder getReview() {
        StringBuilder history = new StringBuilder ( String.format ( "%s:\n", name ) );
        int i = 1;
        for(AcquisitionHistory acquisitionHistory : purchaseHistory){
            history.append ( String.format ( "purchase %d:\n%s", i, acquisitionHistory.toString () ));//TODO - Check if shoppingCart.getReview is same as acq.tostring
            i++;
        }
        EventLog eventLog = EventLog.getInstance();
        eventLog.Log("Pulled "+this.getName()+" history");
        return history;
    }



    public static void setMemberRep(MemberRep memberRep) {
        Member.memberRep = memberRep;
    }

    public static MemberRep getMemberRep() {
        return memberRep;
    }
}
