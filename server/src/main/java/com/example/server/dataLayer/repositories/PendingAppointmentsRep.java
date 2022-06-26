package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Appointment.PendingAppointments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingAppointmentsRep extends JpaRepository<PendingAppointments, Long> {
}
