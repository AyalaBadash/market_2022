package main.businessLayer;

import java.util.HashMap;
import java.util.List;

public class Security {
    private HashMap<String, LoginCard> namesToLoginInfo;

    private static Security instance;

    private Security() {
        this.namesToLoginInfo =  new HashMap<>();
    }

    public static Security getInstance() {
        if (instance == null)
            return new Security();
        else return instance;
    }

    public void validateRegister(String name, String password,
                                 List<String> questions,List<String> answers) throws Exception {
        validateRegisterPassword(password);
        validateName(name);
        validateQuestions(questions, answers);


    }
    // TODO need to document all exceptions
    private void validateQuestions(List<String> questions, List<String> answers) throws Exception {
        if (questions.size() != answers.size())
            throw new Exception();
    }

    private void validateName(String name) throws Exception {
        if (namesToLoginInfo.containsKey(name) || name == null || name.equals(""))
            throw new Exception();
    }

    private void validateRegisterPassword(String password) throws Exception {

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


}
