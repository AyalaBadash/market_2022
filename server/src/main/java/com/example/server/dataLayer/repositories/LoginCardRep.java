package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Security.LoginCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginCardRep extends JpaRepository<LoginCard, String> {
}
