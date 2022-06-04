package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.ConditionalDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

public class ConditionalDiscountFacade extends DiscountTypeFacade {
    ConditionFacade conditionFacade;

    public ConditionalDiscountFacade(int percentageOfDiscount, DiscountLevelStateFacade discountLevelState, ConditionFacade conditionFacade) {
        super (percentageOfDiscount, discountLevelState );
        this.conditionFacade = conditionFacade;
        this.type = "ConditionalDiscountFacade";

    }

    public ConditionFacade getConditionFacade() {
        return conditionFacade;
    }

    public void setConditionFacade(ConditionFacade conditionFacade) {
        this.conditionFacade = conditionFacade;
    }

    @Override
    public DiscountType toBusinessObject() throws MarketException {
        return new ConditionalDiscount (percentageOfDiscount, discountLevelState.toBusinessObject (),conditionFacade.toBusinessObject () );
    }
}
