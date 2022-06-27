package com.example.server.businessLayer.Market.Users;

import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingCart;
//import com.example.server.dataLayer.repositories.VisitorRep;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

//@Entity
public class Visitor {
    @Id
    private String name;
    @OneToOne (cascade = {CascadeType.PERSIST})
    private Member member;
    @OneToOne (cascade = {CascadeType.MERGE})
    private ShoppingCart cart;
//    private static VisitorRep visitorRep;

    public Visitor(String name) throws MarketException {
        if (name == null || name.equals(""))
            throw new MarketException("Name cant be null or empty string");
        this.name = name;
        this.member = null;
        this.cart = new ShoppingCart();
        if (!MarketConfig.IS_TEST_MODE) {
//        ShoppingCart.getShoppingCartRep().save(cart);
//        visitorRep.save(this);
        }
    }

    public Visitor(String name, Member member, ShoppingCart cart) {
        this.name = name;
        this.member = member;
        this.cart = cart;
        if (!MarketConfig.IS_TEST_MODE) {
//        ShoppingCart.getShoppingCartRep().save(cart);
//        visitorRep.save(this);
        }
    }
    public Visitor(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public ShoppingCart getCart() {
        cart.calculate();
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Visitor && ((Visitor) obj).getName().equals(this.name);
    }

    public boolean updateAmountInCart(double amount, Item item, String shopName) throws MarketException {
        cart.editQuantity (amount,item,shopName);
        if (!MarketConfig.IS_TEST_MODE) {
//        visitorRep.save(this);
        }
        return true;
    }

//    public static void setVisitorRep(VisitorRep visitorRep) {
//        Visitor.visitorRep = visitorRep;
//    }
//
//    public static VisitorRep getVisitorRep() {
//        return visitorRep;
//    }
}