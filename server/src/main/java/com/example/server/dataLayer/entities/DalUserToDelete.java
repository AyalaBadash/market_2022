package com.example.server.dataLayer.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DalUserToDelete {
    @Id
    String id;
    String name;

    public DalUserToDelete(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public DalUserToDelete(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
