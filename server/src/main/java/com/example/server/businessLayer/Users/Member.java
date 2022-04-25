package com.example.server.businessLayer.Users;

import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingCart;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Member {
    // TODO must be unique
    private String name;
    private ShoppingCart myCart;
    private List<Appointment> appointedByMe;
    private List<Appointment> myAppointments;
    private List<ShoppingCart> purchaseHistory;


    public Member(String name){
        this.name = name;
        myCart = new ShoppingCart();
        appointedByMe = new CopyOnWriteArrayList<>();
        myAppointments = new CopyOnWriteArrayList<>();
        purchaseHistory = new ArrayList<>(  );
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public StringBuilder getPurchaseHistory() {
        StringBuilder history = new StringBuilder ( String.format ( "%s:\n", name ) );
        int i = 1;
        for(ShoppingCart shoppingCart : purchaseHistory){
            history.append ( String.format ( "purcase %d:\n%s", i, shoppingCart.getReview () ));
            i++;
        }
        return history;
    }

    public void savePurchase(ShoppingCart cart) {
        purchaseHistory.add ( cart );
    }

    public boolean updateAmountInCart(int amount, ItemFacade itemFacade, String shopName) throws MarketException {
        myCart.editQuantity(amount,new Item(itemFacade),shopName);
        return true;
    }
}
