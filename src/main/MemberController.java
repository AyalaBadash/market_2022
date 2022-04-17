package main;

import java.util.HashMap;
import java.util.Map;

public class MemberController {
    Map<String,Member> members;
    private static MemberController instance;
    public static MemberController getInstance() {
        if (instance == null)
            return new MemberController();
        else return instance;
    }
    private MemberController(){
        members = new HashMap<>();
    }

    public boolean collectDebt(){
        throw new UnsupportedOperationException();
    }
    public boolean supplyProducts(){throw new UnsupportedOperationException();}

    public void createMember(String name) {
        Member member = new Member(name);
        members.put(name,member);
    }
}
