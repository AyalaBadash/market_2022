package main;

public class Member {
    String name;
    ShoppingCart myCart;
    BuyerState state;

    public Member(String name){
        this.name = name;
    }

    public void logout(){throw new UnsupportedOperationException();}//we need to save shopping cart on logout
}
