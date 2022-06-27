package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Appointment.ShopManagerAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopManagerAppointmentRep extends JpaRepository<ShopManagerAppointment, Long> {
}
