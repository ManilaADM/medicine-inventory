package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Employee;
import models.Medicine;
import models.Transaction;
import models.dto.TransactionVO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.Configuration;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import validator.TransactionValidator;
import views.html.transaction;
import dao.JongoDAO;
import dao.TransactionDAO;

public class TransactionController extends Controller {

	private static Logger log = LoggerFactory.getLogger(TransactionController.class);
	
	private static JongoDAO<Employee> employeeDao = new JongoDAO<>(Employee.class);
	private static TransactionDAO transactionDao = new TransactionDAO(Transaction.class);
	private static JongoDAO<Medicine> medicineDao = new JongoDAO<>(Medicine.class);
	
	
	@Security.Authenticated(Secured.class)
    public static Result getTransactions() {
    	int rowLimit = Integer.parseInt(Configuration.root().getString("transaction.table.rowLimit"));
    	log.debug("Current transaction row limit is :" + rowLimit);
    	List<TransactionVO> medLogs = transactionDao.sortBy("timeStamp", false, rowLimit);
    	
    	List<Employee> employees = employeeDao.findAll();
		String employeeNames = getEmployeeNames(employees);
		
		List<Medicine> medicines = medicineDao.findAll();
		String medicinesJson = getMedicinesJson(medicines);
		
		Form<Transaction> transactionForm = Form.form(Transaction.class);
    	
    	return ok(transaction.render(medLogs, rowLimit, employeeNames, medicinesJson, transactionForm));
	 }
    
    public static Result returnMedSupply() {
    	
    	log.info("Returning med");
    	
    	return getTransactions();
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
    
    public static Result setTransaction(){
		
    	int rowLimit = Integer.parseInt(Configuration.root().getString("transaction.table.rowLimit"));
    	List<TransactionVO> medLogs = transactionDao.sortBy("timeStamp", false, rowLimit);
    	
    	List<Employee> employees = employeeDao.findAll();
		String employeeNames = getEmployeeNames(employees);
		
		List<Medicine> medicines = medicineDao.findAll();
		String medicinesJson = getMedicinesJson(medicines);
		
		
		Form<Transaction> transactionForm = Form.form(Transaction.class).bindFromRequest();
		TransactionValidator transactionValidator = new TransactionValidator();
		transactionValidator.validate(transactionForm, employees, medicines);
		
		
		if(transactionForm.hasErrors()) {
			return badRequest(transaction.render(medLogs, rowLimit, employeeNames, medicinesJson, transactionForm));
	    }
		else {
			Transaction transactionObj = transactionForm.get();
			transactionObj.setTimeStamp(new Date());
			if(transactionObj.getId() == null){
				transactionDao.save(transactionObj);
			}else{
				transactionDao.update(transactionObj.getId(), transactionObj);
			}
		}
		return redirect(routes.TransactionController.getTransactions());
	}
    
}
