package main.serviceLayer;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Appointment.ShopManagerAppointment;
import main.businessLayer.Appointment.ShopOwnerAppointment;
import main.businessLayer.Market2;
import main.businessLayer.MarketException;
import main.businessLayer.users.Visitor;
import main.serviceLayer.FacadeObjects.*;

import java.util.List;

public class UserService {
    private static UserService userService = null;
    private Market2 market2;

    private UserService() {
        market2 = Market2.getInstance();
    }

    public synchronized static UserService getInstance() {
        if (userService == null)
            userService = new UserService();
        return userService;
    }

    public ResponseT<VisitorFacade> guestLogin() {
        try{
            Visitor guest = this.market2.guestLogin();
            return new ResponseT<>(new VisitorFacade(guest));
        }catch (Exception e){
            return new ResponseT(e.getMessage());
        }
    }

    public Response exitSystem(String visitorName) {
        try {
            this.market2.visitorExitSystem(visitorName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Boolean> register(String userName, String userPassword) {
        ResponseT<Boolean> responseT;
        try {
            market2.register(userName, userPassword);
            responseT = new ResponseT<>(true);
        }
        catch (MarketException e)
        {
            responseT = new ResponseT<>(false);
        }
        return responseT;
    }

    public ResponseT<Boolean> addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member) {
        return market2.addPersonalQuery(userAdditionalQueries, userAdditionalAnswers, member);
    }

    public ResponseT<List<String>> memberLogin(String userName, String userPassword, String visitorName) {
        try {
            List<String> securityQs = market2.memberLogin(userName,userPassword,visitorName);
            return new ResponseT<>(securityQs);
        } catch (MarketException e){
            return new ResponseT<> ( e.getMessage () );
        }
        /*
        MemberFacade memberFacade = new MemberFacade(member.getName(),member.getMyCart(),appointedByMeFacadeList,appointmentsFacadeList);
    */
    }

    // TODO implement
    private ResponseT<List<String>> memberLoginGetQuestions(String memberName, String password)
    {
        ;
    }


    public ResponseT<VisitorFacade> logout(String visitorName) {
        ResponseT<VisitorFacade> toReturn;
        try {
            VisitorFacade visitorFacade = new VisitorFacade(market2.memberLogout(visitorName) , null, null);
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
            market2.appointShopOwner(shopOwnerName,appointedShopOwner,shopName);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }


    public Response appointShopManager(String shopOwnerName, String appointedShopManager, String shopName) {
        Response toReturn;
        try {
            toReturn = new Response();
            market2.appointShopManager(shopOwnerName,appointedShopManager,shopName);
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
            this.market2.editShopManagerPermissions(shopOwnerName, managerName, relatedShop,updatedAppointment.toBusinessObject());
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT getManagerAppointment(String shopOwnerName, String managerName, String relatedShop) {
        try {

            Appointment appointment = (market2.getManagerAppointment(shopOwnerName, managerName, relatedShop));
            AppointmentFacade appointmentFacade = appointment.isManager() ?
                    new ShopManagerAppointmentFacade((ShopManagerAppointment) appointment) :
                     new ShopOwnerAppointmentFacade((ShopOwnerAppointment) appointment);
            return new ResponseT<>(appointmentFacade);
        }catch (Exception e){
            return new ResponseT(e.getMessage());
        }
    }

    public ResponseT<MemberFacade> validateSecurityQuestions(String userName, List<String> answers)  {
        try{
            MemberFacade memberLoggedIn = market2.validateSecurityQuestions(userName,answers);
            return new ResponseT<> ( memberLoggedIn );
        }catch (MarketException e){
            return new ResponseT<> ( e.getMessage () );
        }
    }
    public ResponseT<MemberFacade> validateMember(String userName, String userPassword, String visitorName) throws Exception {
        return market2.validateMember(userName,userPassword,visitorName);
    }
}
