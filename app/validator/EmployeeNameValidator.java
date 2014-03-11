package validator;

import java.util.List;

import models.Employee;
import models.Transaction;
import play.data.Form;

public class EmployeeNameValidator 
{
	
	public void validateEmployeeName(List<Employee> employees, Form<Transaction> form) 
	{
		Transaction transactionForm = form.get();
		String empName = transactionForm.getEmployeeName();
    	if(empName.isEmpty())
    	{
    		form.reject("employeeNameId","error.empName.is.mandatory");
    	}
    	else if(!hasMatchedEmployee(employees, empName))
    	{
    		form.reject("employeeNameId","error.empName.no.matched");
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
