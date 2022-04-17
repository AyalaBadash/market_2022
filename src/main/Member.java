package main;

public class Member {
    String ID;
    String name;
    String password;
    ShoppingCart myCart;
    BuyerState state;

    public void logout(){throw new UnsupportedOperationException();}//we need to save shopping cart on logout
}
