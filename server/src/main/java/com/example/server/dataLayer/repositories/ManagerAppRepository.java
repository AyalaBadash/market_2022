package com.example.server.dataLayer.repositories;

import com.example.server.dataLayer.entities.DalManagerApp;
import com.example.server.dataLayer.entities.AppointmentID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerAppRepository extends JpaRepository<DalManagerApp, AppointmentID> {
}
