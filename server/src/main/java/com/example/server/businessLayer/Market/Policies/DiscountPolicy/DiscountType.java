package com.example.server.businessLayer.Market.Policies.DiscountPolicy;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.ShopLevelState;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.ConditionalDiscountFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.DiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.MaxCompositeDiscountTypeFacade;
import com.example.server.serviceLayer.FacadeObjects.PolicyFacade.SimpleDiscountFacade;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "discount_type",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class DiscountType {
    @Id
    @GeneratedValue
    private long id;
    protected double percentageOfDiscount;
    @OneToOne
    protected DiscountLevelState discountLevelState;

    public DiscountType(double percentageOfDiscount, DiscountLevelState discountLevelState) {
        this.percentageOfDiscount = percentageOfDiscount;
        this.discountLevelState = discountLevelState;
    }

    public DiscountType(){
        percentageOfDiscount = 0;
        discountLevelState = new ShopLevelState ();
    }

    public double getPercentageOfDiscount() {
        return percentageOfDiscount;
    }

    public void setPercentageOfDiscount(double percentageOfDiscount) {
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public DiscountLevelState getDiscountLevelState() {
        return discountLevelState;
    }

    public void setDiscountLevelState(DiscountLevelState discountLevelState) {
        this.discountLevelState = discountLevelState;
    }

    public double calculateDiscount(ShoppingBasket shoppingBasket) throws MarketException {
        if(isDiscountHeld(shoppingBasket))
            return discountLevelState.calculateDiscount(shoppingBasket, percentageOfDiscount);
        return shoppingBasket.getPrice();
    }
    public abstract boolean isDiscountHeld(ShoppingBasket shoppingBasket) throws MarketException;

    public abstract boolean equals(Object object);

    public boolean isSimple(){
        return false;
    }

    public boolean isConditional(){
        return false;
    }

    public boolean isMax(){
        return false;
    }

    public abstract DiscountTypeFacade visitToFacade(SimpleDiscountFacade discountFacade);
    public abstract  DiscountTypeFacade visitToFacade(ConditionalDiscountFacade discountFacade);
    public abstract DiscountTypeFacade visitToFacade(MaxCompositeDiscountTypeFacade discountFacade);

}
