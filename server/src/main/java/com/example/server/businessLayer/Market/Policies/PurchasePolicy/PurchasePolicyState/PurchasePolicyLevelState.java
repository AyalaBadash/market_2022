package com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import java.util.List;

public interface PurchasePolicyLevelState {
    public boolean isPolicyHeld(ShoppingBasket shoppingBasket, double amount, boolean greater) throws MarketException;
    public List<Double> getAmount(ShoppingBasket shoppingBasket) throws MarketException;
    public boolean equals(Object object);
}
