package com.example.server.dataLayer.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class KeywordId implements Serializable {
    private String keyword;
    private int item;

    public KeywordId(String keyword, int item) {
        this.keyword = keyword;
        this.item = item;
    }
    public KeywordId(){}

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }
}
