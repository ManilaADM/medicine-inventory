package service;

import java.util.List;

import models.AuditTrail;

public interface AuditTrailManager {
	public void save(AuditTrail individualAuditTrail);	
	public List<AuditTrail> findAll();
}
