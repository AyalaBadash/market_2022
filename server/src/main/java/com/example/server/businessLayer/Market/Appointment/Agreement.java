package com.example.server.businessLayer.Market.Appointment;

import com.example.server.businessLayer.Market.ResourcesObjects.DebugLog;
import com.example.server.businessLayer.Market.ResourcesObjects.EventLog;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.dataLayer.repositories.AgreementRep;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Entity
public class Agreement {
    @Id
    @GeneratedValue
    private long id;
    private boolean agreed;
    @ElementCollection
    @CollectionTable(name = "ownersAppointmentApproval")
    @Column(name="approved")
    @MapKeyColumn(name="owner_name")
    private Map<String,Boolean> ownersAppointmentApproval;
    private static AgreementRep agreementRep;

    public Agreement() {}

    public Agreement(boolean agreed, Map<String, Boolean> ownersAgreementStatus) {
        this.agreed = agreed;
        this.ownersAppointmentApproval = ownersAgreementStatus;
        if (!MarketConfig.IS_TEST_MODE) {
            agreementRep.save(this);
        }
    }

    public Agreement(List<String> owners){
        this.ownersAppointmentApproval = new HashMap<>();
        for (String owner: owners){
            ownersAppointmentApproval.put(owner,false);
        }
        this.agreed = false;
        if (!MarketConfig.IS_TEST_MODE) {
            agreementRep.save(this);
        }
    }

    public void setOwnerApproval(String ownerName , boolean approve) throws MarketException {
        if (!ownersAppointmentApproval.containsKey(ownerName)) {
            DebugLog.getInstance().Log("You dont have the authority to approve or reject this appointment.");
            throw new MarketException("You dont have the authority to approve or reject this appointment.");
        }
        ownersAppointmentApproval.replace(ownerName,approve);
        updateStatus();
        if (!MarketConfig.IS_TEST_MODE) {
            agreementRep.save(this);
        }
    }

    private void updateStatus() {
        for (Map.Entry<String,Boolean> entry:ownersAppointmentApproval.entrySet())
        {
            if (!entry.getValue()) {
                this.agreed = false;
                return;
            }
        }
        this.agreed =true;
        if (!MarketConfig.IS_TEST_MODE) {
            agreementRep.save(this);
        }
    }

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
        if (!MarketConfig.IS_TEST_MODE) {
            agreementRep.save(this);
        }
    }

    public Map<String, Boolean> getOwnersAppointmentApproval() {
        return ownersAppointmentApproval;
    }

    public void setOwnersAppointmentApproval(Map<String, Boolean> ownersAppointmentApproval) {
        this.ownersAppointmentApproval = ownersAppointmentApproval;
        if (!MarketConfig.IS_TEST_MODE) {
            agreementRep.save(this);
        }
    }

    public boolean getOwnerStatus(String ownerName) {
        return ownersAppointmentApproval.get(ownerName);
    }

    public void removeOwner(String firedAppointed) {
        if (this.ownersAppointmentApproval.containsKey(firedAppointed))
            ownersAppointmentApproval.remove(firedAppointed);
        updateStatus();
        if (!MarketConfig.IS_TEST_MODE) {
            agreementRep.save(this);
        }
    }

    public static AgreementRep getAgreementRep() {
        return agreementRep;
    }

    public static void setAgreementRep(AgreementRep agreementRep) {
        Agreement.agreementRep = agreementRep;
    }
}
