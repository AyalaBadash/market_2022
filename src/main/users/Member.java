package main.users;

import main.ShoppingCart;

public class Member {
    private String name;
    private ShoppingCart myCart;
    private BuyerState state;

    public Member(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BuyerState getState() {
        return state;
    }

    public ShoppingCart getMyCart() {
        return myCart;
    }

    public void logout(){throw new UnsupportedOperationException();}//we need to save shopping cart on logout
}
