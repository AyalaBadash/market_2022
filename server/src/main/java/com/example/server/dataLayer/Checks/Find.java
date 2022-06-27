package com.example.server.dataLayer.Checks;

import com.example.server.businessLayer.Market.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Find {
    public static void findEntity(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Item item = em.find(Item.class, 1);
        System.out.println(item.getName());
    }
}
