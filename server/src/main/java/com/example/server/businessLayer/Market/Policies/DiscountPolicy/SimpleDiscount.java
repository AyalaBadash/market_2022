package com.example.server.businessLayer.Market.Policies.DiscountPolicy;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.ConditionalDiscountFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.MaxCompositeDiscountFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.SimpleDiscountFacade;

public class SimpleDiscount extends DiscountType{

    public SimpleDiscount(int percentageOfDiscount, DiscountLevelState discountLevelState) {
        super(percentageOfDiscount, discountLevelState);
    }

    public SimpleDiscount() {
    }

    @Override
    public boolean isDiscountHeld(ShoppingBasket shoppingBasket) {
        return true;
    }

    public boolean equals(Object object){
        if(object instanceof SimpleDiscount){
            SimpleDiscount toCompare = (SimpleDiscount) object;
            return this.discountLevelState.equals(toCompare.discountLevelState) && this.percentageOfDiscount == toCompare.percentageOfDiscount;
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
    public DiscountTypeFacade visitToFacade(MaxCompositeDiscountFacade discountFacade) {
        return null;
    }
}
