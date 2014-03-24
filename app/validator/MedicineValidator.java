package validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.MedSupQty;
import models.Medicine;
import models.Transaction;
import play.Configuration;
import play.data.Form;
import dao.TransactionDAO;

public class MedicineValidator 
{
	private static TransactionDAO transactionDao = new TransactionDAO(Transaction.class);
	
	public void validateMedSupQty(Form<Transaction> form, List<Medicine> medicines) 
	{
		Transaction transaction = form.get();
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
    					if (Collections.frequency(medNames, medSupQtyBrandName) == 1) {
    						putFormError(form, "medicineInput" + i, Configuration.root().getString("error.medicine.duplicate"));
    					}
    					else {
    						putFormError(form, "medicineInput" + i, "");
    					}
        			}
    				else
    				{
    					String employeeName = transaction.getEmployeeName();
    					boolean isMedSupReqExceedMaxLimit = isMedSupReqExceedMaxLimit(medicines, employeeName, medSupQty);
        				boolean isMedSupReqExceedCount = isMedSupReqExceedCount(medicines, medSupQty);
        				if (employeeName != "" && employeeName != null && isMedSupReqExceedMaxLimit)
        				{
        					String errorMsg = Configuration.root().getString("error.medSupQty.request.exceed.qtyDailyLimit") + " " + medSupQtyBrandName;
    						putFormError(form, "requestMaxLimitError" + i, errorMsg);
        				}
        				if (isMedSupReqExceedCount)
        				{
        					String errorMsg = Configuration.root().getString("error.medSupQty.request.exceed.qtyDb") + " " + medSupQtyBrandName;
    						putFormError(form, "requestQtyError" + i, errorMsg);
        				}
        				medListToBeAdded.add(medSupQty);
    				}
    				medNames.add(medSupQtyBrandName);
    			}
    			else 
    			{
    				putFormError(form, "medicineInput" + i, Configuration.root().getString("error.medicine.no.matched"));
    			}
    		}
    	}
    	
    	if(medListToBeAdded.isEmpty())
    	{
    		putFormError(form, "medSupQty", Configuration.root().getString("error.medSupQty.are.empty"));
    	}
    	else if(!form.hasErrors())
    	{
    		transaction.getMedSupItems().clear();
    		transaction.getMedSupItems().addAll(medListToBeAdded);
    	}
	}
	
	private void putFormError(Form<Transaction> form, String errorKey, String errorMessage)
	{
		form.reject(errorKey, errorMessage);
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
	
	private boolean isMedSupReqExceedMaxLimit(List<Medicine> medicines, String employeeName, MedSupQty medSupReq)
	{
		boolean isMedSupReqExceedMaxLimit = false;
		
		medLoop: for(Medicine medicine : medicines)
		{
			String medSupId = medicine.getId().toString();
			String medSupReqId = medSupReq.getId();
			if(medicine.isQuantifiable() && (medSupId.equals(medSupReqId) || medSupId == medSupReqId))
			{
				int todaysDoneRequest = transactionDao.countEmpDoneMedRequestInCurrentDate(employeeName, medSupId);
				int medSupDailyQtyLimitPerUser = medicine.getDailyQtyLimitPerUser();
				if (todaysDoneRequest + medSupReq.getQuantity() > medSupDailyQtyLimitPerUser)
				{
					isMedSupReqExceedMaxLimit = true;
				}
				break medLoop;
			}
		}
		return isMedSupReqExceedMaxLimit;
	}
	
	private boolean isMedSupReqExceedCount(List<Medicine> medicines, MedSupQty medSupReq)
	{
		boolean isMedSupReqExceedCount = false;
		
		medLoop: for(Medicine medicine : medicines)
		{
			String medSupId = medicine.getId().toString();
			String medSupReqId = medSupReq.getId();
			if(medSupId.equals(medSupReqId) || medSupId == medSupReqId)
			{
				if (medSupReq.getQuantity() > medicine.getCount())
				{
					isMedSupReqExceedCount = true;
				}
				break medLoop;
			}
		}
		return isMedSupReqExceedCount;
	}

}
