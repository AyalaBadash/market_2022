package com.example.server.serviceLayer.FacadeObjects.PolicyFacade.Wrappers;

import java.util.List;

public class DiscountTypeWrapper {
    enum CompositeDiscountTypeWrapperType {
        MaxCompositeDiscountTypeFacade,
        SimpleDiscountFacade,
        ConditionalDiscountFacade
    }
    private CompositeDiscountTypeWrapperType compositeDiscountTypeWrapperType;

    private double percentageOfDiscount;
    private DiscountLevelStateWrapper discountLevelStateWrapper;
    private ConditionWrapper conditionWrapper;
    List<DiscountTypeWrapper> discountTypeWrappers;

    public DiscountTypeWrapper(CompositeDiscountTypeWrapperType compositeDiscountTypeWrapperType, double percentageOfDiscount, DiscountLevelStateWrapper discountLevelStateWrapper, ConditionWrapper conditionWrapper, List<DiscountTypeWrapper> discountTypeWrappers) {
        this.compositeDiscountTypeWrapperType = compositeDiscountTypeWrapperType;
        this.percentageOfDiscount = percentageOfDiscount;
        this.discountLevelStateWrapper = discountLevelStateWrapper;
        this.conditionWrapper = conditionWrapper;
        this.discountTypeWrappers = discountTypeWrappers;
    }

    public DiscountTypeWrapper() {
    }

    public double getPercentageOfDiscount() {
        return percentageOfDiscount;
    }

    public void setPercentageOfDiscount(double percentageOfDiscount) {
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public DiscountLevelStateWrapper getDiscountLevelStateWrapper() {
        return discountLevelStateWrapper;
    }

    public void setDiscountLevelStateWrapper(DiscountLevelStateWrapper discountLevelStateWrapper) {
        this.discountLevelStateWrapper = discountLevelStateWrapper;
    }

    public ConditionWrapper getConditionWrapper() {
        return conditionWrapper;
    }

    public void setConditionWrapper(ConditionWrapper conditionWrapper) {
        this.conditionWrapper = conditionWrapper;
    }

    public List<DiscountTypeWrapper> getDiscountTypeWrappers() {
        return discountTypeWrappers;
    }

    public void setDiscountTypeWrappers(List<DiscountTypeWrapper> discountTypeWrappers) {
        this.discountTypeWrappers = discountTypeWrappers;
    }

    public CompositeDiscountTypeWrapperType getCompositeDiscountTypeWrapperType() {
        return compositeDiscountTypeWrapperType;
    }

    public void setCompositeDiscountTypeWrapperType(CompositeDiscountTypeWrapperType compositeDiscountTypeWrapperType) {
        this.compositeDiscountTypeWrapperType = compositeDiscountTypeWrapperType;
    }
}
