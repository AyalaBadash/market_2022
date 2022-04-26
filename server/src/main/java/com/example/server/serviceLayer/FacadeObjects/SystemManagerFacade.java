package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.ClosedShopsHistory;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.SystemManager;

public class SystemManagerFacade implements FacadeObject<SystemManager> {

    private Member member;
    private ClosedShopsHistory history;

    public SystemManagerFacade(Member member) {
        this.member = member;
        this.history = ClosedShopsHistory.getInstance();
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
        return new SystemManager(this.member);
    }
}