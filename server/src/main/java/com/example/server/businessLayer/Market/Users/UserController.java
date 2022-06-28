package com.example.server.businessLayer.Market.Users;

import com.example.server.businessLayer.Market.Appointment.Appointment;
import com.example.server.businessLayer.Market.Market;
import com.example.server.businessLayer.Market.ResourcesObjects.*;
import com.example.server.businessLayer.Market.Item;
import com.example.server.businessLayer.Market.Shop;
import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.businessLayer.Market.ShoppingCart;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class UserController {
    private Map<String, Member> members;
//    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
//    @JoinTable(name = "visitors_in_market",
//            joinColumns = {@JoinColumn(name = "us", referencedColumnName = "id")})
//    @MapKeyColumn(name = "visitor") // the key column
    private Map<String, Visitor> visitorsInMarket;
    private SynchronizedCounter nextUniqueNumber;
    private static UserController instance;
    private long registeredMembersAvg;
    private LocalDate openingDate;
//    private static UserControllerRep userControllerRep;

    public synchronized static UserController getInstance() {
        if (instance == null)
            instance = new UserController(1);
        return instance;
    }

    private UserController(int i) {
        members = new ConcurrentHashMap<>();
        visitorsInMarket = new ConcurrentHashMap<>();
        nextUniqueNumber = new SynchronizedCounter();
        this.registeredMembersAvg = 0;
        this.openingDate = LocalDate.now();
    }

    public UserController(){}



    /**
     *
     * @return a new visitor with a unique name and null member
     * getting unique name is the only sync code in this method
     */
    public Visitor guestLogin() {
        String name = getNextUniqueName();
        Visitor res = new Visitor(name,null,new ShoppingCart());
        this.visitorsInMarket.put(res.getName(),res);
        if (!MarketConfig.IS_TEST_MODE) {
//        userControllerRep.save(this);
        }
        EventLog.getInstance().Log("A new visitor entered the market.");
        return res;
    }
    public Member memberLogin(String userName, String userPassword){
        return null;
    }


    public void exitSystem(String visitorName) throws MarketException {
        if (this.visitorsInMarket.containsKey(visitorName)) {
            Visitor visitorToDelete = this.visitorsInMarket.get(visitorName);
            this.visitorsInMarket.remove(visitorName);
            if (!MarketConfig.IS_TEST_MODE) {
//            userControllerRep.save(this);
            }
            if (!MarketConfig.IS_TEST_MODE) {
//            Visitor.getVisitorRep().delete(visitorToDelete);
            }
            if (!MarketConfig.IS_TEST_MODE) {
                ShoppingCart.getShoppingCartRep().delete(visitorToDelete.getCart());
            }

            Market.getInstance ().updateBidInLoggingOut(visitorName);
            EventLog.getInstance().Log("User left the market.");
        }
        else
        {
            DebugLog.getInstance().Log("Non visitor tried to leave - The only way to be out is to be in.");
            throw new MarketException(String.format("%s tried to exit system but never entered", visitorName));
        }
    }

    public boolean register(String userName) throws MarketException {
        Member newMember = new Member(userName);
        members.put(userName,newMember);
        EventLog.getInstance().Log("Welcome to our new member: "+userName);
        return true;
    }


    private synchronized String getNextUniqueName() {
        String name = "@visitor" + nextUniqueNumber.increment();
        return name;
    }

    public synchronized Map<String, Member> getMembers() {
        return members;
    }

    public void addMemberToMarket() {
        throw new UnsupportedOperationException();
    }

    public Visitor getVisitor(String visitorName) throws MarketException {
        Visitor visitor = this.visitorsInMarket.get(visitorName);
        if (visitor == null)
            throw new MarketException("no such visitor in the market");
        return visitor;
    }
    public Map<String, Visitor> getVisitorsInMarket() {
        return visitorsInMarket;
    }

    public void setVisitorsInMarket(Map<String, Visitor> visitorsInMarket) {
        this.visitorsInMarket = visitorsInMarket;
    }

    public String memberLogout(String member) throws MarketException {
        if (!members.containsKey(member)) {
            DebugLog.getInstance().Log("Non member tried to logout");
            throw new MarketException("no such member");
        }
        if (!visitorsInMarket.containsKey(member)) {
            DebugLog.getInstance().Log("member who is not visiting tried to logout");
            throw new MarketException("not currently visiting the shop");
        }
        Visitor visitorToDelete = this.visitorsInMarket.get(member);
        visitorsInMarket.remove(member);
        String newVisitorName = getNextUniqueName();
        Visitor newVisitor = new Visitor(newVisitorName);
        visitorsInMarket.put(newVisitorName, newVisitor);
        if (!MarketConfig.IS_TEST_MODE) {
//        userControllerRep.save(this);
        }
        if (!MarketConfig.IS_TEST_MODE) {
//        Visitor.getVisitorRep().delete(visitorToDelete);
        }
        EventLog.getInstance().Log("Our beloved member " + member + " logged out.");
        return newVisitorName;
    }
    public synchronized Member finishLogin(String userName, String visitorName) throws MarketException {
        Visitor newVisitorMember = new Visitor(userName,members.get(userName),members.get(userName).getMyCart());
        if(visitorsInMarket.containsKey(userName))
            throw new MarketException("member is already logged in");
        visitorsInMarket.put(userName,newVisitorMember);
        Visitor visitorToDelete = this.visitorsInMarket.get(visitorName);
        visitorsInMarket.remove(visitorName);

        if (!MarketConfig.IS_TEST_MODE) {
//        userControllerRep.save(this);
        }
        if (!MarketConfig.IS_TEST_MODE) {
//        Visitor.getVisitorRep().delete(visitorToDelete);
        }
        if (!MarketConfig.IS_TEST_MODE) {
            ShoppingCart.getShoppingCartRep().delete(visitorToDelete.getCart());
        }

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

        if (!MarketConfig.IS_TEST_MODE) {
//        userControllerRep.save(this);
        }
        EventLog.getInstance().Log("Visitors cart has been updated due to item removal.");
    }
    public synchronized void setRegisteredAvg(){

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
        this.nextUniqueNumber.setValue(nextUniqueNumber);
        if (!MarketConfig.IS_TEST_MODE) {
//        userControllerRep.save(this);
        }
    }

    public String getUsersInfo(){
        StringBuilder s = new StringBuilder("Numbers of avg new members per day:"+getRegisteredMembersAvg()+"\n");
        s.append("------------------------------------------");
        return s.toString();
    }
    private synchronized long getRegisteredMembersAvg() {
        int daysPassed = openingDate.until(LocalDate.now()).getDays();
        long newAvg = members.size()/ daysPassed;
        DecimalFormat format = new DecimalFormat("0.00");
        newAvg = Long.parseLong(format.format(newAvg));
        this.registeredMembersAvg = newAvg;
        return newAvg;
    }


    public void reset() {
        members = new HashMap<>();
        visitorsInMarket = new HashMap<>();
        nextUniqueNumber.reset();
    }

//    public static void setUserControllerRep(UserControllerRep userControllerRep) {
//        UserController.userControllerRep = userControllerRep;
//    }
    public boolean allInMarket(List<String> list) {

        for(String name :list){
            if(!members.containsKey(name)){
                return false;
            }
        }
        return true;
    }

    public void loadData(List<Member> members){
        for (Member mem : members) {
            this.members.put(mem.getName(), mem);
            mem.setAcquisitions(new ArrayList<>());
            List<Appointment> appts = mem.getAppointedByMe();
            for (Appointment apt : appts)
                apt.getPermissions().toString();
            appts.toString();
            List<Appointment> appts2 = mem.getMyAppointments();
            for (Appointment apt : appts2)
                apt.getPermissions().toString();
            appts2.toString();
            mem.getPurchaseHistory().toString();
        }
    }
}
