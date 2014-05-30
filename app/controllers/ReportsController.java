package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import models.AuditTrail;
import models.Employee;
import models.LoginForm;
import models.Medicine;
import models.User;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.AuditTrailManager;
import service.MedicineManager;
import service.impl.AuditTrailManagerImpl;
import service.impl.MedicineManagerImpl;
import views.html.employee;
import views.html.login;
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
    
    public static Result searchAuditTrails() {
    	List<AuditTrail> trails = auditTrailManager.findAll();
    	DynamicForm trailForm = Form.form().bindFromRequest();
		
		if (trailForm.hasErrors()) {
			return badRequest(auditTrails.render("Audit Trails",trails));
		} else {
			String dateFrom = trailForm.get("dateFrom");
			String dateTo = trailForm.get("dateTo");
			
			Date startDate = null;
			Date endDate = null;
			
			try 
			{
				startDate = convertStartDate(dateFrom, startDate);
				endDate = convertEndDate(dateTo, endDate);
			} catch (ParseException e) {
			    e.printStackTrace();
			}
			
//			trails = auditTrailManager.search("(?i)(.*)" + dateFrom + "(.*)");
			trails = auditTrailManager.search(getStartOfDay(startDate),getEndOfDay(endDate));
			return ok(auditTrails.render("Audit Trails",trails));
		}
		
    }

	private static Date convertStartDate(String strInput, Date date) throws ParseException {
		
		if (StringUtils.isEmpty(strInput) || strInput.equalsIgnoreCase("MM/DD/YYYY")) {
			strInput = "01/01/1901";						
		} 
		return new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(strInput);
	}
	
	private static Date convertEndDate(String strInput, Date date) throws ParseException {

		if (StringUtils.isEmpty(strInput) || strInput.equalsIgnoreCase("MM/DD/YYYY")) 
		{
			date = Calendar.getInstance().getTime();			
		} else {
			date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(strInput);	
		}
		return date;
	}
    
    private static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
    	if ( date!= null)
    	{
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
    	}
        return calendar.getTime();
    }

    private static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
    	if ( date!= null)
    	{
	        calendar.setTime(date);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.SECOND, 0);
	        calendar.set(Calendar.MILLISECOND, 0);
    	}
        return calendar.getTime();
    }
}
