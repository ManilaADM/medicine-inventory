package validator;

import java.util.ArrayList;
import java.util.List;

import dao.TransactionDAO;
import models.MedSupQty;
import models.Medicine;
import models.Transaction;
import play.Configuration;
import play.data.Form;

public class MedicineValidator 
{
	private static TransactionDAO transactionDao = new TransactionDAO(Transaction.class);
	
	public void validateMedSupQty(Form<Transaction> form, List<Medicine> medicines) 
	{
		Transaction transaction = form.get();
		String employeeName = form.get().getEmployeeName();
		List<MedSupQty> medListToBeAdded = new ArrayList<MedSupQty>();
		List<String> medNames = new ArrayList<String>();
    	for(int i = 0; i < transaction.getMedSupItems().size(); i++)
    	{
    		MedSupQty medSupQty = transaction.getMedSupItems().get(i);
    		String medSupQtyBrandName = medSupQty.getBrandName();
			int medSupQtyQty = medSupQty.getQuantity();
			if(!medSupQtyBrandName.isEmpty() && medSupQtyQty != 0)
    		{
    			boolean isValidMedicine = isValidMedicine(medicines, medSupQtyBrandName);
    			if(isValidMedicine)
    			{
    				if (medNames.contains(medSupQtyBrandName)) 
        			{
        				form.reject("medicineInput" + i, Configuration.root().getString("error.medicine.duplicate"));
        			}
    				else
    				{
    					medListToBeAdded.add(medSupQty);
    					medNames.add(medSupQtyBrandName);
    				}
    				
    				ArrayList<String> medBrandNamesThatReachedMaxLimit = getMedBrandNamesThatReachedMaxLimit(medicines, medSupQtyBrandName, employeeName);
    				ArrayList<String> medBrandNamesThatExceedCount = getMedBrandNamesThatExceedCount(medicines, medSupQtyBrandName, medSupQtyQty);
    				if (employeeName != "" && employeeName != null && !medBrandNamesThatReachedMaxLimit.isEmpty())
    				{
    					for (String brandName : medBrandNamesThatReachedMaxLimit)
    					{
    						String errorMsg = Configuration.root().getString("error.medSupQty.request.exceed.qtyDailyLimit") + " " + brandName;
    						form.reject("requestMaxLimitError", errorMsg);
    					}
    				}
    				if (!medBrandNamesThatExceedCount.isEmpty())
    				{
    					for (String brandName : medBrandNamesThatReachedMaxLimit)
    					{
    						String errorMsg = Configuration.root().getString("error.medSupQty.request.exceed.inventory") + " " + brandName;
    						form.reject("requestQtyError", errorMsg);
    					}
    				}
    			}
    			else 
    			{
    				form.reject("medicineInput" + i, Configuration.root().getString("error.medicine.no.matched"));
    			}
    		}
    	}
    	
    	if(medListToBeAdded.isEmpty())
    	{
    		form.reject("medSupQty", Configuration.root().getString("error.medSupQty.are.empty"));
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
	
	private ArrayList<String> getMedBrandNamesThatReachedMaxLimit(List<Medicine> medicines, String medSupQtyBrandName, String employeeName)
	{
		ArrayList<String> medBrandNamesThatReachedMaxLimit = new ArrayList<String>();
		
		for(Medicine medicine : medicines)
		{
			String medBrandName = medicine.getBrandName();			
			if(medBrandName.equalsIgnoreCase(medSupQtyBrandName))
			{
				long todaysDoneRequest = transactionDao.countEmpMedDoneRequestInCurrentDate(employeeName, medSupQtyBrandName);
				int medSupDailyQtyLimitPerUser = medicine.getDailyQtyLimitPerUser();
				if (todaysDoneRequest >= medSupDailyQtyLimitPerUser)
				{
					medBrandNamesThatReachedMaxLimit.add(medSupQtyBrandName);
				}
			}
		}
		return medBrandNamesThatReachedMaxLimit;
	}
	
	private ArrayList<String> getMedBrandNamesThatExceedCount(List<Medicine> medicines, String medSupQtyBrandName, int medSupQty)
	{
		ArrayList<String> medBrandNamesThatExceedCount = new ArrayList<String>();
		
		for(Medicine medicine : medicines)
		{
			String medBrandName = medicine.getBrandName();			
			if(medBrandName.equalsIgnoreCase(medSupQtyBrandName))
			{
				if (medSupQty > medicine.getCount())
				{
					medBrandNamesThatExceedCount.add(medSupQtyBrandName);
				}
			}
		}
		return medBrandNamesThatExceedCount;
	}

}
