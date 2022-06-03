package com.example.server.dataLayer;

import com.example.server.dataLayer.entities.DalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface repositoryToDelete extends JpaRepository<DalUser, String> {
}
