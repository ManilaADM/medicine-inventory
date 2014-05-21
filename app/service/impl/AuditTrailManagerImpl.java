package service.impl;

import java.util.List;

import dao.JongoDAO;
import models.AuditTrail;
import service.AuditTrailManager;

public class AuditTrailManagerImpl implements AuditTrailManager{
	private static JongoDAO<AuditTrail> auditTrailDao = new JongoDAO<>(AuditTrail.class);
	
	@Override
	public void save(AuditTrail individualAuditTrail) {		
		auditTrailDao.save(individualAuditTrail);
	}
	
	@Override
	public List<AuditTrail> findAll() {
		return auditTrailDao.findAll();
	}
}
