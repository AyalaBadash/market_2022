package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.SystemManager;
import main.businessLayer.ClosedShopsHistory;

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