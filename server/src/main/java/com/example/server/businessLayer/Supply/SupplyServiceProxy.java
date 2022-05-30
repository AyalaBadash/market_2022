package com.example.server.businessLayer.Supply;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class SupplyServiceProxy {

    private final boolean testRequest;
    private final String TypeSupply="supply";
    private final String TypeCancel_supply="cancel_supply";
    private SupplyService supplyService;

    public SupplyServiceProxy(SupplyService supplyService1, boolean test){
        super();
        testRequest=test;
        this.supplyService=supplyService1;
    }
    public SupplyServiceProxy(){
        super();
        testRequest=true;
    }

    /**
     * set supply to a customer.
     * @param address the address to send to.
     * @return the transaction id
     */
    public int supply(Address address) {
        if(testRequest){
            return 10000;
        }
        try {

            List<NameValuePair> request = addressToString(address);
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
        if(testRequest){
            return 10000;
        }
        try{
            if(transactionId==-1){
                return -1;
            }
            List<NameValuePair> request= transactionToString(transactionId);
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
    private List<NameValuePair> transactionToString(int transactionId)  {
        List<NameValuePair> params = new ArrayList<NameValuePair>(){
            {

                add(new BasicNameValuePair("action_type", TypeCancel_supply));
                add(new BasicNameValuePair("transaction_id", String.valueOf(transactionId)));
            }
        };

        return params;
    }

    /**
     * creates request string out of an address.
     * @param address the address of the customer.
     * @return the request string.
     * @throws JsonProcessingException
     */
    private List<NameValuePair> addressToString(Address address) throws JsonProcessingException {
        List<NameValuePair> params = new ArrayList<NameValuePair>(){
            {

                add(new BasicNameValuePair("action_type", TypeSupply ));
                add(new BasicNameValuePair("name", address.getName()));
                add(new BasicNameValuePair("address", address.getAddress()));
                add(new BasicNameValuePair("city",address.getCity()));
                add(new BasicNameValuePair("country",address.getCountry()));
                add(new BasicNameValuePair("zip", address.getZip()));
            }
        };

        return params;
    }

    /**
     * sets new supply service to the handler.
     * @param supplyService1
     */
    public void setService(SupplyService supplyService1) {
        this.supplyService= supplyService1;
    }
}
