package models;

import controllers.UserController;

public class LoginForm {
	
	public String email;
    public String password;
    
    public String validate() {
    	if (UserController.authenticate(email, password)) {
    		return "Invalid user or password";
    	}
    	return null;
    }

}
