package com.example.server.businessLayer.Market.Policies.DiscountPolicy;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.dataLayer.repositories.SimpleDiscountRep;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.ConditionalDiscountFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.MaxCompositeDiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.SimpleDiscountFacade;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "SimpleDiscount")
public class SimpleDiscount extends DiscountType{

    private static SimpleDiscountRep simpleDiscountRep;

    public SimpleDiscount(double percentageOfDiscount, DiscountLevelState discountLevelState) throws MarketException {
        super(percentageOfDiscount, discountLevelState);
        if (!MarketConfig.IS_TEST_MODE) {
            simpleDiscountRep.save(this);
        }
    }

    public SimpleDiscount() {}

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        return true;
    }

    public boolean equals(Object object){
        if(object instanceof SimpleDiscount){
            SimpleDiscount toCompare = (SimpleDiscount) object;
            boolean out =  this.discountLevelState.equals(toCompare.discountLevelState) && this.percentageOfDiscount == toCompare.percentageOfDiscount;
            return out;
        }
        return false;
    }

    @Override
    public boolean isSimple(){
        return true;
    }

    @Override
    public DiscountTypeFacade visitToFacade(SimpleDiscountFacade discountFacade) {
        return discountFacade.toFacade ( this );
    }

    @Override
    public DiscountTypeFacade visitToFacade(ConditionalDiscountFacade discountFacade) {
        return null;
    }

    @Override
    public DiscountTypeFacade visitToFacade(MaxCompositeDiscountTypeFacade discountFacade) {
        return null;
    }

    public static SimpleDiscountRep getSimpleDiscountRep() {
        return simpleDiscountRep;
    }

    public static void setSimpleDiscountRep(SimpleDiscountRep simpleDiscountRep) {
        SimpleDiscount.simpleDiscountRep = simpleDiscountRep;
    }
}
