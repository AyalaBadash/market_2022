package com.example.server.businessLayer.ExternalComponents.Supply;

import com.example.server.businessLayer.ExternalComponents.Payment.CreditCard;
import com.example.server.businessLayer.ExternalComponents.Payment.ExternalServiceHandler;
import com.example.server.businessLayer.ExternalComponents.Payment.PaymentMethod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SupplyHandler extends ExternalServiceHandler {

    private final String TypeSupply="supply";
    private final String TypeCancel_supply="cancel_supply";
    private SupplyService supplyService;

    public SupplyHandler(SupplyService supplyService1){
        super();
        this.supplyService=supplyService1;
    }


    /**
     * set supply to a customer.
     * @param address the address to send to.
     * @return the transaction id
     */
    public int supply(Address address) {
        try {

            String request = addressToString(address);
            return supplyService.supply(request);

        } catch (JsonProcessingException e) {
            String str= e.getMessage();
            return -1;
        }
        catch(Exception e){
            String str= e.getMessage();
            return -1;
        }
    }

    /**
     * Cancel a supply.
     * @param transactionId the supply transaction is.
     * @return
     */
    public int cancelSupply(int transactionId){
        try{
            if(transactionId==-1){
                return -1;
            }
            String request= transactionToString(transactionId);
            return supplyService.cancelSupply(request);
        }
        catch(Exception e){
            String str= e.getMessage();
            return -1;
        }
    }

    /**
     * creates a string request from the transaction id and the transaction type.
     * @param transactionId the transaction id to cancel.
     * @return the request string to send.
     * @throws JsonProcessingException
     */
    private String transactionToString(int transactionId) throws JsonProcessingException {
        var postContent = new LinkedHashMap<String,String>() {
            {
                put("action_type", "cancel_supply");
                put("transaction_id", String.valueOf(transactionId));
            }
        };
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(postContent);
        return requestBody;
    }

    /**
     * creates request string out of an address.
     * @param address the address of the customer.
     * @return the request string.
     * @throws JsonProcessingException
     */
    private String addressToString(Address address) throws JsonProcessingException {
        var postContent = new LinkedHashMap<String,String>() {
            {
                put( "action_type", "supply" );
                put( "name", "Israel Israelovice" );
                put("address", "Rager Blvd 12" );
                put( "city", "Beer Sheva" );
                put( "country", "Israel" );
                put( "zip", "8458527" );

            }
        };
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(postContent);
        return requestBody;
    }

    /**
     * sets new supply service to the handler.
     * @param supplyService1
     */
    public void setService(SupplyService supplyService1) {
        this.supplyService= supplyService1;
    }
}
