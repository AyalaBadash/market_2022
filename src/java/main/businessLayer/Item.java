package main.businessLayer;

import main.serviceLayer.FacadeObjects.ItemFacade;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class Item implements IHistory {

    public enum Category {
        fruit,
        meat,
        //TODO complete
    }

    private Integer ID;
    private String name;
    private double price;
    private String info;
    private Category category;
    private List<String> keywords;
    // TODO ID must be generated in market
    public Item(Integer ID, String name, double price, String info) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.keywords = new CopyOnWriteArrayList<>();
        this.info = info;
    }

    public Item(ItemFacade it){
        this.ID= it.getID();
        this.name= it.getName();
        this.price= it.getPrice();
        this.keywords= it.getKeywords();
        this.info= it.getInfo();
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


    public Integer getID() {
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
    //TODO need to re-implement
    public StringBuilder getReview() {
        return new StringBuilder(this.getName());
    }

    public void addKeyword(String keyword){
        throw new UnsupportedOperationException();
    }
    public void removeKeyword(String keyword){
        throw new UnsupportedOperationException();
    }
    public Category getCategory() {
        return category;
    }

    public List<String> getKeywords() {
        return keywords;
    }

}
