package com.example.server.dataLayer.repositories;

import com.example.server.dataLayer.entities.DalShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<DalShop, String> {
}
