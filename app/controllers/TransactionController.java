package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Employee;
import models.MedSupQty;
import models.Medicine;
import models.Transaction;
import models.dto.TransactionVO;

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
import validator.TransactionValidator;
import views.html.transaction;

import com.fasterxml.jackson.databind.node.ObjectNode;

import dao.JongoDAO;
import dao.MedicineDAO;
import dao.TransactionDAO;

public class TransactionController extends Controller {

	private static String TXN_ROW_LIMIT = Configuration.root().getString("transaction.table.rowLimit");;
	private static String SORT_BY_FIELD = Configuration.root().getString("transaction.table.sortBy");;
	
	private static JongoDAO<Employee> employeeDao = new JongoDAO<>(Employee.class);
	private static TransactionDAO transactionDao = new TransactionDAO(Transaction.class);
	private static MedicineDAO medicineDao = new MedicineDAO(Medicine.class);
	
	@Security.Authenticated(Secured.class)
    public static Result getTransactions() {

		List<TransactionVO> medLogs = transactionDao.fetchTransactions(SORT_BY_FIELD, false, TXN_ROW_LIMIT);
    	
    	List<Employee> employees = employeeDao.findAll();
		String employeeNames = getEmployeeNames(employees);
		
		List<Medicine> medicines = medicineDao.findAll();
		String medicinesJson = getMedicinesJson(medicines);
		
		Form<Transaction> transactionForm = Form.form(Transaction.class);
		List<String> errorKeys = new ArrayList<>();
    	
    	return ok(transaction.render(medLogs, employeeNames, medicinesJson, transactionForm, errorKeys));
	 }

	private static boolean isVisitor(String employeeName) {
		return employeeName.matches("(.*)Visitor(.*)");
	}
    
    public static Result returnMedSupply(String txnId, String medId, int quantity) {

    	Logger.debug("Cancelling [transaction id: "+ txnId + ", medsup id: " + medId + ", qty: " + quantity);

    	ObjectNode result = Json.newObject();
    	boolean foundRecordToUpdate = false;
    	
    	if(transactionDao.cancelMedSupItemRequest(txnId, medId)) {
    		medicineDao.updateMedicalSupply(medId, quantity, true);
    		foundRecordToUpdate = true;
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
		
    	List<TransactionVO> medLogs = transactionDao.fetchTransactions(SORT_BY_FIELD, false, TXN_ROW_LIMIT);
    	
    	List<Employee> employees = employeeDao.findAll();
		String employeeNames = getEmployeeNames(employees);
		
		Form<Transaction> transactionForm = Form.form(Transaction.class).bindFromRequest();
		List<ObjectId> medicineIDs = findRequestedMedicinesIDs(transactionForm.get().getMedSupItems());
		List<Medicine> medicines = medicineDao.findRequestedMedicinesFromDB(medicineIDs);
		
		TransactionValidator transactionValidator = new TransactionValidator();
		List<String> errorKeys = new ArrayList<>();
		String employeeName = transactionForm.get().getEmployeeName();

		if (isVisitor(employeeName)) {
			transactionForm.get().setVisitor(true);
		}
		
		transactionValidator.validate(transactionForm, employees, medicines, errorKeys);
		
		if(transactionForm.hasErrors()) {
			List<Medicine> allMedicines = medicineDao.findAll();
			String medicinesJson = getMedicinesJson(allMedicines);
			return badRequest(transaction.render(medLogs, employeeNames, medicinesJson, transactionForm, errorKeys));
	    }
		else {
			Transaction transactionObj = transactionForm.get();
			transactionObj.setTimeStamp(new Date());
			if(transactionObj.getId() == null){
				try 
				{
					for (MedSupQty medReq : transactionObj.getMedSupItems())
					{
						medicineDao.updateMedSupUponTransaction(medReq, medicines);
					}
					transactionDao.save(transactionObj);
				}
				catch (Exception e)
				{
					transactionForm.reject("savingError", Configuration.root().getString("error.generic"));
				}
			}else{
				transactionDao.update(transactionObj.getId(), transactionObj);
			}
		}
		return redirect(routes.TransactionController.getTransactions());
	}
    
}
