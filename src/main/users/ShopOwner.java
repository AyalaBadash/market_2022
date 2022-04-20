package main.users;

import main.Shop;

public class ShopOwner implements BuyerState {
    boolean isFounder;
    Shop shop; // TODO- check if it needs to be here and if so , maybe as a list?

    //use case - shop owner appointment
    public boolean appointOwner(){throw new UnsupportedOperationException();}

    //use case - shop manager appointment
    public boolean appointManager(){throw new UnsupportedOperationException();}

    //use case - Edit manager permissions
    public void changePermissions(){throw new UnsupportedOperationException();}

    //use case - close a shop
    public boolean closeShop(Shop shop){
        if(isFounder)
            throw new UnsupportedOperationException();
        throw new UnsupportedOperationException();
    }

    public boolean isFounder() {
        return isFounder;
    }

}
