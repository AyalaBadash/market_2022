package com.example.server.businessLayer.Payment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.List;

public class WSEPPaymentService implements PaymentService{

    private final String url;

    {
        url = "https://cs-bgu-wsep.herokuapp.com/";

    }

    public WSEPPaymentService() {
    }

    @Override
    public int pay(List<NameValuePair> requestBody) {
        try {
            int result=-1;
            result=sendRequest(requestBody);
            return result;
            }
        catch (Exception e){
            return -1;
        }
    }

    @Override
    public int cancelPayment(List<NameValuePair> request) {
        try {
            int result=-1;
            result=sendRequest(request);
            return result;
        }
        catch (Exception e){
            return -1;
        }
    }


    /**
     * Makes handshake for payment.
     * @param requestBody the request string.
     * @return "OK" if success. empty if not.
     */
    @Override
    public String handShake(List<NameValuePair> requestBody) {
        try {
            String result="";
            result = sendRequestHandshake(requestBody);
            return result;
        }
        catch (Exception e){
            return "";
        }
    }

    /**
     * The main method for sending handshake request.
     * @param requestBody the request string.
     * @return the response of the request.
     * @throws IOException
     * @throws InterruptedException
     */
    private String sendRequestHandshake(List<NameValuePair> requestBody) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            HttpPost request = new HttpPost(url);

            // adding the form data
            request.setEntity(new UrlEncodedFormEntity(requestBody));
            HttpResponse response= httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            // String of the response
            return EntityUtils.toString(entity);
        }
        catch(Exception e){
            String str= e.getMessage();
            return "";
        }
    }


    /**
     * Sends the request to the given url.
     * @param requestBody the dictionary of values.
     * @return the request result.
     * @throws IOException
     * @throws InterruptedException
     */
    public int sendRequest(List<NameValuePair> requestBody) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        HttpPost request = new HttpPost(url);

        // adding the form data
        request.setEntity(new UrlEncodedFormEntity(requestBody));
        HttpResponse response= httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        // String of the response
        String responseString = EntityUtils.toString(entity);
        int res;
        try{
          res =Integer.parseInt(responseString);
        }
        catch (Exception e ){
            res=-1;
        }
        return  res;
    }
}
