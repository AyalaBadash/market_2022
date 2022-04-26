package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.ClosedShopsHistory;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingCart;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.SystemManager;

public class SystemManagerFacade implements FacadeObject<SystemManager> {

    private MemberFacade member;
    private ClosedShopsHistory history;

    public SystemManagerFacade(Member member) {
        this.member = new MemberFacade (member);
        this.history = ClosedShopsHistory.getInstance();
    }

    public MemberFacade getMember() {
        return member;
    }

    public void setMember(MemberFacade member) {
        this.member = member;
    }

    public ClosedShopsHistory getHistory() {
        return history;
    }

    public void setHistory(ClosedShopsHistory closedShopsHistory) {
        this.history = closedShopsHistory;
    }

    @Override
    public SystemManager toBusinessObject() throws MarketException {
        Member member = this.member.toBusinessObject ();
        return new SystemManager( member );
    }
}