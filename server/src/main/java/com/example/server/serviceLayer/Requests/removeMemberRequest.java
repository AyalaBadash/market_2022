package com.example.server.serviceLayer.Requests;

public class removeMemberRequest {
    String manager;
    String MemberToRemove;

    public removeMemberRequest(String manager, String memberToRemove) {
        this.manager = manager;
        MemberToRemove = memberToRemove;
    }
    public removeMemberRequest(){}

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
