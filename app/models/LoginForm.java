package models;

import play.Play;
import controllers.UserController;

public class LoginForm {
	
	public String email;
    public String password;
    
    public String validate() {
    	if (UserController.isValidUser(email, password)) {
    		return null;
    	}
    	return Play.application().configuration().getString("login.error.message.invalid");
    }

}
