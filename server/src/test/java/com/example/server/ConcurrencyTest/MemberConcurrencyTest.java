package com.example.server.ConcurrencyTest;

import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.ResourcesObjects.MarketException;
import com.example.server.businessLayer.ShoppingCart;
import com.example.server.businessLayer.Users.Member;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class MemberConcurrencyTest {
    Member member;
    String name = "moshe";
    ShoppingCart myCart = Mockito.mock(ShoppingCart.class);
    ShoppingCart oldCart = Mockito.mock(ShoppingCart.class);
    List<Appointment> appointedByMe = new ArrayList<> ();
    List<Appointment> myAppointments = new ArrayList<>();
    List<ShoppingCart> purchaseHistory = new ArrayList<>();

    @BeforeEach
    public void initMemberTest() throws MarketException {
        member = new Member(name);
        member.setMyCart(myCart);
        Thread[] threads = new Thread[10];
        for(int i = 0; i < 10; i ++){
            threads[i] = new Thread (  );
        }
    }
}
