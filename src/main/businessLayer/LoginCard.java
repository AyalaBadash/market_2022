package main.businessLayer;

import resources.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginCard {
    private String name;
    private String password;
    private HashMap<String, String> QandA;

    public LoginCard(String name, String password, List<String> questions, List<String> answers) {
        this.name = name;
        this.password = password;
        this.QandA =  new HashMap<>();
        if (questions.size() != answers.size()){
            throw new UnsupportedOperationException();
        }
        for (int i = 0; i< answers.size(); i++){
            this.QandA.put(questions.get(i), answers.get(i));
        }
    }

    public List<String> getQuestions(){
        List<String> questions =  new ArrayList<>();
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

    public HashMap<String, String> getQandA() {
        return QandA;
    }

}
