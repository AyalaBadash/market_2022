package com.example.server.serviceLayer.FacadeObjects;
public class Response {
    private String errorMessage;

    public Response(){}
    public Response(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isErrorOccurred() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
