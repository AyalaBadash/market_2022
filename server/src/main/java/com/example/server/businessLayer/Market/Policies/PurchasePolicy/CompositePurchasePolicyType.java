package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.Policies.PurchasePolicy.PurchasePolicyState.PurchasePolicyLevelState;

import javax.persistence.*;
import java.util.List;
@Entity
@DiscriminatorValue(value = "CompositePurchasePolicyType")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "PurchasePolicyType",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class CompositePurchasePolicyType extends PurchasePolicyType {
    @Transient
    List<PurchasePolicyType> policies;

    public CompositePurchasePolicyType(PurchasePolicyLevelState purchasePolicyLevelState, List<PurchasePolicyType> policies) {
        super ( purchasePolicyLevelState );
        this.policies = policies;
    }

    public CompositePurchasePolicyType(){}


    public List<PurchasePolicyType> getPolicies() {
        return policies;
    }

    public void setPolicies(List<PurchasePolicyType> policies) {
        this.policies = policies;
    }
}
