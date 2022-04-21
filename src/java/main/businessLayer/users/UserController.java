package main.businessLayer.users;


import java.util.HashMap;
import java.util.Map;

public class UserController {
    private Map<String, Member> members;
    private Map<String,Visitor> visitorsInMarket;
    private int nextUniqueNumber;
    private static UserController instance;

    public synchronized static UserController getInstance() {
        if (instance == null)
            return new UserController();
        else return instance;
    }
    private UserController(){
        members = new HashMap<>();
        visitorsInMarket = new HashMap<>();
        nextUniqueNumber = 1;
    }

    public Visitor guestLogin(){
        // TODO need to implement
        String name = getNextUniqueName();
        throw new UnsupportedOperationException();
    }

    public void exitSystem(){
        throw new UnsupportedOperationException();
    }

    public boolean register(String userName, String userPassword) {
    throw new UnsupportedOperationException();

    }



    private synchronized String getNextUniqueName() {
        String name = "visitor" + nextUniqueNumber;
        nextUniqueNumber ++;
        return name;
    }

    public synchronized Map<String, Member> getMembers() {
        return members;
    }

    public void addMemberToMarket() {
        throw new UnsupportedOperationException();
    }


    public Map<String, Visitor> getVisitorsInMarket() {
        return visitorsInMarket;
    }

    public void setVisitorsInMarket(Map<String, Visitor> visitorsInMarket) {
        this.visitorsInMarket = visitorsInMarket;
    }

    public boolean isMember(String visitorName) {
        return members.get ( visitorName ) != null;
    }

    public Member getMember(String visitorName) {
        return members.get ( visitorName );
    }
}
