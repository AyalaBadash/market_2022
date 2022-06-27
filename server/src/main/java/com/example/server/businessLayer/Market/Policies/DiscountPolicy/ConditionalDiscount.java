package com.example.server.businessLayer.Market.Policies.DiscountPolicy;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.Condition.Cond;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.dataLayer.repositories.ConditionalDiscountRep;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.ConditionalDiscountFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.MaxCompositeDiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.SimpleDiscountFacade;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "CondDiscount")
public class ConditionalDiscount extends DiscountType{
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
    private Cond condition;
    private static ConditionalDiscountRep condDiscountRep;

    public ConditionalDiscount(double percentageOfDiscount, DiscountLevelState discountLevelState, Cond condition) throws MarketException {
        super ( percentageOfDiscount, discountLevelState );
        this.condition = condition;
        if (!MarketConfig.IS_TEST_MODE) {
            condDiscountRep.save(this);
        }
    }

    public ConditionalDiscount(){}

    public Cond getCondition() {
        return condition;
    }

    public void setCondition(Cond condition) {
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
