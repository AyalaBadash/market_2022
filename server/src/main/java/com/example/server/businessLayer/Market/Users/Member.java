package com.example.server.businessLayer.Market.Users;


import com.example.server.businessLayer.Market.ResourcesObjects.EventLog;
import com.example.server.businessLayer.Market.AcquisitionHistory;
import com.example.server.businessLayer.Market.Appointment.Appointment;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.IHistory;
import com.example.server.businessLayer.Market.ShoppingCart;
import com.example.server.dataLayer.entities.DalAcquisitionHistory;
import com.example.server.dataLayer.entities.DalManagerApp;
import com.example.server.dataLayer.entities.DalMember;
import com.example.server.dataLayer.entities.DalOwnerApp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Member implements IHistory {
    private String name;
    private ShoppingCart myCart;
    private List<Appointment> appointedByMe;
    private List<Appointment> myAppointments;
    private List<AcquisitionHistory> purchaseHistory;

    public Member(String name,int cartID) throws MarketException {
        if(name.charAt ( 0 ) == '@')
            throw new MarketException ( "cannot create a member with a username starts with @" );
        this.name = name;
        myCart = new ShoppingCart(cartID);
        appointedByMe = new CopyOnWriteArrayList<>();
        myAppointments = new CopyOnWriteArrayList<>();
        purchaseHistory = new ArrayList<> (  );
    }

    public Member(String name, ShoppingCart shoppingCart, List<Appointment> appointmentedByME, List<Appointment> myAppointments, List<AcquisitionHistory> purchaseHistory ){
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
    public DalMember toDalObject(){
        List<DalManagerApp> managerAppsByMe = new ArrayList<>();
        List<DalOwnerApp> ownerAppsByMe = new ArrayList<>();
        List<DalManagerApp> myManagerApps = new ArrayList<>();
        List<DalOwnerApp> myOwnerApps = new ArrayList<>();
        List<DalAcquisitionHistory> dalPurchaseHistory = new ArrayList<>();
        for (Appointment app: this.appointedByMe){
            if (app.isManager())
            {

                managerAppsByMe.add((DalManagerApp) app.toDalObject());
            }
            else {
                ownerAppsByMe.add((DalOwnerApp) app.toDalObject());
            }
        }
        for (Appointment app: this.appointedByMe){
            if (app.isManager())
            {
                myManagerApps.add((DalManagerApp) app.toDalObject());
            }
            else {
                myOwnerApps.add((DalOwnerApp) app.toDalObject());
            }
        }
        for (AcquisitionHistory acq : this.purchaseHistory){
            dalPurchaseHistory.add(acq.toDalObject());
        }
        DalMember res = new DalMember(this.name,this.myCart.getId(),managerAppsByMe,ownerAppsByMe,myManagerApps,myOwnerApps,dalPurchaseHistory);
        return res;
    }
}
