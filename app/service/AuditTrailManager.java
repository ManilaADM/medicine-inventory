package service;

import java.util.Date;
import java.util.List;

import models.AuditTrail;

public interface AuditTrailManager {
	public void save(AuditTrail individualAuditTrail);	
	public List<AuditTrail> findAll();
	public List<AuditTrail> search(Date dateFrom, Date dateTo);
}
