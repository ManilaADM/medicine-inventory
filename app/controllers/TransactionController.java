package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Employee;
import models.Transaction;
import models.dto.TransactionVO;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.transaction;
import dao.JongoDAO;
import dao.TransactionDAO;

public class TransactionController extends Controller {
	
	static JongoDAO<Employee> employeeDao = new JongoDAO<Employee>(Employee.class);
	static TransactionDAO transactionDao = new TransactionDAO(Transaction.class);
	
    public static Result getTransactions() {
    	int rowLimit = Integer.parseInt(Configuration.root().getString("transaction.table.rowLimit"));
    	List<TransactionVO> medLogs = transactionDao.sortBy("timeStamp", false, rowLimit);
    	
    	List<Employee> employees = employeeDao.findAll();
		List<String> employeeNames = getEmployeeNames(employees);
    	
    	return ok(transaction.render(medLogs, rowLimit, employeeNames));
	 }
    
    public static Result returnMedSupply() {
    	
    	return getTransactions();
    }
    
    private static List<String> getEmployeeNames(List<Employee> employees) {
		List<String> employeeNames = new ArrayList<String>();
		for (Employee employee : employees) 
		{
			employeeNames.add(employee.getFirstName() + " "
					+ employee.getLastName());
		}

		return employeeNames;
	}
}
