package com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.dataLayer.repositories.AndCompDRep;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
@Entity
@DiscriminatorValue(value = "And")
public class AndCompositeDiscountLevelState extends CompositeDiscountLevelState{
    private static AndCompDRep andCompDRep;
    public AndCompositeDiscountLevelState(List<DiscountLevelState> discountLevelStates) {
        super ( discountLevelStates );
        andCompDRep.save(this);
    }

    public AndCompositeDiscountLevelState(){
        super();
    }

    @Override
    protected Double calculateAllDiscount(double price, List<Double> discounts) throws MarketException {
        double priceAfterDiscount = price;
        for ( Double discount: discounts )
            price -= discount;
        return price;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof AndCompositeDiscountLevelState){
            AndCompositeDiscountLevelState toCompare = (AndCompositeDiscountLevelState) object;
            for( DiscountLevelState discountLevelState: this.discountLevelStates){
                if (!toCompare.discountLevelStates.contains ( discountLevelState ))
                    return false;
            }
            for(DiscountLevelState discountLevelState: toCompare.discountLevelStates){
                if (!this.discountLevelStates.contains ( discountLevelState ))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isAnd(){
        return true;
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(CategoryLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(ItemLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(ShopLevelStateFacade levelStateFacade) {
        return null;
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(AndCompositeDiscountLevelStateFacade levelStateFacade) {
        return levelStateFacade.toFacade ( this );
    }

    @Override
    public DiscountLevelStateFacade visitToFacade(MaxXorCompositeDiscountLevelStateFacade levelStateFacade) {
        return null;
    }

    public static AndCompDRep getAndCompDRep() {
        return andCompDRep;
    }

    public static void setAndCompDRep(AndCompDRep andCompDRep) {
        AndCompositeDiscountLevelState.andCompDRep = andCompDRep;
    }
}
