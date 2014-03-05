package controllers;

import dao.JongoDAO;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

public class UserController extends Controller {
	
	static JongoDAO<User> userDao = new JongoDAO<>(User.class);
	
	public static Result login() {
    	return ok(login.render(Form.form(Login.class)));
    }
    
    public static class Login {

        public String email;
        public String password;
        
	    public String validate() {
	    	if (UserController.authenticate(email, password)) {
	    		return "Invalid user or password";
	    	}
	    	return null;
	    }
    }
    
    public static boolean authenticate(String email, String password) {
    	if ((userDao.searchOne("email", email) == null) || (userDao.searchOne("password", password) == null))
    	{
    		return true;
    	}
    	return false;
    }
    
    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                routes.Application.index()
            );
        }
    }
    
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.UserController.login()
        );
    }

}
