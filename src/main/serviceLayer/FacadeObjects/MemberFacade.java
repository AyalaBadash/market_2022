package main.serviceLayer.FacadeObjects;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.ShoppingCart;

import java.util.List;

public class MemberFacade {
    private String name;
    private ShoppingCart myCart;
    private List<AppointmentFacade> appointedByMe;
    private List<AppointmentFacade> myAppointments;

    public MemberFacade(String name, ShoppingCart myCart,
                        List<AppointmentFacade> appointedByMe,
                        List<AppointmentFacade> myAppointments) {
        this.name = name;
        this.myCart = myCart;
        this.appointedByMe = appointedByMe;
        this.myAppointments = myAppointments;
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

    public List<AppointmentFacade> getAppointedByMe() {
        return appointedByMe;
    }

    public void setAppointedByMe(List<AppointmentFacade> appointedByMe) {
        this.appointedByMe = appointedByMe;
    }

    public List<AppointmentFacade> getMyAppointments() {
        return myAppointments;
    }

    public void setMyAppointments(List<AppointmentFacade> myAppointments) {
        this.myAppointments = myAppointments;
    }
}
