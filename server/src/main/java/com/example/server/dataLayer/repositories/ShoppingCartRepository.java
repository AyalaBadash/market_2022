package com.example.server.dataLayer.repositories;

import com.example.server.dataLayer.entities.DalShoppingBasket;
import com.example.server.dataLayer.entities.DalShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Map;


public interface ShoppingCartRepository extends JpaRepository<DalShoppingCart, Integer> {
    @Transactional
    @Modifying
    @Query("update DalShoppingCart c set c.baskets = ?1")
    int updateBaskets(Map<String, DalShoppingBasket> baskets);
}
