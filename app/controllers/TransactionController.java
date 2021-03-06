package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.ActionDoneType;
import models.Employee;
import models.MedSupQty;
import models.Medicine;
import models.Transaction;
import models.dto.TransactionVO;

import org.apache.commons.mail.EmailException;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import play.Configuration;
import play.Logger;
import play.Routes;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.EmployeeManager;
import service.MedicineManager;
import service.TransactionManager;
import service.impl.EmployeeManagerImpl;
import service.impl.MedicineManagerImpl;
import service.impl.TransactionManagerImpl;
import validator.TransactionValidator;
import views.html.transaction;
import action.SendEmailAlertAction;

import com.fasterxml.jackson.databind.node.ObjectNode;

import exceptions.InsufficientCountException;

public class TransactionController extends Controller {

	private static final String VISITOR = "(Visitor)";
	
	private static String TXN_ROW_LIMIT = Configuration.root().getString("transaction.table.rowLimit");;
	private static String SORT_BY_FIELD = Configuration.root().getString("transaction.table.sortBy");;
	
	private static EmployeeManager employeeManager = new EmployeeManagerImpl();
	private static TransactionManager transactionManager = new TransactionManagerImpl();
	private static MedicineManager medicineManager = new MedicineManagerImpl();
	private static AuditTrailProcessor auditTrailProcessor = new AuditTrailProcessor();
	private static SendEmailAlertAction sendEmailAction = new SendEmailAlertAction();
	
	@Security.Authenticated(Secured.class)
    public static Result getTransactions() {

		List<TransactionVO> medLogs = transactionManager.fetchTransactions(SORT_BY_FIELD, false, TXN_ROW_LIMIT);
    	
    	List<Employee> employees = employeeManager.findAll();
		String employeeNames = getEmployeeNames(employees);
		
		List<Medicine> medicines = medicineManager.findAll();
		String medicinesJson = getMedicinesJson(medicines);
		
		Form<Transaction> transactionForm = Form.form(Transaction.class);
    	
    	return ok(transaction.render(medLogs, employeeNames, medicinesJson, transactionForm));
	 }

	private static boolean isVisitor(String visitorName) {
		return "visitor".equals(visitorName);
	}
    
    public static Result returnMedSupply(String txnId, String medId, int quantity) throws InsufficientCountException {

    	Logger.debug("Cancelling [transaction id: "+ txnId + ", medsup id: " + medId + ", qty: " + quantity);

    	ObjectNode result = Json.newObject();
    	boolean foundRecordToUpdate = false;
    	
    	if(transactionManager.cancelMedSupItemRequest(txnId, medId)) {
    		medicineManager.updateMedicalSupply(medId, quantity, true);
    		foundRecordToUpdate = true;
    		Transaction transactionId = transactionManager.findOne(new ObjectId (txnId));
			auditTrailProcessor.saveTransactionInAuditTrail(transactionId, new ObjectId(medId), ActionDoneType.Returned);
    	}
    	result.put("ok", foundRecordToUpdate);
    	return ok(result);
//    	return badRequest();
    }
    
    public static Result javascriptRoutes() {
    	response().setContentType("text/javascript");
    	return ok(
    			Routes.javascriptRouter("jsRoutes",
    					// Routes
    					routes.javascript.TransactionController.returnMedSupply()
    					)
    			);
    }
    
    private static String getEmployeeNames(List<Employee> employees) {
		List<String> employeeNames = new ArrayList<String>();
		for (Employee employee : employees) 
		{
			employeeNames.add(employee.getFirstName() + " "
					+ employee.getLastName());
		}
		
		JSONArray jsonArray = new JSONArray(employeeNames);

		return jsonArray.toString();
	}
    
    private static String getMedicinesJson(List<Medicine> medicines) {
		Map<String, Medicine> medicinesMap = new HashMap<String, Medicine>();
		for (Medicine medicine : medicines) 
		{
			if(medicine.isAvailable())
			{
				medicinesMap.put(medicine.getBrandName(), medicine);
			}
			
		}
		
		JSONObject jsonObject = new JSONObject(medicinesMap);

		return jsonObject.toString();
	}
    
    public static List<ObjectId> findRequestedMedicinesIDs(List<MedSupQty> medSupQtyList)
	{
    	List<ObjectId> medicineIDs = new ArrayList<ObjectId>();
    	for (MedSupQty medSupQty : medSupQtyList)
		{
    		String medSupId = medSupQty.getId();
			if (medSupId != "" && medSupId != null && !medSupId.isEmpty())
			{
    			medicineIDs.add(new ObjectId(medSupId));
    		}
		}
    	return medicineIDs;
	}
    
    public static Result setTransaction(){
		
    	List<TransactionVO> medLogs = transactionManager.fetchTransactions(SORT_BY_FIELD, false, TXN_ROW_LIMIT);
    	
    	List<Employee> employees = employeeManager.findAll();
		String employeeNames = getEmployeeNames(employees);
		
		Form<Transaction> transactionForm = Form.form(Transaction.class).bindFromRequest();
		List<ObjectId> medicineIDs = findRequestedMedicinesIDs(transactionForm.get().getMedSupItems());
		List<Medicine> medicines = medicineManager.findRequestedMedicinesFromDB(medicineIDs);
		
		TransactionValidator transactionValidator = new TransactionValidator();
		String vistorName = transactionForm.get().getVisitorName();
		
		transactionValidator.validate(transactionForm, employees, medicines);

		if (isVisitor(vistorName)) {
			
			transactionForm.get().setEmployeeName(VISITOR + transactionForm.get().getEmployeeName());
		}
		
		if(transactionForm.hasErrors()) {
			List<Medicine> allMedicines = medicineManager.findAll();
			String medicinesJson = getMedicinesJson(allMedicines);
			return badRequest(transaction.render(medLogs, employeeNames, medicinesJson, transactionForm));
	    }
		else {
			Transaction transactionObj = transactionForm.get();
			transactionObj.setTimeStamp(new Date());
			if(transactionObj.getId() == null){
				try 
				{
					for (MedSupQty medReq : transactionObj.getMedSupItems())
					{
						medicineManager.updateMedSupUponTransaction(medReq, medicines);
						sendEmailNotification(medReq);
					}
					transactionManager.save(transactionObj);
					auditTrailProcessor.saveTransactionInAuditTrail(transactionObj, null, ActionDoneType.Taken);
				}
				catch (InsufficientCountException e)
				{
					transactionForm.reject("requestQtyError", e.getMessage());
					return getBadRequestStatus(medLogs, employeeNames, transactionForm);
				}
				catch (Exception e)
				{
					transactionForm.reject("savingError", Configuration.root().getString("error.generic"));
					return getBadRequestStatus(medLogs, employeeNames, transactionForm);
				}
			}else{
				transactionManager.update(transactionObj.getId(), transactionObj);
			}
		}
		return redirect(routes.TransactionController.getTransactions());
	}
    
    private static Result getBadRequestStatus(List<TransactionVO> medLogs, String employeeNames, Form<Transaction> transactionForm)
	{
		List<Medicine> allMedicines = medicineManager.findAll();
		String medicinesJson = getMedicinesJson(allMedicines);
		return badRequest(transaction.render(medLogs, employeeNames, medicinesJson, transactionForm));
	}
    
	private static void sendEmailNotification(MedSupQty medReq)
	{
		try
		{
			Medicine updatedMedicine = medicineManager.findRequestedMedicineFromDB(new ObjectId(medReq.getId()));
			sendEmailAction.sendEmail(updatedMedicine);
		}
		catch (EmailException e)
		{
			Logger.warn("Unable to send email notification", e);
		}
	}    
}
