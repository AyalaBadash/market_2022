package com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import org.springframework.aop.target.LazyInitTargetSource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@DiscriminatorValue(value = "Composite_ls")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "Composite_ls",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class CompositePurchasePolicyLevelState extends PurchasePolicyLevelState {
    @ManyToMany
    List<PurchasePolicyLevelState> purchasePolicyLevelStates;

    public CompositePurchasePolicyLevelState(List<PurchasePolicyLevelState> purchasePolicyLevelStates) {
        this.purchasePolicyLevelStates = purchasePolicyLevelStates;
    }

    public CompositePurchasePolicyLevelState(){}

    public List<Double> getAmount(ShoppingBasket shoppingBasket) throws MarketException {
        List<Double> amounts = new ArrayList<> (  );
        for(PurchasePolicyLevelState purchasePolicyLevelState : purchasePolicyLevelStates){
            amounts.addAll (purchasePolicyLevelState.getAmount ( shoppingBasket ));
        }
        return amounts;
    }

    public List<PurchasePolicyLevelState> getPurchasePolicyLevelStates() {
        return purchasePolicyLevelStates;
    }

    public void setPurchasePolicyLevelStates(List<PurchasePolicyLevelState> purchasePolicyLevelStates) {
        this.purchasePolicyLevelStates = purchasePolicyLevelStates;
    }
}

