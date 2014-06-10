package service.impl;

import java.util.List;

import org.bson.types.ObjectId;

import dao.MedicineDAO;
import exceptions.InsufficientCountException;
import models.MedSupQty;
import models.Medicine;
import service.MedicineManager;

public class MedicineManagerImpl implements MedicineManager{
	private static MedicineDAO medicineDao = new MedicineDAO(Medicine.class);
	@Override
	public Medicine findOne(ObjectId medId) {
		// TODO Auto-generated method stub
		return medicineDao.findOne(medId);
	}
	@Override
	public List<Medicine> findAll() {
		// TODO Auto-generated method stub
		return medicineDao.findAll();
	}
	@Override
	public List<Medicine> fetchMedicalSupplies(int rowLimit) {
		// TODO Auto-generated method stub
		return medicineDao.fetchMedicalSupplies(rowLimit);
	}
	
	@Override
	public List<Medicine> fetchCriticalMedicalSupplies() {
		// TODO Auto-generated method stub
		return medicineDao.fetchCriticalMedicalSupplies();
	}
	
	@Override
	public void save(Medicine medicineObj) {
		// TODO Auto-generated method stub
		medicineDao.save(medicineObj);
	}
	@Override
	public void update(ObjectId objectId, Medicine medicineObj) {
		// TODO Auto-generated method stub
		medicineDao.update(objectId, medicineObj);
	}
	@Override
	public void delete(ObjectId id) {
		// TODO Auto-generated method stub
		medicineDao.delete(id);
	}
	@Override
	public void updateMedicalSupply(String medId, int quantity, boolean isAvailable) throws InsufficientCountException {
		medicineDao.updateMedicalSupply(medId, quantity, isAvailable);
	}
	@Override
	public List<Medicine> findRequestedMedicinesFromDB(
			List<ObjectId> medicineIDs) {
		// TODO Auto-generated method stub
		return medicineDao.findRequestedMedicinesFromDB(medicineIDs);
	}
	@Override
	public void updateMedSupUponTransaction(MedSupQty medReq,
			List<Medicine> medicines) throws InsufficientCountException {
		// TODO Auto-generated method stub
		medicineDao.updateMedSupUponTransaction(medReq, medicines);
	}
	@Override
	public Medicine findRequestedMedicineFromDB(ObjectId objectId) {
		// TODO Auto-generated method stub
		return medicineDao.findRequestedMedicineFromDB(objectId);
	}
	@Override
	public List<Medicine> search(String medicine) {
		return medicineDao.search(medicine);
	}

}
