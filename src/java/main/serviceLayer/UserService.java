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

    public Response exitSystem(String visitorName) {
        return null;
    }

    public ResponseT<Boolean> register(String userName, String userPassword) {
        return null;
    }

    public ResponseT<Boolean> addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member) {
        return null;
    }

    public ResponseT<List<String>> memberLogin(String userName, String userPassword, String visitorName) throws Exception {
        List<String> securityQs = market.memberLogin(userName,userPassword,visitorName);
        return new ResponseT<>(securityQs);
        /*

        MemberFacade memberFacade = new MemberFacade(member.getName(),member.getMyCart(),appointedByMeFacadeList,appointmentsFacadeList);
    */
    }

    private ResponseT<List<String>> memberLoginGetQuestions(String memberName, String password)
    {
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


    public Response editShopManagerPermissions(String shopOwnerName,
                                               ShopManagerAppointmentFacade updatedAppointment) {
        return null;
    }

    public ResponseT<MemberFacade> validateSecurityQuestions(String userName, List<String> answers) throws Exception {
        return market.validateSecurityQuestions(userName,answers);
    }

}
