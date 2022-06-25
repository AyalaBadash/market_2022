package com.example.server.serviceLayer;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ent {
    @Id
    private String name;

    public Ent(String name) {
        this.name = name;
    }

    public Ent() {
    }
}
