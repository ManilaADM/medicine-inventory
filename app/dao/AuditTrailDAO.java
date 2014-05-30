package dao;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import models.AuditTrail;

public class AuditTrailDAO extends JongoDAO<AuditTrail>{
	public AuditTrailDAO(Class<AuditTrail> clazz)
	{
		super(clazz);
	}
	
	public List<AuditTrail> search(Date dateFrom, Date dateTo){
		return Lists.newArrayList(collections.find("{logTimeStamp : {$gt : #, $lte : #}}", dateFrom, dateTo).as(clazz));
	}

}
