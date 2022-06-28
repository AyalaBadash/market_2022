package com.example.server.serviceLayer.Requests;

public class GetAcqsForMemberRequest {
    private  String memberName;

    public void GetAcqsForMemberRequest(){}

    public GetAcqsForMemberRequest(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
