package com.example.server.businessLayer.Payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class PaymentServiceProxy {

    private final boolean testRequest;
    private final String okayMessage="OK";
    private final String TypeHandshake="handshake";
    private final String TypePay="pay";
    private final String TypeCancel_pay="cancel_pay";

    private PaymentService paymentService;

    public PaymentServiceProxy(PaymentService paymentService1, boolean test) {

        super();
        this.testRequest=test;
        this.paymentService = paymentService1;
    }

    public PaymentServiceProxy() {

        super();
        this.testRequest=true;
    }
    /**
     * Send the payment post if the handshake works.
     * @param method the payment method. The credit card in this version.
     * @return the transaction id.
     */
    public int pay(PaymentMethod method) throws JsonProcessingException {
        if(testRequest){
            return 10000;
        }
        try {
        if(handshake().equals(okayMessage)){
            if(method instanceof CreditCard){

                List<NameValuePair> request= CreditCardToString((CreditCard)method );
                return paymentService.pay(request);
            }
        }
        return -1;

        }

        catch (Exception e){
            throw  e;
        }
    }


    /**
     * Cancel a payment.
     * @param transactionId the transaction id to cancel.
     * @return 1 if succeed and -1 if not.
     */
    public int cancelPay(int transactionId) throws JsonProcessingException {
        if(testRequest){
            return 10000;
        }
        try{
            if(transactionId==-1){
                return -1;
            }
            List<NameValuePair> request= transactionToString(transactionId);
            return paymentService.cancelPayment(request);
        }
        catch(Exception e){
            throw e;
        }
    }


    /**
     * do handshake before payment.
     * @return "OK" if succeed. empty if not.
     */
    private String handshake(){

        try {
            return paymentService.handShake(handshakeString());
        }
        catch (Exception e){
            return "";
        }
    }

    /**
     * creates a string request from the credit cart details,address and the transaction type.
     * @param paymentMethod
     * @return the string request.
     * @throws JsonProcessingException
     */
    private List<NameValuePair> CreditCardToString(CreditCard paymentMethod) throws JsonProcessingException {
        List<NameValuePair> params = new ArrayList<NameValuePair>(){
            {

                add(new BasicNameValuePair("action_type", TypePay));
                add(new BasicNameValuePair("card_number", paymentMethod.getNumber()));
                add(new BasicNameValuePair("month", paymentMethod.getMonth()));
                add(new BasicNameValuePair("year",paymentMethod.getYear()));
                add(new BasicNameValuePair("holder", paymentMethod.getHolder()));
                add(new BasicNameValuePair("ccv", paymentMethod.getCvv()));
                add(new BasicNameValuePair("id", paymentMethod.getId()));
            }
        };

        return params;
    }

    /**
     * creates a string request from the transaction id and the transaction type.
     *
     * @param transactionId the transaction id to cancel.
     * @return the request string to send.
     * @throws JsonProcessingException
     */
    private List<NameValuePair> transactionToString(int transactionId) throws JsonProcessingException {

        List<NameValuePair> params = new ArrayList<NameValuePair>(){
            {

                add(new BasicNameValuePair("action_type", TypeCancel_pay ));
                add(new BasicNameValuePair("transaction_id", String.valueOf(transactionId)));
            }
        };

        return params;
    }

    /**
     * Creates request string for handshake.
     *
     * @return the request.
     */
    private List<NameValuePair> handshakeString(){
        List<NameValuePair> params = new ArrayList<NameValuePair>(){
            {

                add(new BasicNameValuePair("action_type", TypeHandshake ));
            }
        };

        return params;
    }


    /**
     * sets new payment service to the handler.
     * @param paymentService1
     */
    public void setService(PaymentService paymentService1) {
        this.paymentService=paymentService1;
    }
}
