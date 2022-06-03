package com.example.server.dataLayer;

import com.example.server.dataLayer.entities.DalUserToDelete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface repositoryToDelete extends JpaRepository<DalUserToDelete, String> {
}
