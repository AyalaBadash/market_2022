package com.example.server.businessLayer.ExternalComponents.Payment;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WSEPPaymentService implements PaymentService{

    private final String url;

    {
        url = "https://cs-bgu-wsep.herokuapp.com/";

    }

    public WSEPPaymentService() {
    }

    @Override
    public int pay(String requestBody) {
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
    public int cancelPayment(String requestBody) {
        try {
            int result=-1;
            result=sendRequest(requestBody);
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
    public String handShake(String requestBody) {
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
    private String sendRequestHandshake(String requestBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return response.body();
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
