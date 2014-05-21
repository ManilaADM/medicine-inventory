package controllers;

import java.util.List;

import models.AuditTrail;
import models.Employee;
import models.Medicine;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.AuditTrailManager;
import service.MedicineManager;
import service.impl.AuditTrailManagerImpl;
import service.impl.MedicineManagerImpl;
import views.html.employee;
import views.html.medicineInventory;
import views.html.reports;
import views.html.auditTrails;

public class ReportsController extends Controller {
	
	private static MedicineManager medicineManager = new MedicineManagerImpl();
	private static AuditTrailManager auditTrailManager = new AuditTrailManagerImpl();

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
    }
    
    public static Result getMedSupCritical() {
        return ok(reports.render());
    }
    
    public static Result getAuditTrails() {
    	List<AuditTrail> trails = auditTrailManager.findAll();
		return ok(auditTrails.render("Audit Trails",trails));
    }
}
