package validator;

import java.util.List;

import models.Employee;
import models.Medicine;
import models.Transaction;
import play.Configuration;
import play.data.Form;

public class TransactionValidator
{

	@SuppressWarnings("unchecked")
	public void validate(Object obj, List<Employee> employees, List<Medicine> medicines) 
	{
		Form<Transaction> form = (Form<Transaction>) obj;
		
		try
		{
			NameValidator nameValidator = new NameValidator();
			nameValidator.validateName(employees, form);
			
			MedicineValidator medicineValidator = new MedicineValidator();
			medicineValidator.validateMedSupQty(form, medicines);
		}
		catch (Exception e){
			form.reject("processingError", Configuration.root().getString("error.generic"));
		}
	}

}
