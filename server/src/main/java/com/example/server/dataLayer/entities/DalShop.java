package com.example.server.dataLayer.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shops")
public class DalShop {
    @Id
    private String name;

}
