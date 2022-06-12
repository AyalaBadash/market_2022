package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Users.UserController;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserControllerRep extends JpaRepository<UserController, Long> {
}
