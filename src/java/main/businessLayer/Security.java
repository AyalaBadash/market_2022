package main.businessLayer;

import main.serviceLayer.FacadeObjects.MemberFacade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

public class Security {
    private Map<String, LoginCard> namesToLoginInfo;

    private static Security instance;

    private Security() {
        this.namesToLoginInfo =  new ConcurrentHashMap<>();
    }

    public static Security getInstance() {
        if (instance == null)
            return new Security();
        else return instance;
    }

    public void validateRegister(String name, String password) throws MarketException {
        validateName(name);
        LoginCard card = new LoginCard(name,password,new ArrayList<>(),new ArrayList<>());
        namesToLoginInfo.put(name,card);
    }
    // TODO need to document all exceptions
    private void validateQuestions(List<String> questions, List<String> answers) throws Exception {
        if (questions.size() != answers.size())
            throw new Exception();
    }

    private void validateName(String name) throws MarketException {
        if (namesToLoginInfo.containsKey(name))
            throw new MarketException("Name is already taken ,try to be a little more creative and choose another name. ");
        if (name == null || name.equals(""))
            throw new MarketException("Name can't be null or empty string");
    }



    public void addNewMember(String name, String password, List<String> questions,
                             List<String> answers) {
        LoginCard card = new LoginCard(name, password,questions, answers);
        this.namesToLoginInfo.put(name, card);
    }
    public List<String> getQuestionsByName(String name){
        return this.namesToLoginInfo.get(name).getQuestions();
    }

    // TODO question to answer must be corresponding sorted
    public boolean validateLogin(String name, String password,
                                                    List<String> questions, List<String> answers) throws Exception {
        LoginCard loginCard = namesToLoginInfo.get(name);
        if (!password.equals(loginCard.getPassword()))
            throw new Exception();
        for (int i =0; i< questions.size(); i++){
            if (loginCard.getQandA().get(questions.get(i)).equals(answers.get(i))){
                throw new Exception();
            }
        }
        return true;
    }


    public List<String> validatePassword(String userName, String userPassword) throws MarketException { //TODO specify exceptions
        if (!namesToLoginInfo.containsKey(userName))
            throw new MarketException ("No such user name in the system");
        if (!namesToLoginInfo.get(userName).getPassword().equals(userPassword))
            throw new MarketException ("Password mismatch");
        List<String> questions = new ArrayList<>();
        LoginCard card = namesToLoginInfo.get(userName);
        for (Map.Entry<String,String> entry : card.getQandA().entrySet())
        {
            questions.add(entry.getValue());
        }
        return questions;

    }

    public void validateQuestions(String userName, List<String> answers) throws MarketException{
        LoginCard card = namesToLoginInfo.get(userName);
        Map<String, String> QsAndAns = card.getQandA();
        if (answers.size()!=QsAndAns.size())
            throw new MarketException ("Answers size different from questions size");
        int index = 0;
        for (Map.Entry<String,String> entry: QsAndAns.entrySet())
        {
            if(entry.getValue().equals(answers.get(index)))
                index++;
        }
    }

    public void addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, MemberFacade member) throws MarketException {
        if (!namesToLoginInfo.containsKey(member.getName()))
        {
            throw new MarketException("No such user exist");
        }
        else {
            LoginCard card = namesToLoginInfo.get(member.getName());
            Map<String,String> QA = card.getQandA();
            QA.put(userAdditionalQueries,userAdditionalAnswers);
        }

    }
}
