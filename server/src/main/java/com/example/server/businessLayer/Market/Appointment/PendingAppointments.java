package com.example.server.businessLayer.Market.Appointment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingAppointments {
    private Map<String ,Agreement> agreements;
    private Map<String,ShopOwnerAppointment> appointments; // <AppointedName , appointment>

    public PendingAppointments(Map<String ,Agreement> agreements, Map<String, ShopOwnerAppointment> appointments) {
        this.agreements = agreements;
        this.appointments = appointments;
    }
    public PendingAppointments(){
        this.appointments = new HashMap<>();
        this.agreements = new HashMap<>();
    }

    public void removeAppointment(String appointedName){
        appointments.remove(appointedName);
        agreements.remove(appointedName);
    }

    public void addAppointment(String appointedName, ShopOwnerAppointment appointment, List<String> owners){
        this.appointments.put(appointedName,appointment);
        Agreement agreement = new Agreement(owners);
        this.agreements.put(appointedName,agreement);
    }

    public Map<String, Agreement> getAgreements() {
        return agreements;
    }

    public void setAgreements(Map<String, Agreement> agreements) {
        this.agreements = agreements;
    }

    public Map<String, ShopOwnerAppointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Map<String, ShopOwnerAppointment> appointments) {
        this.appointments = appointments;
    }
}
