package com.example.server.businessLayer.ExternalComponents.Supply;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class WSEPSupplyService implements SupplyService {

    private final String url;

    public WSEPSupplyService() {
        url = "https://cs-bgu-wsep.herokuapp.com/";
    }

    @Override
    public int supply(String requestBody) {
        try {
            return sendRequest(requestBody);
        }
        catch (Exception e){
            return -1;
        }
    }

    @Override
    public int cancelSupply(String requestBody) {
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
    public int sendRequest(String requestBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return Integer.parseInt(response.body());
    }
}
