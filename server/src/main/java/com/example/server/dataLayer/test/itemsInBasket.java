//package com.example.server.dataLayer.test;
//
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
//@Entity
//public class itemsInBasket {
//    @Id
//    private int items_in_basket_id;
//
//    @ManyToOne
//    @JoinColumn(name = "item_id")
//    private item item;
//
//    @ManyToOne
//    @JoinColumn(name = "basket_id")
//    private basket basket;
//
//    public int getItems_in_basket_id() {
//        return items_in_basket_id;
//    }
//
//    public void setItems_in_basket_id(int items_in_basket_id) {
//        this.items_in_basket_id = items_in_basket_id;
//    }
//
//    public com.example.server.dataLayer.test.item getItem() {
//        return item;
//    }
//
//    public void setItem(com.example.server.dataLayer.test.item item) {
//        this.item = item;
//    }
//
//    public com.example.server.dataLayer.test.basket getBasket() {
//        return basket;
//    }
//
//    public void setBasket(com.example.server.dataLayer.test.basket basket) {
//        this.basket = basket;
//    }
//}
