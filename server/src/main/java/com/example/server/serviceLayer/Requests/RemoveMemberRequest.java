package com.example.server.serviceLayer.Requests;

public class RemoveMemberRequest {
    String manager;
    String MemberToRemove;

    public RemoveMemberRequest(String manager, String memberToRemove) {
        this.manager = manager;
        MemberToRemove = memberToRemove;
    }
    public RemoveMemberRequest(){}

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMemberToRemove() {
        return MemberToRemove;
    }

    public void setMemberToRemove(String memberToRemove) {
        MemberToRemove = memberToRemove;
    }
}
