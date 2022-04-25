package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Users.Visitor;

public class VisitorFacade implements FacadeObject<Visitor>{
    private String name;
    private MemberFacade member;
    private ShoppingCartFacade cart;

    public VisitorFacade(String name, MemberFacade member, ShoppingCartFacade cart) {
        this.name = name;
        this.member = member;
        this.cart = cart;
    }

    public VisitorFacade(Visitor visitor) {
        this.name = visitor.getName();
        this.member = new MemberFacade(visitor.getMember());
        this.cart = new ShoppingCartFacade(visitor.getCart());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MemberFacade getMember() {
        return member;
    }

    public void setMember(MemberFacade member) {
        this.member = member;
    }

    public ShoppingCartFacade getCart() {
        return cart;
    }

    public void setCart(ShoppingCartFacade cart) {
        this.cart = cart;
    }

    @Override
    public Visitor toBusinessObject() {
        return null;
    }
}
