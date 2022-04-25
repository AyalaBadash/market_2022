package main.serviceLayer.FacadeObjects;

import main.businessLayer.ClosedShopsHistory;
import main.businessLayer.users.Member;
import main.businessLayer.users.SystemManager;

public class SystemManagerFacade implements FacadeObject<SystemManager> {

    private Member member;
    private ClosedShopsHistory history;

    public SystemManagerFacade(Member member, ClosedShopsHistory history) {
        this.member = member;
        this.history = history;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public ClosedShopsHistory getHistory() {
        return history;
    }

    public void setHistory(ClosedShopsHistory closedShopsHistory) {
        this.history = closedShopsHistory;
    }

    @Override
    public SystemManager toBusinessObject() {
        return null;
    }
}
