package features;

import java.util.List;
import pages.LoginPage;
import database.api.DBApi;
import database.model.User;

public class Correctness {
	//baza danych 
	DBApi dbapi = LoginPage.dbapi;
	public boolean checkFillOfFields(String... values){
		for(String s:values){  
			if(checkIsFieldEmpty(s))
				return false;
		}
		return true;
	}
	public boolean checkIsFieldEmpty(String field){
		if(field == null  || field.trim() == ""){
			return true;
		}
		else return false;
	}	
//sprawdzanie loginu
    public boolean isLoginAvailable(String login){
    	List<User> users = dbapi.selectUsers();
        
                System.out.println("metoda isLoginAvailable !");
                System.out.println("Lista userów: ");
        for(User u: users){
        	System.out.println(u);
        	if(login.equals(u.getLogin())){
        		System.out.println(Communicates.messagesArray[2]);
        		return false;
        	}
        }        	
    	return true;
    }
	//sprawdzanie logowania - wpisanych danych
	public boolean checkIsInputCorrect(String login, String password){
		
		List<User> users = dbapi.selectUsers();
	    
                                System.out.println("checkIsInputCorrect!");
	    for(User u: users){
	    	if(login.equals(u.getLogin())){
                                System.out.println("Login istnieje!");
	    		if(password.equals(u.getPassword())){
	    			System.out.println(Communicates.messagesArray[3]);
	    			return true;
	    		}else{
	    			System.out.println(Communicates.messagesArray[4]);	    			
	    		} 
	    	}
	    }        	
		return false;
	}
}