package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Appointment.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRep extends JpaRepository<Agreement, Long> {
}
