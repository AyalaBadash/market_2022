package com.example.server.businessLayer;


import java.util.List;
import java.util.Objects;

public class Item implements IHistory {

    public enum Category {
        general,
        fruit,
        meat,
        cellular,
        electricity
    }

    private java.lang.Integer ID;
    private String name;
    private double price;
    private String info;
    private int rank;
    private int rankers;
    private Category category;
    private List<String> keywords;
    public Item(java.lang.Integer ID, String name, double price, String info,
                Category category, List<String> keywords) throws MarketException {
        if (ID <1)
            throw new MarketException("Item id must be a positive number");
        this.ID = ID;
        this.name = name;
        if(price < 0)
            throw new MarketException( "price has to be positive" );
        this.price = price;
        this.keywords = keywords;
        this.info = info;
        this.category = Objects.requireNonNullElse(category, Category.general);
        rank= 1;
        rankers=0;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }


    public java.lang.Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    public void setName(String name) {
        this.name = name;
    }

    @Override
    public StringBuilder getReview() {
        return new StringBuilder(String.format ("%s - %f", name, price));
    }

    public void addKeyword(String keyword){
        if (!keywords.contains(keyword))
            keywords.add(keyword);
    }
    public void removeKeyword(String keyword){
        if (keywords.contains(keyword))
            keywords.remove(keyword);

    }
    public Category getCategory() {
        return category;
    }

    public List<String> getKeywords() {
        return keywords;
    }


    public void addRank(int rankN){
        rank=((rank*rankers)+rankN)/(rankers+1);
        rankers++;
    }
    public int getRank(){return rank;}
    public int getRankers(){return rankers;}

    public boolean equals(Item itemToCompare){
        return  itemToCompare.ID == ID;
    }

}
