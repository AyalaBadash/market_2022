package main.businessLayer;

import main.IHistory;

public class Item  implements IHistory {

    public enum Category {
        fruit,
        meat,
        //TODO complete
    }
    private String ID;
    private String name;
    private double weight;
    private double price;
    private Category category;
    public Item(String ID,String name,double weight,double price){
        this.ID=ID;
        this.name=name;
        this.price=price;
        this.weight=weight;
    }
    public Item(String name,double weight,double price){
        this.name=name;
        this.price=price;
        this.weight=weight;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name:" + this.name + " \tPrice:" + this.price;
    }

    @Override
    public String getReview() {
        return this.getName();
    }
}
