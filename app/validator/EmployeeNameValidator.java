package validator;

import java.util.List;

import models.Employee;
import models.Transaction;
import play.Configuration;
import play.data.Form;

public class EmployeeNameValidator 
{
	
	private static final String EMPLOYEE_NAME_ID = "employeeNameId";

	public void validateEmployeeName(List<Employee> employees, Form<Transaction> form, List<String> errorKeys) 
	{
		Transaction transactionForm = form.get();
		String empName = transactionForm.getEmployeeName();
    	if(empName.isEmpty())
    	{
    		form.reject(EMPLOYEE_NAME_ID, Configuration.root().getString("error.empName.is.mandatory"));
    		errorKeys.add(EMPLOYEE_NAME_ID);
    	}
    	else if(!hasMatchedEmployee(employees, empName))
    	{
    		form.reject(EMPLOYEE_NAME_ID, Configuration.root().getString("error.empName.no.matched"));
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
