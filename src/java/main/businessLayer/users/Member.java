package main.businessLayer.users;

import main.businessLayer.ShoppingCart;
import main.businessLayer.Appointment.Appointment;

import java.util.ArrayList;
import java.util.List;

public class Member {
    // TODO must be unique
    private String name;
    private ShoppingCart myCart;
    private List<Appointment> appointedByMe;
    private List<Appointment> myAppointments;



    public Member(String name){
        this.name = name;
        myCart = new ShoppingCart();
        appointedByMe = new ArrayList<>();
        myAppointments = new ArrayList<>();
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


}
