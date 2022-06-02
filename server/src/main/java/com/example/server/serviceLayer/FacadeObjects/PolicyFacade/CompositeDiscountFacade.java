package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.KindOfCompositeDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.CompositeDiscount.MaxCompositeDiscount;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountType;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.ArrayList;
import java.util.List;

public class CompositeDiscountFacade extends DiscountTypeFacade{
    enum CompositeDiscountType{
        max,
        kindOf
    }
    protected List<DiscountTypeFacade> discountTypes;
    protected CompositeDiscountType compositeDiscountType;

    public CompositeDiscountFacade(int percentageOfDiscount, DiscountLevelStateFacade discountLevelState, List<DiscountTypeFacade> discountTypes, CompositeDiscountType compositeDiscountType) {
        super (percentageOfDiscount, discountLevelState );
        this.discountTypes = discountTypes;
        this.compositeDiscountType = compositeDiscountType;
    }

    public List<DiscountTypeFacade> getDiscountTypes() {
        return discountTypes;
    }

    public void setDiscountTypes(List<DiscountTypeFacade> discountTypes) {
        this.discountTypes = discountTypes;
    }

    public CompositeDiscountType getCompositeDiscountType() {
        return compositeDiscountType;
    }

    public void setCompositeDiscountType(CompositeDiscountType compositeDiscountType) {
        this.compositeDiscountType = compositeDiscountType;
    }

    @Override
    public DiscountType toBusinessObject() throws MarketException {
        List<DiscountType> discountTypeList = new ArrayList<> (  );
        for(DiscountTypeFacade discountTypeFacade: discountTypes)
            discountTypeList.add ( discountTypeFacade.toBusinessObject () );
        switch (compositeDiscountType){
            case max -> {
                return new MaxCompositeDiscount (discountTypeList );
            }
            case kindOf -> {
                return new KindOfCompositeDiscount (discountTypeList );
            }
            default -> {
                throw new MarketException ( "composite discount type does not exist" );
            }
        }
    }
}
