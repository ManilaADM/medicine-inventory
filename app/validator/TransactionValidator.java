package validator;

import java.util.List;

import models.Employee;
import models.Medicine;
import models.Transaction;
import play.data.Form;

public class TransactionValidator
{

	@SuppressWarnings("unchecked")
	public void validate(Object obj, List<Employee> employees, List<Medicine> medicines) 
	{
		Form<Transaction> form = (Form<Transaction>) obj;
		
		
		EmployeeNameValidator empNameValidator = new EmployeeNameValidator();
		empNameValidator.validateEmployeeName(employees, form);
    	
		MedicineValidator medicineValidator = new MedicineValidator(); 
		medicineValidator.validateMedSupQty(form, medicines);
	}

}
