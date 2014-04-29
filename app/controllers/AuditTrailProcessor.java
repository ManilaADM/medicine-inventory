package controllers;

import models.ActionDoneType;
import models.AuditTrail;
import models.MedSupQty;
import models.Transaction;

import org.bson.types.ObjectId;

import service.AuditTrailManager;
import service.MedicineManager;
import service.impl.AuditTrailManagerImpl;
import service.impl.MedicineManagerImpl;

public class AuditTrailProcessor {
	
	//private static JongoDAO<AuditTrail> auditTrailDao = new JongoDAO<>(AuditTrail.class);
	private static AuditTrailManager auditTrailManager = new AuditTrailManagerImpl();
	//private static MedicineDAO medicineDao = new MedicineDAO(Medicine.class);
	private static MedicineManager medicineManager = new MedicineManagerImpl();

	private static String loginUser = UserController.name();

	public void saveTransactionInAuditTrail(Transaction transaction, ObjectId medId, ActionDoneType actionDone)
	{
		AuditTrail transactionAuditTrail = new AuditTrail();
		transactionAuditTrail.setLogTimeStamp(transaction.getTimeStamp());
		transactionAuditTrail.setUserName(loginUser);
		transactionAuditTrail.setRequestorName(transaction.getEmployeeName());
		transactionAuditTrail.setActionDone(actionDone.toString());
		
		switch(actionDone)
		{
			case Taken:
			{
				for (MedSupQty medSup : transaction.getMedSupItems())
				{
					AuditTrail individualAuditTrail = transactionAuditTrail.clone();
					individualAuditTrail.setMedSupBrandName(medSup.getBrandName());
					individualAuditTrail.setMedSupQty(medSup.getQuantity());
					auditTrailManager.save(individualAuditTrail);
				}
				break;
			}
			case Returned:
			{
				medLoop: for (MedSupQty medSup : transaction.getMedSupItems())
				{
					if (medSup.getId().equals(medicineManager.findOne(medId).getIdAsString()))
					{
						transactionAuditTrail.setMedSupBrandName(medSup.getBrandName());
						transactionAuditTrail.setMedSupQty(medSup.getQuantity());
						auditTrailManager.save(transactionAuditTrail);
						break medLoop;
					}
				}
				break;
			}
		}		
	}

}
