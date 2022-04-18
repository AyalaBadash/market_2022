package main;

public class Item {
    private String ID;
    private String name;
    private double weight;
    private double price;
    public Item(String ID,String name,double weight,double price){
        this.ID=ID;
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
}
