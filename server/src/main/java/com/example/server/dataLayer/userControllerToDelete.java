package com.example.server.dataLayer;

import com.example.server.dataLayer.entities.DalUserToDelete;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class userControllerToDelete {

    private SimpleJpaRepository<DalUserToDelete, String> repository;

    public userControllerToDelete(){
        EntityManagerFactory em = Persistence.createEntityManagerFactory("com.example.server.dataLayer.entities");
        EntityManager entityManager = em.createEntityManager();
        repository = new SimpleJpaRepository<DalUserToDelete, String>(
                DalUserToDelete.class, entityManager);
    }

    public void save(DalUserToDelete user){
        repository.save(user);
    }
}
