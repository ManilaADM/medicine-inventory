package controllers;

import models.LoginForm;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import dao.JongoDAO;
import views.html.login;

public class UserController extends Controller {

	static JongoDAO<User> userDao = new JongoDAO<>(User.class);
	static Form<LoginForm> loginForm = Form.form(LoginForm.class);

	public static Result login() {
		return ok(login.render(loginForm));
	}

	public static boolean authenticate(String email, String password) {
		if ((currentUserEmail(email) == null) || (currentUserPassword(password) == null)) {
			return true;
		}
		return false;
	}

	private static User currentUserPassword(String password) {
		return userDao.searchOne("password", password);
	}

	private static User currentUserEmail(String email) {
		return userDao.searchOne("email", email);
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
			return redirect(routes.Application.index());
		}
	}

	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.UserController.login());
	}

}
