package com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "composite_discount_level_state",
        discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue(value = "compositeDiscountLevelState")
public abstract class CompositeDiscountLevelState extends DiscountLevelState {
    @ManyToMany (cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    protected List<DiscountLevelState> discountLevelStates;

    public CompositeDiscountLevelState(List<DiscountLevelState> discountLevelStates) {
        this.discountLevelStates = discountLevelStates;
    }

    public CompositeDiscountLevelState() {

    }

    @Override
    public double calculateDiscount(ShoppingBasket shoppingBasket, double percentageOfDiscount) throws MarketException {
        double price = shoppingBasket.getPrice ();
        List<Double> discounts = new ArrayList<> (  );
        for(DiscountLevelState discountLevelState: discountLevelStates){
            double priceAfterDiscount = discountLevelState.calculateDiscount ( shoppingBasket, percentageOfDiscount );
            discounts.add (price - priceAfterDiscount);
        }
        return calculateAllDiscount ( price, discounts );
    }

    protected abstract Double calculateAllDiscount(double price, List<Double> discounts) throws MarketException;

    public abstract boolean equals(Object object);

    public List<DiscountLevelState> getDiscountLevelStates() {
        return discountLevelStates;
    }

    public void setDiscountLevelStates(List<DiscountLevelState> discountLevelStates) {
        this.discountLevelStates = discountLevelStates;
    }
}
