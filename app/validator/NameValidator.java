package validator;

import java.util.List;

import models.Employee;
import models.Transaction;
import play.Configuration;
import play.data.Form;

public class NameValidator 
{
	
	private static final String NUMERIC_PATTERN = "(.*)([^A-Za-z()\t])(.*)";
	private static final String EMPLOYEE_NAME_ID = "employeeNameId";

	public void validateName(List<Employee> employees, Form<Transaction> form, List<String> errorKeys) 
	{
		Transaction transactionForm = form.get();
		String name = transactionForm.getEmployeeName();
    	if(name.isEmpty())
    	{
    		form.reject(EMPLOYEE_NAME_ID, Configuration.root().getString("error.empName.is.mandatory"));
    		errorKeys.add(EMPLOYEE_NAME_ID);
    	} 
    	else if (transactionForm.isVisitor()) {
    		validateVisitorName(form, errorKeys, name);
    	}
    	else {
    		validateEmployeeName(employees, form, errorKeys, name);
    	}
    	
	}

	private void validateEmployeeName(List<Employee> employees, Form<Transaction> form, List<String> errorKeys, String empName) {
		if(!hasMatchedEmployee(employees, empName))
    	{
    		form.reject(EMPLOYEE_NAME_ID, Configuration.root().getString("error.empName.not.matched"));
    		errorKeys.add(EMPLOYEE_NAME_ID);
    	}
	}

	private void validateVisitorName(Form<Transaction> form, List<String> errorKeys, String empName) {
		if(empName.matches(NUMERIC_PATTERN)) {
    		form.reject(EMPLOYEE_NAME_ID, Configuration.root().getString("error.empName.invalid.visitor"));
    		errorKeys.add(EMPLOYEE_NAME_ID);
    	}
	}
	
	private boolean hasMatchedEmployee(List<Employee> employees, String empName)
	{
		for(Employee employee : employees)
    	{
    		String employeeName = employee.getFirstName() + " " + employee.getLastName();
    		if(employeeName.equalsIgnoreCase(empName))
    		{
    			return true;
    		}
    	}
		return false;
	}
}
