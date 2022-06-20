package com.example.server.UnitTests;

import com.example.server.businessLayer.Market.Appointment.Agreement;
import com.example.server.businessLayer.Market.Appointment.PendingAppointments;
import com.example.server.businessLayer.Market.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Users.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class PendingAppointmentsTest {
    PendingAppointments pendingAppointments;
    @Mock
    ShopOwnerAppointment appointment1;
    @Mock
    Agreement agreement1;
    @Mock
    Member appointed1;
    @Mock
    Member supervisor1;

    //----------------------------------------------
    @Mock
    ShopOwnerAppointment appointment2;
    @Mock
    Agreement agreement2;
    @Mock
    Member appointed2;
    @Mock
    Member supervisor2;

    //----------------------------------------------




    @BeforeEach
    public void reset(){
        agreement1 = Mockito.mock(Agreement.class);
        agreement2 = Mockito.mock(Agreement.class);
        appointment1= Mockito.mock(ShopOwnerAppointment.class);
        appointment2= Mockito.mock(ShopOwnerAppointment.class);
        appointed1 = Mockito.mock(Member.class);
        appointed2= Mockito.mock(Member.class);
        supervisor1= Mockito.mock(Member.class);
        supervisor2= Mockito.mock(Member.class);
        Mockito.when(appointed1.getName()).thenReturn("raz");
        Mockito.when(appointed2.getName()).thenReturn("shaked");
        Mockito.when(supervisor1.getName()).thenReturn("ido");
        Mockito.when(supervisor2.getName()).thenReturn("ayala");
        Mockito.when(appointment1.getAppointed()).thenReturn(appointed1);
        Mockito.when(appointment2.getAppointed()).thenReturn(appointed2);
        Mockito.when(appointment1.getSuperVisor()).thenReturn(supervisor1);
        Mockito.when(appointment2.getSuperVisor()).thenReturn(supervisor2);
    }
    @Test
    @DisplayName("Add appointment test")
    public void addAppointmentTest(){
        List<String> owners = new ArrayList<>();
        owners.add("ido");owners.add("ayala");
        try {
            pendingAppointments.addAppointment(appointed1.getName(),appointment1,owners);
        } catch (MarketException e) {
            assert false;
        }
    }
}
