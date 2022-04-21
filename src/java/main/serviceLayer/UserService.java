package main.serviceLayer;

import main.businessLayer.Appointment.Appointment;
import main.businessLayer.Appointment.ShopManagerAppointment;
import main.businessLayer.Appointment.ShopOwnerAppointment;
import main.businessLayer.Market;
import main.serviceLayer.FacadeObjects.*;

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
        return null;
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
        return null;
    }

    public ResponseT<Boolean> addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member) {
        return null;
    }

    public ResponseT<MemberFacade> memberLogin(String userName, String userPassword, List<String> userAdditionalAnswers, String visitorName) {
        return null;
    }


    public Response logout(String visitorName) {
        return null;
    }


    public Response appointShopOwner(String shopOwnerName, String appointedShopOwner, String shopName) {

        return null;
    }


    public Response appointShopManager(String shopOwnerName, String appointedShopOwner, String shopName) {
        return null;
    }


    public ResponseT<List<AppointmentFacade>> getSelfAppointed(String shopOwnerName) {
        return null;
    }


    public ResponseT<List<ShopManagerAppointmentFacade>> getSelfManagerAppointed(String shopOwnerName) {
        return null;
    }


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
            // TODO need to remove casting
            Appointment appointment = (market.getManagerAppointment(shopOwnerName, managerName, relatedShop));
            AppointmentFacade appointmentFacade = appointment.isManager() ?
                    new ShopManagerAppointmentFacade((ShopManagerAppointment) appointment) :
                     new ShopOwnerAppointmentFacade((ShopOwnerAppointment) appointment);
            return new ResponseT<>(appointmentFacade);
        }catch (Exception e){
            return new ResponseT(e.getMessage());
        }
    }
}
