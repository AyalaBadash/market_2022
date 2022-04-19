package main.users;

import main.Item;
import main.ShoppingCart;

public class Visitor {

    private Member member;
    private ShoppingCart cart;

    public Visitor(){

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
    public void getShopInfo(String shopID){throw new UnsupportedOperationException();} // TODO - maybe shopID needs to be main.Shop name

    //use case - Save items in shopping cart
    public void addToShoppingCart(Item item, int amount, String shopID) // TODO - think if shopID needed here and if so from where we are getting it
    {throw new UnsupportedOperationException();}

    //use case - Show shopping cart
    public void displayShoppingCart(){throw new UnsupportedOperationException();}

    //use case - Acquisition
    public boolean purchase(){throw new UnsupportedOperationException();
    //cleanShoppingCart();
    }

}
