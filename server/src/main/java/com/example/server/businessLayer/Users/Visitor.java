package com.example.server.businessLayer.Users;

import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.ShoppingCart;

public class Visitor {
    // TODO must be unique
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

    //use case - Register
    public boolean register(String username,String password){throw new UnsupportedOperationException();}

    public boolean login(String username , String password){
        throw new UnsupportedOperationException();}//returns true if login succeeded

    //use case - payment
    public boolean payment(ShoppingCart cart,String paymentDetails){
        throw new UnsupportedOperationException();
    } // return true if payment succeeded
    public void cleanShoppingCart(){
        throw new UnsupportedOperationException();
    }

    //use case - user leaves the market
    public void leaveMarket(){throw new UnsupportedOperationException();}
    public void saveShoppingCart(){throw new UnsupportedOperationException();};

    //use case - receive info of a shop
    public void getShopInfo(String shopID){throw new UnsupportedOperationException();} // TODO - maybe shopID needs to be main.businessLayer.Shop name

    //use case - Save items in shopping cart
    public void addToShoppingCart(Item item, int amount, String shopID) // TODO - think if shopID needed here and if so from where we are getting it
    {throw new UnsupportedOperationException();}

    //use case - Show shopping cart
    public void displayShoppingCart(){throw new UnsupportedOperationException();}

    //use case - Acquisition
    public boolean purchase(){throw new UnsupportedOperationException();
        //cleanShoppingCart();
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
}