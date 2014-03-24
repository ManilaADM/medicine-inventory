package validator;

import java.util.List;

import models.Employee;
import models.Transaction;
import play.Configuration;
import play.data.Form;

public class NameValidator 
{
	
	private static final String VISITOR = "visitor";
	private static final String NON_ALPHA_SPACE_PATTERN = "(.*)([^A-Za-z\\s])(.*)";
	private static final String EMPLOYEE_NAME_ID = "employeeNameId";

	public void validateName(List<Employee> employees, Form<Transaction> form) 
	{
		Transaction transactionForm = form.get();
		String name = transactionForm.getEmployeeName();
    	if(name.isEmpty())
    	{
    		form.reject(EMPLOYEE_NAME_ID, Configuration.root().getString("error.empName.is.mandatory"));
    	} else {
			String visitorName = transactionForm.getVisitorName();
			
			if (VISITOR.equals(visitorName)) {
				validateVisitorName(form, name);
			}
			else {
				validateEmployeeName(employees, form, name);
			}
		}
    	
	}

	private void validateEmployeeName(List<Employee> employees, Form<Transaction> form, String name) {
		if(!hasMatchedEmployee(employees, name))
    	{
    		form.reject(EMPLOYEE_NAME_ID, Configuration.root().getString("error.empName.not.matched"));
    	}
	}

	private void validateVisitorName(Form<Transaction> form, String name) {
		if(name.matches(NON_ALPHA_SPACE_PATTERN)) {
    		form.reject(EMPLOYEE_NAME_ID, Configuration.root().getString("error.empName.invalid.visitor"));
    	}
	}
	
	private boolean hasMatchedEmployee(List<Employee> employees, String name)
	{
		for(Employee employee : employees)
    	{
    		String employeeName = employee.getFirstName() + " " + employee.getLastName();
    		if(employeeName.equalsIgnoreCase(name))
    		{
    			return true;
    		}
    	}
		return false;
	}
}
