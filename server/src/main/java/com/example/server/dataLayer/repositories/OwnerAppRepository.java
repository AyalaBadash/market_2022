package com.example.server.dataLayer.repositories;

import com.example.server.dataLayer.entities.AppointmentID;
import com.example.server.dataLayer.entities.DalOwnerApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerAppRepository extends JpaRepository<DalOwnerApp, AppointmentID> {
}
