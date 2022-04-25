package com.example.server.businessLayer.Users;

import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.ShoppingCart;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserController {
    private Map<String, Member> members;
    private Map<String, Visitor> visitorsInMarket;
    private int nextUniqueNumber;
    private static UserController instance;

    public synchronized static UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }

    private UserController() {
        members = new ConcurrentHashMap<>();
        visitorsInMarket = new ConcurrentHashMap<>();
        nextUniqueNumber = 1;
    }

    /**
     *
     * @return a new visitor with a unique name and null member
     * getting unique name is the only sync code in this method
     */
    public Visitor guestLogin() {
        String name = getNextUniqueName();
        Visitor res = new Visitor(name,null,new ShoppingCart());
        this.visitorsInMarket.put(res.getName(),res);
        return res;
    }
    public Member memberLogin(String userName, String userPassword){
        return null;
    }


    public void exitSystem(String visitorName) throws MarketException {
        if (this.visitorsInMarket.containsKey(visitorName)) {
            if (members.containsKey(visitorName)){
//                visitorName = this.logout(visitorName).getName();
            }
            this.visitorsInMarket.remove(visitorName);
        }
        throw new MarketException(String.format("%s tried to exit system but never entered", visitorName));
    }

    public boolean register(String userName) {
        members.put(userName,new Member(userName));
        return true;
    }


    private synchronized String getNextUniqueName() {
        String name = "visitor" + nextUniqueNumber;
        nextUniqueNumber++;
        return name;
    }

    public synchronized Map<String, Member> getMembers() {
        return members;
    }

    public void addMemberToMarket() {
        throw new UnsupportedOperationException();
    }

    public Visitor getVisitor(String visitorName){
        return this.visitorsInMarket.get(visitorName);
    }
    public Map<String, Visitor> getVisitorsInMarket() {
        return visitorsInMarket;
    }

    public void setVisitorsInMarket(Map<String, Visitor> visitorsInMarket) {
        this.visitorsInMarket = visitorsInMarket;
    }

    public String memberLogout(String member) throws MarketException {
        if (!members.containsKey(member))
            throw new MarketException("no such member");
        else if (!visitorsInMarket.containsKey(member))
            throw new MarketException("not currently visiting the shop");
        else{
            visitorsInMarket.remove(member);
            String newVisitorName = getNextUniqueName();
            Visitor newVisitor = new Visitor(newVisitorName);
            visitorsInMarket.put(newVisitorName, newVisitor);
            return newVisitorName;
        }
    }
    public Member finishLogin(String userName) {
        Visitor newVisitorMember = new Visitor(userName,members.get(userName),members.get(userName).getMyCart());
        visitorsInMarket.put(userName,newVisitorMember);
        return newVisitorMember.getMember();
    }

    public boolean isMember(String visitorName) {
        return members.get ( visitorName ) != null;
    }

    public Member getMember(String visitorName) {
        return members.get ( visitorName );
    }
}
