package com.example.server.businessLayer.Supply;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class WSEPSupplyServiceAdapter implements SupplyService {

    private final String url;

    public WSEPSupplyServiceAdapter()
    {
        url = "https://cs-bgu-wsep.herokuapp.com/";
    }

    @Override
    public int supply(Address address) throws MarketException, IOException {
        try {
            List<NameValuePair> requestBody= addressToString(address);
            return sendRequest(requestBody);
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public int cancelSupply(int transactionId) throws MarketException, IOException {
        try {
            List<NameValuePair> requestBody= transactionToString(transactionId);
            return sendRequest(requestBody);
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * Sends the request to the given url.
     * @param requestBody the dictionary of values.
     * @return the request result.
     * @throws IOException
     * @throws InterruptedException
     */
    public int sendRequest(List<NameValuePair> requestBody) throws IOException, MarketException {
        HttpClient httpClient ;
        HttpPost request;
        try {
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            request = new HttpPost(url);

            // adding the form data
            request.setEntity(new UrlEncodedFormEntity(requestBody));
        }
        catch (Exception e){
            throw new MarketException("Error0");
        }
        if(httpClient==null | request==null){
            throw new MarketException("Error0");
        }
        int res;
        try{
        //send the request to the service server.
        HttpResponse response= httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        // String of the response
        String responseString = EntityUtils.toString(entity);


            res =Integer.parseInt(responseString);
        }
        catch (Exception e ){
            throw new MarketException("Error1");

        }
        return  res;
    }

    /**
     * creates a string request from the transaction id and the transaction type.
     *
     * @param transactionId the transaction id to cancel.
     * @return the request string to send.
     * @throws JsonProcessingException
     */
    public List<NameValuePair> transactionToString(int transactionId) {
        List<NameValuePair> params = new ArrayList<NameValuePair>() {
            {

                add(new BasicNameValuePair("action_type", TypeCancel_supply));
                add(new BasicNameValuePair("transaction_id", String.valueOf(transactionId)));
            }
        };

        return params;
    }

    /**
     * creates request string out of an address.
     *
     * @param address the address of the customer.
     * @return the request string.
     * @throws JsonProcessingException
     */
    public List<NameValuePair> addressToString(Address address) {
        List<NameValuePair> params = new ArrayList<NameValuePair>() {
            {

                add(new BasicNameValuePair("action_type", TypeSupply));
                add(new BasicNameValuePair("name", address.getName()));
                add(new BasicNameValuePair("address", address.getAddress()));
                add(new BasicNameValuePair("city", address.getCity()));
                add(new BasicNameValuePair("country", address.getCountry()));
                add(new BasicNameValuePair("zip", address.getZip()));
            }
        };

        return params;
    }

}
