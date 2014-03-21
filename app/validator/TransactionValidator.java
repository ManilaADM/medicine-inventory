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
	public void validate(Object obj, List<Employee> employees, List<Medicine> medicines, List<String> errorKeys) 
	{
		Form<Transaction> form = (Form<Transaction>) obj;
		
		try
		{
			NameValidator nameValidator = new NameValidator();
			nameValidator.validateName(employees, form, errorKeys);
			
			MedicineValidator medicineValidator = new MedicineValidator();
			medicineValidator.validateMedSupQty(form, medicines, errorKeys);
		}
		catch (Exception e){
			form.reject("processingError", Configuration.root().getString("error.generic"));
			errorKeys.add("processingError");
		}
	}

}
