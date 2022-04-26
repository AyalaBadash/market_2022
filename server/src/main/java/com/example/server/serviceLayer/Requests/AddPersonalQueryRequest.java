package com.example.server.serviceLayer.Requests;

import com.example.server.serviceLayer.FacadeObjects.MemberFacade;

public class AddPersonalQueryRequest {
    private String userAdditionalQueries;
    private String userAdditionalAnswers;
    private MemberFacade member;

    public AddPersonalQueryRequest() {
    }

    public AddPersonalQueryRequest(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member) {
        this.userAdditionalQueries = userAdditionalQueries;
        this.userAdditionalAnswers = userAdditionalAnswers;
        this.member = member;

    }

    public String getUserAdditionalQueries() {
        return userAdditionalQueries;
    }

    public void setUserAdditionalQueries(String userAdditionalQueries) {
        this.userAdditionalQueries = userAdditionalQueries;
    }

    public String getUserAdditionalAnswers() {
        return userAdditionalAnswers;
    }

    public void setUserAdditionalAnswers(String userAdditionalAnswers) {
        this.userAdditionalAnswers = userAdditionalAnswers;
    }

    public MemberFacade getMember() {
        return member;
    }

    public void setMember(MemberFacade member) {
        this.member = member;
    }
}
