package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;

public class Application extends Controller {

	@Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
}
