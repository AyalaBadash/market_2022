package com.example.server.dataLayer.repositories;

import com.example.server.dataLayer.entities.DalAcquisition;
import com.example.server.dataLayer.entities.DalShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquisitionRepository extends JpaRepository<DalAcquisition, DalShoppingCart> {
}
