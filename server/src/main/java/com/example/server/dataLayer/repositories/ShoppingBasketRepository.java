package com.example.server.dataLayer.repositories;

import com.example.server.dataLayer.entities.DalShoppingBasket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingBasketRepository extends JpaRepository<DalShoppingBasket, Integer>
{
}
