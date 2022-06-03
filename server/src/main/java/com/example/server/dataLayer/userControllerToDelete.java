package com.example.server.dataLayer;

import com.example.server.dataLayer.entities.DalUser;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class userControllerToDelete {

    private SimpleJpaRepository<DalUser, String> repository;

    public userControllerToDelete(){
        EntityManagerFactory em = Persistence.createEntityManagerFactory("com.example.server.dataLayer.entities");
        EntityManager entityManager = em.createEntityManager();
        repository = new SimpleJpaRepository<DalUser, String>(
                DalUser.class, entityManager);
    }

    public void save(DalUser user){
        repository.save(user);
    }
}
