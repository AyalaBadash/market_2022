package com.example.server.dataLayer.Checks;

import javax.persistence.*;
import java.util.List;

@Entity
public class ManyTrying {
    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Trying> lst;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Trying> getLst() {
        return lst;
    }

    public void setLst(List<Trying> lst) {
        this.lst = lst;
    }
}
