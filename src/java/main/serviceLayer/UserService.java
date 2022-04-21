package main.serviceLayer;

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

    public Response exitSystem(String visitorName)  {
        try{
            this.market.visitorExitSystem(visitorName);
            return new Response();
        } catch (Exception e){
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


    public Response editShopManagerPermissions(String shopOwnerName,
                                               ShopManagerAppointmentFacade updatedAppointment) {
        return null;
    }
}
