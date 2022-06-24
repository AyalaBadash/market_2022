package com.example.server.businessLayer.Security;

import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.dataLayer.repositories.LoginCardRep;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
@Entity
public class LoginCard {
    @Id
    private String name;
    private int password;
    @ElementCollection (fetch = FetchType.EAGER)
    @CollectionTable(name = "Q_and_A")
    @Column(name="answer")
    @MapKeyColumn(name="question")
    private Map<String, String> QandA;
    private static LoginCardRep loginCardRep;

    public LoginCard(String name, String password, List<String> questions, List<String> answers) throws MarketException {
        this.name = name;
        this.password = password.hashCode();
        this.QandA =  new ConcurrentHashMap<>();
        if(questions == null && answers == null)
            return;
        if (questions.size() != answers.size()){
            throw new MarketException ("in login request - number of answers does not match the numver of questions");
        }
        for (int i = 0; i< answers.size(); i++){
            this.QandA.put(questions.get(i), answers.get(i));
        }
        loginCardRep.save(this);
    }

    public LoginCard(){}

    public List<String> getQuestions(){
        List<String> questions =  new CopyOnWriteArrayList<>();
        for (Map.Entry<String, String> questionAndAnswers : QandA.entrySet()){
            questions.add(questionAndAnswers.getKey());
        }
        return questions;
    }


    public void addPrivateQuestion(String q, String ans){
        QandA.put(q,ans);
        loginCardRep.save(this);
    }
    public void removePrivateQuestion(String q){
        if (QandA.containsKey(q))
            QandA.remove(q);
    }

    public String getName() {
        return name;
    }

    public int getPassword() {
        return password;
    }

    public Map<String, String> getQandA() {
        return QandA;
    }

    public static void setLoginCardRep(LoginCardRep loginCardRep) {
        LoginCard.loginCardRep = loginCardRep;
    }
}
