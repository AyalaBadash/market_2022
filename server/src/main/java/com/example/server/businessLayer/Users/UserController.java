package com.example.server.businessLayer.Users;

import com.example.server.ResourcesObjects.ErrorLog;
import com.example.server.ResourcesObjects.EventLog;
import com.example.server.ResourcesObjects.SynchronizedCounter;
import com.example.server.businessLayer.Item;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.ShoppingCart;
import com.example.server.serviceLayer.Notifications.Notification;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserController {
    private Map<String, Member> members;
    private Map<String, Visitor> visitorsInMarket;
    //TODO synchronized next
    private SynchronizedCounter nextUniqueNumber;
    private static UserController instance;

    public synchronized static UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }

    private UserController() {
        members = new ConcurrentHashMap<>();
        visitorsInMarket = new ConcurrentHashMap<>();
        nextUniqueNumber = new SynchronizedCounter();
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
        EventLog.getInstance().Log("A new visitor entered the market.");
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
            EventLog.getInstance().Log("User left the market.");
        }
        else
        {
            ErrorLog.getInstance().Log("Non visitor tried to leave - The only way to be out is to be in.");
            throw new MarketException(String.format("%s tried to exit system but never entered", visitorName));
        }
    }

    public boolean register(String userName) throws MarketException {
        members.put(userName,new Member(userName));
        EventLog.getInstance().Log("Welcome to our new member: "+userName);
        return true;
    }


    private synchronized String getNextUniqueName() {
        String name = "@visitor" + nextUniqueNumber;
        nextUniqueNumber.increment();
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
        if (!members.containsKey(member)) {
            ErrorLog.getInstance().Log("Non member tried to logout");
            throw new MarketException("no such member");
        }
        else if (!visitorsInMarket.containsKey(member)) {
            ErrorLog.getInstance().Log("member who is not visiting tried to logout");
            throw new MarketException("not currently visiting the shop");
        }
        else{
            visitorsInMarket.remove(member);
            String newVisitorName = getNextUniqueName();
            Visitor newVisitor = new Visitor(newVisitorName);
            visitorsInMarket.put(newVisitorName, newVisitor);
            EventLog.getInstance().Log("Our beloved member "+member+" logged out.");
            return newVisitorName;
        }
    }
    public synchronized Member finishLogin(String userName, String visitorName) throws MarketException {
        Visitor newVisitorMember = new Visitor(userName,members.get(userName),members.get(userName).getMyCart());
        if(visitorsInMarket.containsKey(userName))
            throw new MarketException("member is already logged in");
        visitorsInMarket.put(userName,newVisitorMember);
        visitorsInMarket.remove ( visitorName );
        EventLog.getInstance().Log(userName+" logged in successfully.");
        return newVisitorMember.getMember();
    }


    //TODO - Going through members after visitors in market will result in Exception ( removed item for members who visiting twice)
    public void updateVisitorsInRemoveOfItem(Shop shop, Item itemToRemove) throws MarketException {
        for ( Visitor visitor : visitorsInMarket.values ()){
            visitor.getCart ().removeItem ( shop, itemToRemove);
        }
        for ( Member member: members.values ()){
            member.getMyCart().removeItem( shop, itemToRemove);
        }
        EventLog.getInstance().Log("Visitors cart has been updated due to item removal.");
    }

    public boolean isLoggedIn(String visitorName) {
        return visitorsInMarket.containsKey ( visitorName );
    }
    public boolean isMember(String visitorName) {
        return members.get ( visitorName ) != null;
    }

    public Member getMember(String visitorName) {
        return members.get ( visitorName );
    }

  
    public void setNextUniqueNumber(int nextUniqueNumber) {
        //TODO
//        this.nextUniqueNumber = 0;
    }

    public void reset() {
        members = new HashMap<>();
        visitorsInMarket = new HashMap<>();
        nextUniqueNumber.reset();
    }

}
