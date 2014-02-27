package controllers;

import static play.data.Form.*;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.login;
import play.data.*;
import models.User;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result login() {
    	return ok(login.render(Form.form(Login.class)));
    }
    
    public static class Login {

        public String email;
        public String password;
        
	    public String validate() {
	    	if (User.authenticate(email, password) == null) {
	    		return "Invalid user or password";
	    	}
	    	return null;
	    }
    }
    
    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        return ok();
    }
    
    
}
