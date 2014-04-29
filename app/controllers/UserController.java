package controllers;

import models.LoginForm;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import service.UserManager;
import service.impl.UserManagerImpl;
import views.html.login;

public class UserController extends Controller {
	
	static UserManager userManager = new UserManagerImpl();
	static Form<LoginForm> loginForm = Form.form(LoginForm.class);

	public static Result login() {
		return ok(login.render(loginForm));
	}

	public static boolean isValidUser(String email, String password) {
		if (userManager.findUser(email, password) != null) {
			return true;
		}
		return false;
	}

	private static User currentUserEmail(String email) {
		return userManager.searchOne("email", email);
	}

	public static String name() {
		return session("name");
	}

	public static Result authenticate() {
		Form<LoginForm> loginForm = Form.form(LoginForm.class)
				.bindFromRequest();

		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			User user = currentUserEmail(loginForm.get().email);
			session().clear();
			session("email", loginForm.get().email);
			session("name", user.name);
			return redirect(routes.TransactionController.getTransactions());
		}
	}

	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.UserController.login());
	}

}
