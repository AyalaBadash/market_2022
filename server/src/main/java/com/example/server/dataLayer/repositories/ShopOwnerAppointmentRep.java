package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Appointment.ShopOwnerAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOwnerAppointmentRep extends JpaRepository<ShopOwnerAppointment, Long> {
}
