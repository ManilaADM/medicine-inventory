package service.impl;

import java.util.Date;
import java.util.List;

import dao.AuditTrailDAO;
import dao.JongoDAO;
import models.AuditTrail;
import service.AuditTrailManager;

public class AuditTrailManagerImpl implements AuditTrailManager{
	private static AuditTrailDAO auditTrailDao = new AuditTrailDAO(AuditTrail.class);
	
	@Override
	public void save(AuditTrail individualAuditTrail) {		
		auditTrailDao.save(individualAuditTrail);
	}
	
	@Override
	public List<AuditTrail> findAll() {
		return auditTrailDao.findAll();
	}
	
	public List<AuditTrail> search(Date dateFrom, Date dateTo) {
//		return auditTrailDao.search("medSupBrandName", value);
		return auditTrailDao.search(dateFrom, dateTo);
	}
}
