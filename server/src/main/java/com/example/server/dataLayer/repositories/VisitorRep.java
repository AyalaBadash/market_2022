package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Users.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRep extends JpaRepository<Visitor, String> {
}
