package com.example.server.dataLayer.repositories;

import com.example.server.dataLayer.entities.DalItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<DalItem, String> {
}
