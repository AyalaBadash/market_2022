package com.example.server.businessLayer.Publisher;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.serviceLayer.Notifications.DelayedNotifications;
import com.example.server.serviceLayer.Notifications.Notification;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationHandler {

    private static NotificationHandler instance=null;
    //holds notifications to send to each domain(by the member name)
    private Publisher dispatcher;

    //Map for sessionId-name pairs.
    private Map<String, String> sessions;


    public static NotificationHandler getInstance(){
        if(instance==null){
            instance=new NotificationHandler(NotificationDispatcher.getInstance());
        }
        return instance;
    }
    private NotificationHandler(Publisher dispatcher) {
        this.dispatcher = dispatcher;
        sessions = new ConcurrentHashMap<>();
    }

    //Sends message to user who just logged in.
    public synchronized boolean sendMessageToLogged(String name, Notification notification){
        if(!sessions.containsKey(name)){
            return false;
        }
        String sessionId=sessions.get(name);
        return dispatcher.addMessgae(sessionId,notification);
    }

    /**
     * Add new session.
     * @param name name of the new visitor that logged in.
     * @param sessionId the sessionId of the connection.
     * @return
     */
    public synchronized boolean add(String name, String sessionId) {


        sessions.put(name,sessionId);
        if(dispatcher.add(sessionId)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Callback method for user logged in.
     * @param name name of the new visitor that logged in.
     * @return
     */
    private boolean sendAllDelayedNotifications(List<Notification> nots, String name) {


        for(Notification not : nots){
            if(! sendMessageToLogged(name, not)){
                return false;
            }
        }

        deleteDelayed(name);
        return true;
    }

    private void deleteDelayed(String name) {
        try {
            File parentDir = new File(getConfigDir() );
            parentDir.mkdir();
            File file = new File(parentDir, name + ".txt");
            if(file.delete()){}
            else{
                throw new MarketException("could not delete file");
            }

        }
        catch (Exception e){}

    }

    /**
     * Remove a session in case of logout.
     * @param name the name of the visitor to remove.
     * @param sessionId the sessionId of the connection
     * @return
     */
    public synchronized boolean remove(String name, String sessionId) {

        List<Notification> nots= dispatcher.remove(sessionId);
        if(!nots.isEmpty()){
            for(Notification not : nots) {
                writeToText(not.getMessage(),name);
            }
        }
        sessions.remove(name);
        return true;
    }

    /**
     * Method in case visitor suddenly disconnected.
     * @param sessionId the sessionId to send to.
     * @return
     */
    public boolean removeErr(String sessionId) {
        String name="";
        for(Map.Entry<String ,String> set : sessions.entrySet()){
            if(set.getValue().equals(sessionId)){
                name=set.getKey();
            }
        }
        if(name.isEmpty()){
            return false;
        }
        List<Notification> nots= dispatcher.remove(sessionId);
        if(!nots.isEmpty()){
            for(Notification not : nots) {
                writeToText(not.getMessage(),name);
            }
        }
        sessions.remove(name);
        return true;
    }
    //Check for the market if a memeber/user is logged in .
    public boolean isLogged(String name){
        return sessions.containsKey(name);
    }

    /**
     * The main method for sending a notification for a user.
     *
     * @param name         the name of the visitor to send the message
     * @param notification the notification needed to be sent.
     * @param isMember     bool field that say if the visitor is a member.
     * @param test
     * @return
     */
    public boolean sendNotification(String name , Notification notification, boolean isMember, boolean test){

        if(sessions.containsKey(name)){
            //if user logged.
            String session= sessions.get(name);
            dispatcher.addMessgae(session,notification);
            return true;
        }
        else{
            //if not logged in. save if member
            if(isMember){
                if(test){
                    DelayedNotifications not= new DelayedNotifications();
                    not.createMessage("Delayed message: \n"+ notification.getMessage());
                    dispatcher.addMessgae(name,not);
                }
                else{
                    writeToText(notification.getMessage(), name);
                }
            }
        }
        return true;
    }

    /**
     * Sends to all owner that item is bought from shop.
     * @param buyer the man buyed the items.
     * @param names the names of the owners to send to.
     * @param shopName the shop name
     * @param itemsNames the baught items list.
     * @param prices the bought items prices.
     */
    public void sendItemBaughtNotificationsBatch(String buyer, ArrayList<String> names, String shopName, ArrayList<String> itemsNames, ArrayList<Double> prices,boolean test) {

        RealTimeNotifications not;
        for (String name : names) {
            for (int i = 0; i < itemsNames.size(); i++) {
                not = new RealTimeNotifications();
                not.createBuyingOfferMessage(buyer, shopName, itemsNames.get(i), prices.get(i));
                sendNotification(name,not,true, test);
            }
        }
    }

    public void sendShopClosedBatchNotificationsBatch(ArrayList<String> strings, String shopName,boolean test) {
        RealTimeNotifications not=new RealTimeNotifications();
        not.createShopClosedMessage(shopName);
        for(String name: strings){
            sendNotification(name,not,true, test);
        }
    }

    public void sendAppointmentRemovedNotification(String firedAppointed, String shopName,boolean test) {
        RealTimeNotifications not= new RealTimeNotifications();
        not.createShopPermissionDeniedMessage(shopName,firedAppointed);
        sendNotification(firedAppointed,not,true, test);
    }

    public void setService(Publisher o) {
        dispatcher=o;
    }


    public void login(String name, String visitor, String sessionId) {

        List<Notification> nots = readDelayedMessages(name);
        sessions.remove(visitor);
        sessions.put(name,sessionId);
        sendAllDelayedNotifications(nots,name);
    }

    private boolean writeToText(String message, String name){
        try {
            final File parentDir = new File(getConfigDir());
            parentDir.mkdir();
            final File file = new File(parentDir, name+".txt");
            file.createNewFile(); // Creates file crawl_html/abc.txt
            FileWriter myWriter = new FileWriter(file,true);
            myWriter.write(message);
            myWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    private List<Notification> readDelayedMessages(String name) {

        try {
            List<Notification> nots= new ArrayList<>();
            File myObj = new File(getConfigDir() + name+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.isEmpty())
                    continue;
                DelayedNotifications del= new DelayedNotifications();
                del.createMessage(data);
                nots.add(del);
            }
            return nots;

        } catch (Exception e) {
            return new ArrayList<>();
        }

    }
    private String getConfigDir() {
        String dir = System.getProperty("user.dir").split("/market_2022")[0];
        dir += "/market_2022/server/notifications/delayed/";
        return dir;
    }

    public void sendNewshopManager(Member shopOwner, Member appointed, String shopName,boolean test) {
        RealTimeNotifications not= new RealTimeNotifications();
        not.createNewManagerMessage(shopOwner.getName(),appointed.getName(),shopName);
        sendNotification(appointed.getName(),not,true,  test);
    }

    public void sendNewshopOwner(Member shopOwner, Member appointed, String shopName,boolean test) {

        RealTimeNotifications not= new RealTimeNotifications();
        not.createNewOwnerMessage(shopOwner.getName(),appointed.getName(),shopName);
        sendNotification(appointed.getName(),not,true, test);
    }
}
