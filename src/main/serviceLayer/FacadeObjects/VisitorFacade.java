package main.serviceLayer.FacadeObjects;

import main.businessLayer.ShoppingCart;
import main.businessLayer.users.Member;

public class VisitorFacade {
    private String name;
    private MemberFacade member;
    private ShoppingCartFacade cart;

    public VisitorFacade(String name, MemberFacade member, ShoppingCartFacade cart) {
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
}
