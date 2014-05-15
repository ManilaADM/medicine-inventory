package service;

import java.util.List;

import models.MedSupQty;
import models.Medicine;

import org.bson.types.ObjectId;

import exceptions.InsufficientCountException;

public interface MedicineManager {
	public Medicine findOne(ObjectId medId);

	public List<Medicine> findAll();
	
	public List<Medicine> fetchMedicalSupplies(int rowLimit);

	public void save(Medicine medicineObj);

	public void update(ObjectId objectId, Medicine medicineObj);

	public void delete(ObjectId id);

	public void updateMedicalSupply(String medId, int quantity, boolean b) throws InsufficientCountException;

	public List<Medicine> findRequestedMedicinesFromDB(
			List<ObjectId> medicineIDs);

	public void updateMedSupUponTransaction(MedSupQty medReq,
			List<Medicine> medicines) throws InsufficientCountException;

	public Medicine findRequestedMedicineFromDB(ObjectId objectId);
}
