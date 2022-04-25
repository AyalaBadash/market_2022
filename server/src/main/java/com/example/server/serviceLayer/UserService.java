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
    private static UserService userService = null;
    private Market market;

    private UserService() {
        market = Market.getInstance();
    }

    public synchronized static UserService getInstance() {
        if (userService == null)
            userService = new UserService();
        return userService;
    }

    public ResponseT<VisitorFacade> guestLogin() {
        try{
            Visitor guest = this.market.guestLogin();
            return new ResponseT<>(new VisitorFacade(guest));
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

    public ResponseT<Boolean> addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member) {
        return market.addPersonalQuery(userAdditionalQueries, userAdditionalAnswers, member);
    }

    public ResponseT<List<String>> memberLogin(String userName, String userPassword, String visitorName) throws Exception {
        List<String> securityQs = market.memberLogin(userName,userPassword,visitorName);
        return new ResponseT<>(securityQs);
        /*

        MemberFacade memberFacade = new MemberFacade(member.getName(),member.getMyCart(),appointedByMeFacadeList,appointmentsFacadeList);
    */
    }

    // TODO implement
    private ResponseT<List<String>> memberLoginGetQuestions(String memberName, String password)
    {
        return null;
    }


    public ResponseT<VisitorFacade> logout(String visitorName) {
        ResponseT<VisitorFacade> toReturn;
        try {
            VisitorFacade visitorFacade = new VisitorFacade(market.memberLogout(visitorName) , null, null);
            toReturn = new ResponseT<>(visitorFacade);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }


    public Response appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName) {

        Response toReturn;
        try {
            toReturn = new Response();
            market.appointShopOwner(shopOwnerName,appointedShopOwner,shopName);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
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

    // TODO implement
    public ResponseT<List<AppointmentFacade>> getSelfAppointed(String shopOwnerName) {
        return null;
    }

    // TODO implement
    public ResponseT<List<ShopManagerAppointmentFacade>> getSelfManagerAppointed(String shopOwnerName) {
        return null;
    }

    // TODO implement
    public ResponseT<List<ShopOwnerAppointmentFacade>> getSelfShopOwnerAppointed(String shopOwnerName) {
        return null;
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

    public ResponseT<MemberFacade> validateSecurityQuestions(String userName, List<String> answers) throws Exception {
        return market.validateSecurityQuestions(userName,answers);
    }

}