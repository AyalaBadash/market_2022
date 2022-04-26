package com.example.server.businessLayer.Users;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingCart;
import com.example.server.serviceLayer.FacadeObjects.ItemFacade;

public class Visitor {
    private String name;
    private Member member;
    private ShoppingCart cart;

    public Visitor(String name) {
        this.name = name;
        this.member = null;
        this.cart = new ShoppingCart();
    }

    public Visitor(String name, Member member, ShoppingCart cart) {
        this.name = name;
        this.member = member;
        this.cart = cart;
    }


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
        return true;
    }
}