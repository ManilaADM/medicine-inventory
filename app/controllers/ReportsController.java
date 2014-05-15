package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.reports;

public class ReportsController extends Controller {

	@Security.Authenticated(Secured.class)
    public static Result getReports() {
        return ok(reports.render());
    }
	
    public static Result getConsumption() {
        return ok(reports.render());
    }
    
    public static Result getTopTenMedUsed() {
        return ok(reports.render());
    }
    
    public static Result getMedSupInventory() {
        return ok(reports.render());
    }
    
    public static Result getMedSupCritical() {
        return ok(reports.render());
    }
    
    public static Result getAuditTrails() {
        return ok(reports.render());
    }
}
