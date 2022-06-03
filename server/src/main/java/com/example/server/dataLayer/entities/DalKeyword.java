package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "keywords")
public class DalKeyword implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String keyword;
}
