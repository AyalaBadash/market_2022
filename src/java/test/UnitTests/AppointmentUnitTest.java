package test.UnitTests;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Appointment.ShopManagerAppointment;
import main.businessLayer.Shop;
import main.businessLayer.users.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

public class AppointmentUnitTest {

    @Mock
    Member member1 = Mockito.mock(Member.class);
    Member member2 = Mockito.mock(Member.class);
    Shop shop= Mockito.mock(Shop.class);

    @Test
    @DisplayName("ShoppingCart Unit Test - edit cart")
    public void editShoppingCart() throws IllegalAccessException {
        ShopManagerAppointment shopManagerAppointment = new ShopManagerAppointment(member1,member2,shop);
        Mockito.when(member1.getMyAppointments()).thenReturn(new ArrayList<>());
        Mockito.when(member1.getAppointedByMe()).thenReturn(new ArrayList<>());
        Assertions.assertEquals(0,member1.getMyAppointments().size());
        Assertions.assertEquals(0,member2.getAppointedByMe().size());
        member2.addAppointmentByMe(shopManagerAppointment);
        member1.addAppointmentToMe(shopManagerAppointment);
        ArrayList<Appointment> app= new ArrayList<>();
        app.add(shopManagerAppointment);
        Mockito.when(member1.getMyAppointments()).thenReturn(app);
        Mockito.when(member2.getAppointedByMe()).thenReturn(app);
        Assertions.assertEquals(1,member1.getMyAppointments().size());
        Assertions.assertEquals(1,member2.getAppointedByMe().size());
    }
}
