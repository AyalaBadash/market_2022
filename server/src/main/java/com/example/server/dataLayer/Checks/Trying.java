package com.example.server.dataLayer.Checks;

import javax.persistence.*;
import java.util.Map;


public class Trying {
    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable (name = "table_name" ,
        joinColumns = {@JoinColumn(name = "holder_class", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "val_val", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "key_val")
    private Map<ManyTrying, ManyTrying> complex_table;
//
//    @OneToMany
//    @JoinTable (name = "one_many_table",
//        joinColumns = {@JoinColumn})
//    private Map<Integer, ManyTrying> manyTryingMap;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<ManyTrying, ManyTrying> getComplex_table() {
        return complex_table;
    }

    public void setComplex_table(Map<ManyTrying, ManyTrying> complex_table) {
        this.complex_table = complex_table;
    }
}
