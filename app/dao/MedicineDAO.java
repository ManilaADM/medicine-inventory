package dao;

import java.util.List;

import models.MedSupQty;
import models.Medicine;

import org.bson.types.ObjectId;

import utilities.MedicineUtility;

import com.google.common.collect.Lists;

public class MedicineDAO extends JongoDAO<Medicine> {

	private MedicineUtility medUtility = new MedicineUtility();
	private static final String COUNT_FIELD = "count";
	private static final String AVAILABLE_FIELD = "available";
	
	public MedicineDAO(Class<Medicine> clazz)
	{
		super(clazz);
	}
	
	/**
	 * This operation returns a list of medical supplies from database fetched using a list of objectIds.
	 * @param medicineIDs - list of medical supplies' objectIds
	 */
	public List<Medicine> findRequestedMedicinesFromDB(List<ObjectId> medicineIDs)
	{
		return Lists.newArrayList(collections.find("{ _id: { $in: # } }", medicineIDs).as(Medicine.class));
	}
	
	/**
	 * This operation updates "count" and "available" fields of quantifiable medical supply upon transaction.
	 * @param medReq - requested medical supply
	 * @param medicines - filtered list of medicines from database
	 */
	public void updateMedSupUponTransaction(MedSupQty medReq, List<Medicine> medicines)
	{
		for(Medicine medicine : medicines)
		{
			String medReqIdStr = medReq.getId();
			String medIdStr = medicine.getId().toString();
			if((medIdStr.equals(medReqIdStr) || medIdStr == medReqIdStr) && medicine.isQuantifiable())
			{
				Integer medReqQty = medReq.getQuantity();
				boolean isAvailable = medUtility.isMedAvailable(medReqQty, medicine.getCount());				
				collections.update(new ObjectId(medReq.getId())).with("{$inc: { " + COUNT_FIELD + ": " + -medReqQty + " }, " +
						"$set: { " + AVAILABLE_FIELD + ": " + isAvailable + "}}");
				break;
			}
		}
	}
	
	/**
	 * Helper method to update a medicine's inventory count and availability status
	 * @param medId
	 * @param quantity
	 * @param isAvailable
	 */
	public void updateMedicalSupply(String medId, int quantity, boolean isAvailable) 
	{
		update(new ObjectId(medId),"$inc:{" + COUNT_FIELD +": #}, $set:{" + AVAILABLE_FIELD + ": #}", quantity, isAvailable);
	}

}