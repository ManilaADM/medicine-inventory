package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import models.AuditTrail;
import models.MedConsumption;
import models.Medicine;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.AuditTrailManager;
import service.MedicineManager;
import service.impl.AuditTrailManagerImpl;
import service.impl.MedicineManagerImpl;
import utilities.ConsumptionComparator;
import views.html.medicineInventory;
import views.html.medicineCriticalSupply;
import views.html.reports;
import views.html.auditTrails;
import views.html.monthlyConsumption;
import views.html.topTenConsumption;

public class ReportsController extends Controller {
	
	private static MedicineManager medicineManager = new MedicineManagerImpl();
	private static AuditTrailManager auditTrailManager = new AuditTrailManagerImpl();

	@Security.Authenticated(Secured.class)
    public static Result getReports() {
        return ok(reports.render());
    }
    
    public static Result searchConsumptionByDate() {
		
    	List<AuditTrail> trails = new ArrayList<AuditTrail>();
    	DynamicForm trailForm = Form.form().bindFromRequest();
		
		if (trailForm.hasErrors()) {
			return badRequest(monthlyConsumption.render("Consumption By Date",getSortedConsumption(trails, 0)));
		} else {
			trails = searchConsumption(trailForm);
			return ok(monthlyConsumption.render("Consumption By Date",getSortedConsumption(trails, 0)));
		}
    }

	private static List<MedConsumption> getSortedConsumption(List<AuditTrail> trails, int limit) {
		List<MedConsumption> medConsumptionList = new ArrayList<MedConsumption>();
		List<Medicine> medicines = medicineManager.findAll();
		for (Medicine medicine : medicines)
		{
			MedConsumption consumption = retrieveConsumptionFromTrail(medicine, trails);
			medConsumptionList.add(consumption);
			
		}
		Collections.sort(medConsumptionList, new ConsumptionComparator());
		if (limit == 0) 
		{
			return medConsumptionList;
		} else if (limit >= medConsumptionList.size() )
		{
			return medConsumptionList.subList(0, medConsumptionList.size());
		}
		return medConsumptionList.subList(0, limit);
	}

	private static MedConsumption retrieveConsumptionFromTrail(Medicine medicine, List<AuditTrail> trails) {
		
		MedConsumption consumption = new MedConsumption();
		int takenQty = 0;
		int returnedQty = 0;
		
		for(AuditTrail trail: trails){
			if(trail.getMedSupBrandName().equals(medicine.getBrandName()))
			{
				if(trail.getActionDone().equals("Taken"))
				{
					takenQty += trail.getMedSupQty();
				} else {
					returnedQty += trail.getMedSupQty();
				}
				
			}
		}
		consumption.setBrandName(medicine.getBrandName());
		consumption.setGenericName(medicine.getGenericName());
		consumption.setTakenQty(takenQty);
		consumption.setReturnedQty(returnedQty);
		
		return consumption;
		
	}

	public static Result getTopTenMedUsed() {
    	List<AuditTrail> trails = new ArrayList<AuditTrail>();
    	DynamicForm trailForm = Form.form().bindFromRequest();
		
		if (trailForm.hasErrors()) {
			return badRequest(topTenConsumption.render("Top Ten",getSortedConsumption(trails, 20)));
		} else {
			trails = searchConsumption(trailForm);
			return ok(topTenConsumption.render("Top Ten",getSortedConsumption(trails, 20)));
		}
    }

	private static List<AuditTrail> searchConsumption(DynamicForm trailForm) {
		List<AuditTrail> trails;
		String dateFrom = trailForm.get("dateFrom");
		String dateTo = trailForm.get("dateTo");
		
		Date startDate = null;
		Date endDate = null;
		
		try 
		{
			startDate = getStartOfMonth(dateFrom, startDate);
			endDate = convertEndDate(dateTo, endDate);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
//			trails = auditTrailManager.search("(?i)(.*)" + dateFrom + "(.*)");
		trails = auditTrailManager.search(getStartOfDay(startDate),getEndOfDay(endDate));
		return trails;
	}
    
    public static Result getMedSupInventory() {
		List<Medicine> medicines = medicineManager.findAll();
		return ok(medicineInventory.render("Medicine Inventory",medicines));
    }
    
    public static Result getMedSupCritical() {
		List<Medicine> medicines = medicineManager.fetchCriticalMedicalSupplies();
		return ok(medicineCriticalSupply.render("Critical Supply",medicines));
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
    
    
    private static Date getStartOfMonth(String strInput, Date date) throws ParseException {
		
    	if (StringUtils.isEmpty(strInput) || strInput.equalsIgnoreCase("MM/DD/YYYY")) 
		{
    		Calendar cal = Calendar.getInstance();
    		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			date = cal.getTime();
			
		} else {
			date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(strInput);	
		}
		return date;
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
