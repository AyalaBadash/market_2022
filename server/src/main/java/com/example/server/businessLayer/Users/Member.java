package com.example.server.businessLayer.Users;


import com.example.server.ResourcesObjects.EventLog;
import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Member {
    private String name;
    private ShoppingCart myCart;
    private List<Appointment> appointedByMe;
    private List<Appointment> myAppointments;
    private List<ShoppingCart> purchaseHistory;


    public Member(String name) throws MarketException {
        if(name.charAt ( 0 ) == '@')
            throw new MarketException ( "cannot create a member with a username starts with @" );
        this.name = name;
        myCart = new ShoppingCart();
        appointedByMe = new CopyOnWriteArrayList<>();
        myAppointments = new CopyOnWriteArrayList<>();
        purchaseHistory = new ArrayList<> (  );
    }

    public Member(String name, ShoppingCart shoppingCart, List<Appointment> appointmentedByME, List<Appointment> myAppointments, List<ShoppingCart> purchaseHistory ){
        this.name = name;
        myCart = shoppingCart;
        this.appointedByMe = appointmentedByME;
        this.myAppointments = myAppointments;
        this.purchaseHistory = purchaseHistory;
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

    public void addAppointmentToMe(Appointment app){ this.myAppointments.add(app);}

    public StringBuilder getPurchaseHistoryString() {
        StringBuilder history = new StringBuilder ( String.format ( "%s:\n", name ) );
        int i = 1;
        for(ShoppingCart shoppingCart : purchaseHistory){
            history.append ( String.format ( "purchase %d:\n%s", i, shoppingCart.getReview () ));
            i++;
        }
        EventLog eventLog = EventLog.getInstance();
        eventLog.Log("Pulled "+this.getName()+" history");
        return history;
    }

    public List<ShoppingCart> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void savePurchase(ShoppingCart cart) {
        purchaseHistory.add ( cart );
    }
}
