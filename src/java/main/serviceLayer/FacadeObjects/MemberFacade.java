package main.serviceLayer.FacadeObjects;

import main.businessLayer.Appointment.ShopManagerAppointment;
import main.businessLayer.Appointment.ShopOwnerAppointment;
import main.businessLayer.ShoppingCart;
import main.businessLayer.users.Member;

import java.util.List;

public class MemberFacade implements FacadeObject<Member> {
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

    public MemberFacade(Member member) {
        this.name = member.getName();
        this.myCart = member.getMyCart();
        this.appointedByMe = member.getAppointedByMe().stream().map((appointment ->
                appointment.isManager() ?
                        new ShopManagerAppointmentFacade((ShopManagerAppointment) appointment) :
                        new ShopOwnerAppointmentFacade((ShopOwnerAppointment) appointment))).toList();
        this.myAppointments = member.getMyAppointments().stream().map((appointment ->
                appointment.isManager() ?
                        new ShopManagerAppointmentFacade((ShopManagerAppointment) appointment) :
                        new ShopOwnerAppointmentFacade((ShopOwnerAppointment) appointment))).toList();
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

    @Override
    public Member toBusinessObject() {
        return null;
    }
}
