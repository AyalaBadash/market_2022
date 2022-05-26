package com.example.server.businessLayer.ExternalComponents.Payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PaymentHandler extends ExternalServiceHandler {

    private final String TypeHandshake="handshake";
    private final String TypePay="pay";
    private final String TypeCancel_pay="cancel_pay";

    private PaymentService paymentService;

    public PaymentHandler(PaymentService paymentService1) {

        super();
        this.paymentService = paymentService1;
    }

    /**
     * Send the payment post if the handshake works.
     * @param method the payment method. The credit card in this version.
     * @return the transaction id.
     */
    public int pay(PaymentMethod method){
        try {
        if(handshake().equals(okayMessage)){
            if(method instanceof CreditCard){

                String request= CreditCardToString((CreditCard)method );
                return paymentService.pay(request);
            }
        }
        return -1;

        }

        catch (JsonProcessingException e) {
            String str= e.getMessage();
            return -1;
        }
        catch (Exception e){
            String str= e.getMessage();
            return -1;
        }
    }


    /**
     * Cancel a payment.
     * @param transactionId the transaction id to cancel.
     * @return 1 if succeed and -1 if not.
     */
    public int cancelPay(int transactionId){
        try{
            if(transactionId==-1){
                return -1;
            }
            String request= transactionToString(transactionId);
            return paymentService.cancelPayment(request);
        }
        catch(Exception e){
            String str= e.getMessage();
            return -1;
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
            String str= e.getMessage();
            return "";
        }
    }

    /**
     * creates a string request from the credit cart details,address and the transaction type.
     * @param paymentMethod
     * @return the string request.
     * @throws JsonProcessingException
     */
    private String CreditCardToString(CreditCard paymentMethod) throws JsonProcessingException {
        var postContent = new LinkedHashMap<String,String>()  {
            {
                put("action_type", "pay");
                put("card_number", paymentMethod.getNumber());
                put("month", paymentMethod.getMonth());
                put("year",paymentMethod.getYear());
                put("holder", paymentMethod.getHolder());
                put("ccv", paymentMethod.getCvv());
                put("id", paymentMethod.getId());
            }
        };
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(postContent);
        return requestBody;
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
                put("action_type", "cancel_pay" );
                put("transaction_id", String.valueOf(transactionId));
            }
        };
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(postContent);
        return requestBody;
    }

    /**
     * Creates request string for handshake.
     * @return the request.
     */
    private String handshakeString(){
        var postContent = new LinkedHashMap<String,String>() {
            {
                put("action_type", "handshake");
            }
        };
        var objectMapper = new ObjectMapper();
        String requestBody = "";
        try {
            requestBody = objectMapper.writeValueAsString(postContent);
        } catch (JsonProcessingException e) {
            return "";
        }
        return requestBody;
    }


    /**
     * sets new payment service to the handler.
     * @param paymentService1
     */
    public void setService(PaymentService paymentService1) {
        this.paymentService=paymentService1;
    }
}
