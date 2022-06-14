package com.example.server.businessLayer.Market.Appointment;

import java.util.HashMap;
import java.util.Map;

public class PendingAppointments {
    private Agreement agreement;
    private Map<String,Appointment> appointments; // <AppointedName , appointment>

    public PendingAppointments(Agreement agreement, Map<String, Appointment> appointments) {
        this.agreement = agreement;
        this.appointments = appointments;
    }
    public PendingAppointments(){
        this.appointments = new HashMap<>();
        this.agreement = new Agreement();
    }

    public boolean isApproved(){
        return agreement.isAgreed();
    }
    public void removeAppointment(String appointedName){
        appointments.remove(appointedName);
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public Map<String, Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Map<String, Appointment> appointments) {
        this.appointments = appointments;
    }

}
