package validator;

import java.util.ArrayList;
import java.util.List;

import models.MedSupQty;
import models.Medicine;
import models.Transaction;
import play.data.Form;

public class MedicineValidator 
{
	public void validateMedSupQty(Form<Transaction> form, List<Medicine> medicines) 
	{
		Transaction transaction = form.get();
		List<MedSupQty> medListToBeAdded = new ArrayList<MedSupQty>();
		List<String> medNames = new ArrayList<String>();
    	for(int i = 0; i < transaction.getMedSupItems().size(); i++)
    	{
    		MedSupQty medSupQty = transaction.getMedSupItems().get(i);
    		if(!medSupQty.getBrandName().isEmpty() && medSupQty.getQuantity() != 0)
    		{
    			boolean isValidMedicine = isValidMedicine(medicines, medSupQty.getBrandName());
    			if(isValidMedicine)
    			{
    				if (medNames.contains(medSupQty.getBrandName())) 
        			{
        				form.reject("medicineInput" + i,"error.medicine.duplicate");
        			}
    				else
    				{
    					medListToBeAdded.add(medSupQty);
    					medNames.add(medSupQty.getBrandName());
    				}
    			}
    			else 
    			{
    				form.reject("medicineInput" + i,"error.medicine.no.matched");
    			}
    		}
    	}
    	
    	if(medListToBeAdded.isEmpty())
    	{
    		form.reject("medSupQty", "error.medSupQty.are.empty");
    	}
    	else if(!form.hasErrors())
    	{
    		transaction.getMedSupItems().clear();
    		transaction.getMedSupItems().addAll(medListToBeAdded);
    	}
	}
	
	private boolean isValidMedicine(List<Medicine> medicines, String medSupQtyBrandName)
	{
		for(Medicine medicine:medicines)
		{
			String medBrandName = medicine.getBrandName();
			if(medBrandName.equalsIgnoreCase(medSupQtyBrandName))
			{
				return true;
			}
		}
		return false;
	}
}
