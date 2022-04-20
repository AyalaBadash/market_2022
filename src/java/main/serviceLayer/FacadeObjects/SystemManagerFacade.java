package main.serviceLayer.FacadeObjects;

import main.businessLayer.History;
import main.businessLayer.users.Member;

public class SystemManagerFacade {

    private Member member;
    private History history;

    public SystemManagerFacade(Member member, History history) {
        this.member = member;
        this.history = history;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
