package controllers;

import java.util.List;

import models.Medicine;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.MedicineManager;
import service.impl.MedicineManagerImpl;
import views.html.medicineInventory;
import views.html.reports;

public class ReportsController extends Controller {
	
	private static MedicineManager medicineManager = new MedicineManagerImpl();

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
		List<Medicine> medicines = medicineManager.findAll();
		return ok(medicineInventory.render("Medicine Inventory",medicines));
//		return ok(reports.render());
    }
    
    public static Result getMedSupCritical() {
        return ok(reports.render());
    }
    
    public static Result getAuditTrails() {
        return ok(reports.render());
    }
}
