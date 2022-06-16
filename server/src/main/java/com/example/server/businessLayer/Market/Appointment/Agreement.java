package com.example.server.businessLayer.Market.Appointment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agreement {
    private boolean agreed;
    private Map<String,Boolean> ownersAppointmentApproval;

    public Agreement(boolean agreed, Map<String, Boolean> ownersAgreementStatus) {
        this.agreed = agreed;
        this.ownersAppointmentApproval = ownersAgreementStatus;
    }

    public Agreement(List<String> owners){
        this.ownersAppointmentApproval = new HashMap<>();
        for (String owner: owners){
            ownersAppointmentApproval.put(owner,false);
        }
        this.agreed = false;
    }

    public void setOwnerApproval(String ownerName , boolean approve){
        ownersAppointmentApproval.put(ownerName,approve);
        updateStatus();
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

    }

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }

    public Map<String, Boolean> getOwnersAppointmentApproval() {
        return ownersAppointmentApproval;
    }

    public void setOwnersAppointmentApproval(Map<String, Boolean> ownersAppointmentApproval) {
        this.ownersAppointmentApproval = ownersAppointmentApproval;
    }

}
