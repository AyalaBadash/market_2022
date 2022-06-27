package com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.*;

import javax.persistence.*;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "PurchasePolicy_ls",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class PurchasePolicyLevelState {
    @Id
    @GeneratedValue
    private long id;
    public abstract boolean isPolicyHeld(ShoppingBasket shoppingBasket, double amount, boolean greater) throws MarketException;
    public abstract List<Double> getAmount(ShoppingBasket shoppingBasket) throws MarketException;
    public abstract boolean equals(Object object);

    public abstract boolean isItemLevel();
    public abstract boolean isCategoryLevel();
    public abstract boolean isShopLevel();
    public abstract boolean isOrLevel();
    public abstract boolean isXorLevel();
    public abstract boolean isAndLevel();

    public abstract PurchasePolicyLevelStateFacade visitToFacade(ItemPurchasePolicyLevelStateFacade levelStateFacade);
    public abstract  PurchasePolicyLevelStateFacade visitToFacade(CategoryPurchasePolicyLevelStateFacade levelStateFacade);
    public abstract PurchasePolicyLevelStateFacade visitToFacade(ShopPurchasePolicyLevelStateFacade levelStateFacade);

    public abstract  PurchasePolicyLevelStateFacade visitToFacade(AndCompositePurchasePolicyLevelStateFacade levelStateFacade);
    public abstract PurchasePolicyLevelStateFacade visitToFacade(XorCompositePurchasePolicyLevelStateFacade levelStateFacade);
    public abstract PurchasePolicyLevelStateFacade visitToFacade(OrCompositePurchasePolicyLevelStateFacade levelStateFacade);
}
