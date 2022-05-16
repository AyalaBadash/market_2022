package com.example.server.businessLayer.Policies.Discount;

import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingBasket;

public class PriceCondition extends CompositeCondition{
    private double priceNeeded;

    public PriceCondition(Condition baseCond, CompositeType compositeType, double priceNeeded) {
        super(baseCond, compositeType);
        this.priceNeeded = priceNeeded;
    }

    /**
     *
     * @param shoppingBasket
     * @return price of the shoppingBasket >= priceNeeded
     */
    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        boolean isHeld =  shoppingBasket.getPrice() >= priceNeeded;
        boolean baseHeld = getBaseCond().isDiscountHeld(shoppingBasket);
        switch (compositeType){
            case ORType -> {
                return isHeld || baseHeld;
            }
            case XORType -> {

                return (isHeld && !baseHeld) || (!isHeld && baseHeld);
            }
            case ANDType -> {
                return isHeld && baseHeld;
            }
            default -> {
                throw new MarketException("composite type is not supported");
            }
        }
    }
}
