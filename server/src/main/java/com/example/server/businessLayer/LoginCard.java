package com.example.server.businessLayer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
//TODO change name to MemberCard

public class LoginCard {
    private String name;
    private String password;
    private Map<String, String> QandA;

    public LoginCard(String name, String password, List<String> questions, List<String> answers) {
        this.name = name;
        this.password = password;
        this.QandA =  new ConcurrentHashMap<>();
        if (questions.size() != answers.size()){
            throw new UnsupportedOperationException();
        }
        for (int i = 0; i< answers.size(); i++){
            this.QandA.put(questions.get(i), answers.get(i));
        }
    }

    public List<String> getQuestions(){
        List<String> questions =  new CopyOnWriteArrayList<>();
        for (Map.Entry<String, String> questionAndAnswers : QandA.entrySet()){
            questions.add(questionAndAnswers.getKey());
        }
        return questions;
    }


    public void addPrivateQuestion(String q, String ans){
        throw new UnsupportedOperationException();
    }
    public void removePrivateQuestion(String q){
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, String> getQandA() {
        return QandA;
    }

}
