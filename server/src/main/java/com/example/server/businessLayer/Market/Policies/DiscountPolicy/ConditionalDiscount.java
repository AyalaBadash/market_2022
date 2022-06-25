package com.example.server.businessLayer.Market.Policies.DiscountPolicy;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Condition;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.dataLayer.repositories.ConditionalDiscountRep;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.ConditionalDiscountFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.MaxCompositeDiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.SimpleDiscountFacade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "ConditionalDiscount")
public class ConditionalDiscount extends DiscountType{
    @Transient
    private Condition condition;
    private static ConditionalDiscountRep condDiscountRep;

    public ConditionalDiscount(double percentageOfDiscount, DiscountLevelState discountLevelState, Condition condition) {
        super ( percentageOfDiscount, discountLevelState );
        this.condition = condition;
        condDiscountRep.save(this);
    }

    public ConditionalDiscount(){}

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException {
        return condition.isDiscountHeld(shoppingBasket);
    }

    public boolean equals(Object object) {
        if(object instanceof ConditionalDiscount){
            ConditionalDiscount toCompare = (ConditionalDiscount) object;
            return this.discountLevelState.equals(toCompare.discountLevelState) &&
                    this.percentageOfDiscount == toCompare.percentageOfDiscount &&
                    this.condition.equals ( toCompare.condition );
        }
        return false;
    }

    @Override
    public boolean isConditional(){
        return true;
    }

    @Override
    public DiscountTypeFacade visitToFacade(SimpleDiscountFacade discountFacade) {
        return null;
    }

    @Override
    public DiscountTypeFacade visitToFacade(ConditionalDiscountFacade discountFacade) {
        return discountFacade.toFacade ( this );
    }

    @Override
    public DiscountTypeFacade visitToFacade(MaxCompositeDiscountTypeFacade discountFacade) {
        return null;
    }

    public static ConditionalDiscountRep getCondDiscountRep() {
        return condDiscountRep;
    }

    public static void setCondDiscountRep(ConditionalDiscountRep condDiscountRep) {
        ConditionalDiscount.condDiscountRep = condDiscountRep;
    }
}
