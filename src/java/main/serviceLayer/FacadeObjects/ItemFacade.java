package main.serviceLayer.FacadeObjects;

import main.businessLayer.Item;

import java.util.List;

public class ItemFacade implements FacadeObject<Item>{
    private String info;
    private Integer ID;
    private String name;
    private double price;
    private Item.Category category;
    private List<String> keywords;


    public ItemFacade(Integer ID, String name, double price,
                      Item.Category category, List<String> keywords,
                      String info) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.category = category;
        this.keywords = keywords;
        this.info = info;

    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Item.Category getCategory() {
        return category;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(Item.Category category) {
        this.category = category;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public Item toBusinessObject() {
        Item item = new Item(this);
        return item;
    }
}
