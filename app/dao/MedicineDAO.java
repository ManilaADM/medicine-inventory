package dao;

import java.util.List;

import models.MedSupQty;
import models.Medicine;

import org.bson.types.ObjectId;

import play.Configuration;
import utilities.MedicineUtility;

import com.google.common.collect.Lists;

import exceptions.InsufficientCountException;

public class MedicineDAO extends JongoDAO<Medicine> {

	private MedicineUtility medUtility = new MedicineUtility();
	private static final String COUNT_FIELD = "count";
	private static final String AVAILABLE_FIELD = "available";
	
	public MedicineDAO(Class<Medicine> clazz)
	{
		super(clazz);
	}
	
	@Override
	public List<Medicine> findAll(){
		return Lists.newArrayList(collections.find().sort("{brandName: 1, count: 1}").as(clazz));
	}
	
	/**
	 * This operation returns a list of medical supplies from database fetched using a list of objectIds.
	 * @param medicineIDs - list of medical supplies' objectIds
	 */
	public List<Medicine> findRequestedMedicinesFromDB(List<ObjectId> medicineIDs)
	{
		return Lists.newArrayList(collections.find("{ _id: { $in: # } }", medicineIDs).as(Medicine.class));
	}
	
	public Medicine findRequestedMedicineFromDB(ObjectId medicineID)
	{
		return collections.findOne(medicineID).as(Medicine.class);
	}
	
	/**
	 * This operation updates "count" and "available" fields of quantifiable medical supply upon transaction.
	 * @param medReq - requested medical supply
	 * @param medicines - filtered list of medicines from database
	 * @throws InsufficientCountException 
	 */
	public void updateMedSupUponTransaction(MedSupQty medReq, List<Medicine> medicines) throws InsufficientCountException
	{
		for(Medicine medicine : medicines)
		{
			String medReqIdStr = medReq.getId();
			String medIdStr = medicine.getId().toString();
			if((medIdStr.equals(medReqIdStr) || medIdStr == medReqIdStr) && medicine.isQuantifiable())
			{
				Integer medReqQty = medReq.getQuantity();
				updateMedicalSupply(medReq.getId(), medReqQty, false);
			}
			break;
		}
	}
	
	/**
	 * Helper method to update a medicine's inventory count and availability status
	 * @param medId
	 * @param quantity
	 * @param isAvailableAfterTransaction
	 * @throws InsufficientCountException 
	 */
	public synchronized void updateMedicalSupply(String medId, int quantity, boolean isAvailableAfterTransaction) throws InsufficientCountException 
	{
		if (!isAvailableAfterTransaction)
		{
			Medicine med = findRequestedMedicineFromDB(new ObjectId(medId));
			int medCount = med.getCount();
			boolean isMedReqExceedCount = medUtility.isMedReqExceedCount(quantity, medCount);
			if (isMedReqExceedCount)
			{
				throw new InsufficientCountException(Configuration.root().getString("error.medSupQty.request.exceed.qtyDb") + " " + med.getBrandName());
			}
			isAvailableAfterTransaction = medUtility.isMedAvailableAfterTransaction(quantity, medCount);
			quantity *= -1;
		}
		
		update(new ObjectId(medId),"$inc:{" + COUNT_FIELD +": #}, $set:{" + AVAILABLE_FIELD + ": #}", quantity, isAvailableAfterTransaction);
	}
	
	/**
	 * Retrieves a list of medical supplies where maximum size of list is the rowLimit.
	 * @param rowLimit
	 * @return list of medical supplies
	 */
	public List<Medicine> fetchMedicalSupplies(int rowLimit) {
		return  Lists.newArrayList(collections.find().limit(rowLimit).as(Medicine.class));
	}

}