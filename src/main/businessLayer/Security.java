package main.businessLayer;

import java.util.HashMap;
import java.util.List;

public class Security {
    //TODO - implement as singleton
    private HashMap<String,String> nameAndPasswords;
    private HashMap<String, List<Pair<String,String>>> nameAndQuestions;
    private static Security instance;

    private Security(){
        this.nameAndPasswords = new HashMap<>();
        this.nameAndQuestions = new HashMap<>();
    }
    public static Security getInstance() {
        if (instance == null)
            return new Security();
        else return instance;
    }



    public boolean collectDebt(){
        throw new UnsupportedOperationException();
    }
    public boolean supplyProducts(){throw new UnsupportedOperationException();}

    public void validateRegister(String name, String password, String validatedPassword,
                                 List<Pair<String, String>> securityQuestions) throws Exception {
        validateRegisterPassword(password,validatedPassword);
        validateName(name);


    }

    private void validateName(String name) throws Exception{
        if(nameAndPasswords.containsKey(name)|| name == null || name.equals(""))
            throw new Exception();
    }

    private void validateRegisterPassword(String password, String validatedPassword) throws Exception {
        if (!password.equals(validatedPassword))
            throw new Exception();
    }
    public void addNewMember(String name,String password,List<Pair<String,String>> securityQuestions)
    {
        nameAndPasswords.put(name,password);
        nameAndQuestions.put(name,securityQuestions);
    }

    public List<Pair<String, String>> validateLogin(String name, String password) throws Exception {
        if (!password.equals(nameAndPasswords.get(name)))
            throw new Exception();
        return nameAndQuestions.get(name);

    }
}
