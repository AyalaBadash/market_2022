package com.example.server.businessLayer.Supply;

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

public class WSEPSupplyServiceAdapter implements SupplyService {

    private final String url;

    public WSEPSupplyServiceAdapter()
    {
        url = "https://cs-bgu-wsep.herokuapp.com/";
    }

    @Override
    public int supply(List<NameValuePair> requestBody) {
        try {
            return sendRequest(requestBody);
        }
        catch (Exception e){
            return -1;
        }
    }

    @Override
    public int cancelSupply(List<NameValuePair> requestBody) {
        try {
            return sendRequest(requestBody);
        }
        catch (Exception e){
            return -1;
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
        HttpClient httpClient ;
        HttpPost request;
        try {
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            request = new HttpPost(url);

            // adding the form data
            request.setEntity(new UrlEncodedFormEntity(requestBody));
        }
        catch (Exception e){
            throw new IOException("Could not connect to the supply service");
        }
        if(httpClient==null | request==null){
            throw new IOException("Could not connect to the supply service");
        }
        //send the request to the service server.
        HttpResponse response= httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        // String of the response
        String responseString = EntityUtils.toString(entity);

        int res;
        try{
            res =Integer.parseInt(responseString);
        }
        catch (Exception e ){
            throw new IOException("Error with supply service");
        }
        return  res;
    }
}
