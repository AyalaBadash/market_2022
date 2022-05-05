package com.example.server.businessLayer;

import com.example.server.ResourcesObjects.ErrorLog;
import com.example.server.ResourcesObjects.EventLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Security {
    private Map<String, LoginCard> namesToLoginInfo;

    private static Security instance;

    private Security() {
        this.namesToLoginInfo =  new ConcurrentHashMap<>();
    }

    public static Security getInstance() {
        if (instance == null)
            instance =  new Security();
        return instance;
    }

    public void validateRegister(String name, String password) throws MarketException {
        validateName(name);
        LoginCard card = new LoginCard(name,password,new ArrayList<>(),new ArrayList<>());
        namesToLoginInfo.put(name,card);
    }

    private void validateName(String name) throws MarketException {
        if (name == null || name.equals("")|| name.charAt(0) == '@'){
            ErrorLog.getInstance().Log("User tried to register with invalid name.");
            throw new MarketException("Name can't be null or empty string");
        }
        if (namesToLoginInfo.containsKey(name)) {
            ErrorLog.getInstance().Log("User tried to register with taken name.");
            throw new MarketException("Name is already taken ,try to be a little more creative and choose another name. ");
        }

    }

    public Map<String, LoginCard> getNamesToLoginInfo() {
        return namesToLoginInfo;
    }

    public void addNewMember(String name, String password, List<String> questions,
                             List<String> answers) throws MarketException {
        LoginCard card = new LoginCard(name, password,questions, answers);
        this.namesToLoginInfo.put(name, card);
    }
    public List<String> getQuestionsByName(String name){
        return this.namesToLoginInfo.get(name).getQuestions();
    }

    public List<String> validatePassword(String userName, String userPassword) throws MarketException {
        if (userName == null || !namesToLoginInfo.containsKey(userName)) {
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Non member visitor tried to log in.");
            throw new MarketException("No such user name in the system");
        }
        if (!namesToLoginInfo.get(userName).getPassword().equals(userPassword)) {
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Member "+userName+" tried to log in but has password mismatch.");
            throw new MarketException("Password mismatch");
        }
        List<String> questions = new ArrayList<>();
        LoginCard card = namesToLoginInfo.get(userName);
        for (Map.Entry<String,String> entry : card.getQandA().entrySet())
        {
            questions.add(entry.getValue());
        }
        EventLog eventLog = EventLog.getInstance();
        eventLog.Log("Prepared security questions for member.");
        return questions;

    }

    public void validateQuestions(String userName, List<String> answers) throws MarketException{
        if(answers == null)
            return;
        LoginCard card = namesToLoginInfo.get(userName);
        Map<String, String> QsAndAns = card.getQandA();
        if (answers.size()!=QsAndAns.size()) {
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Member didnt gave number of answers equal to questions number");
            throw new MarketException("Answers size different from questions size");
        }
        int index = 0;
        for (Map.Entry<String,String> entry: QsAndAns.entrySet())
        {
            if(entry.getValue().equals(answers.get(index)))
                index++;
            else {
                ErrorLog.getInstance().Log("Mismatch in validating security questions.");
                throw new MarketException(String.format("answer %s does not fit the answers", answers.get(index)));
            }
        }
    }


    public void addPersonalQuery(String userAdditionalQueries, String userAdditionalAnswers, String name) throws MarketException {
        if (!namesToLoginInfo.containsKey(name))
        {
            ErrorLog.getInstance ().Log ( "Cannot add a personal query cause there is no such user in the system " );
            throw new MarketException("Cannot add a personal query cause there is no such user in the system ");
        }
        if (userAdditionalQueries==null||userAdditionalAnswers==null||userAdditionalAnswers.equals("")||userAdditionalQueries.equals(""))
        {
            ErrorLog.getInstance().Log("User tried to add query with empty/null question/answer ");
            throw new MarketException("Question and answer cant be null or empty string");
        }
        else {
            LoginCard card = namesToLoginInfo.get(name);
            Map<String,String> QA = card.getQandA();
            QA.put(userAdditionalQueries,userAdditionalAnswers);
        }
    }

    public void setNamesToLoginInfo(Map<String, LoginCard> namesToLoginInfo) {
        this.namesToLoginInfo = namesToLoginInfo;
    }
}
