package com.example.server.serviceLayer;


import com.example.server.businessLayer.Appointment.Appointment;
import com.example.server.businessLayer.Appointment.ShopManagerAppointment;
import com.example.server.businessLayer.Appointment.ShopOwnerAppointment;
import com.example.server.businessLayer.Market;
import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Users.Visitor;
import com.example.server.serviceLayer.FacadeObjects.*;


import java.util.List;

public class UserService {
    private static UserService instance = null;
    private Market market;

    private UserService() {
        market = Market.getInstance();
    }

    public synchronized static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    public ResponseT<VisitorFacade> guestLogin() {
        try{
            Visitor guest = this.market.guestLogin();
            VisitorFacade result = new VisitorFacade(guest);
            return new ResponseT<>(result);
        }catch (Exception e){
            return new ResponseT(e.getMessage());
        }
    }

    public Response exitSystem(String visitorName) {
        try {
            this.market.visitorExitSystem(visitorName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Boolean> register(String userName, String userPassword) {
        ResponseT<Boolean> responseT;
        try {
            market.register(userName, userPassword);
            responseT = new ResponseT<>(true);
        }
        catch (MarketException e)
        {
            responseT = new ResponseT<>(false);
        }
        return responseT;
    }

    public Response addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, String member) {
        try {
            market.addPersonalQuery(userAdditionalQueries, userAdditionalAnswers, member);
            return new Response (  );
        }catch (MarketException e){
            return new Response ( e.getMessage () );
        }
    }

    public ResponseT<List<String>> memberLogin(String userName, String userPassword) {
        try {
            List<String> securityQs = market.memberLogin(userName,userPassword);
            return new ResponseT<>(securityQs);
        } catch (MarketException e){
            return new ResponseT<> ( e.getMessage () );
        }
        /*
        MemberFacade memberFacade = new MemberFacade(member.getName(),member.getMyCart(),appointedByMeFacadeList,appointmentsFacadeList);
    */
    }

    public ResponseT<VisitorFacade> logout(String visitorName) {
        ResponseT<VisitorFacade> toReturn;
        try {
            //  TODO cannot create visitor here! id must be re generated. also, cart cannot be null;
            VisitorFacade visitorFacade = new VisitorFacade(market.memberLogout(visitorName) , null, null);
            toReturn = new ResponseT<>(visitorFacade);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }


    public Response appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName) {
        try {
            market.appointShopOwner(shopOwnerName,appointedShopOwner,shopName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response appointShopManager(String shopOwnerName, String appointedShopManager, String shopName) {
        Response toReturn;
        try {
            toReturn = new Response();
            market.appointShopManager(shopOwnerName,appointedShopManager,shopName);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response editShopManagerPermissions(String shopOwnerName, String managerName,
                                               String relatedShop, ShopManagerAppointmentFacade updatedAppointment) {
        try{
            this.market.editShopManagerPermissions(shopOwnerName, managerName, relatedShop,updatedAppointment.toBusinessObject());
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT getManagerAppointment(String shopOwnerName, String managerName, String relatedShop) {
        try {

            Appointment appointment = (market.getManagerAppointment(shopOwnerName, managerName, relatedShop));
            AppointmentFacade appointmentFacade = appointment.isManager() ?
                    new ShopManagerAppointmentFacade((ShopManagerAppointment) appointment) :
                    new ShopOwnerAppointmentFacade((ShopOwnerAppointment) appointment);
            return new ResponseT<>(appointmentFacade);
        }catch (Exception e){
            return new ResponseT(e.getMessage());
        }
    }

    public ResponseT<MemberFacade> validateSecurityQuestions(String userName, List<String> answers, String visitorName)  {
        try{
            MemberFacade memberLoggedIn = market.validateSecurityQuestions(userName,answers, visitorName);
            return new ResponseT<> ( memberLoggedIn );
        }catch (MarketException e){
            return new ResponseT<> ( e.getMessage () );
        }
    }

}