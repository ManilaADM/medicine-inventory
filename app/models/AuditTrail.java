package models;

import java.util.Date;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class AuditTrail implements JongoModel {
	
	@JsonProperty("_id")
	private ObjectId id;
	
	private Date logTimeStamp;
	
	private String userName;
	
	private String requestorName;
	
	private String medSupBrandName;
	
	private int medSupQty;
	
	private String actionDone;
	
	public AuditTrail() {

	}
	
	public AuditTrail(Date logTimeStamp, String userName, String requestorName, String actionDone) {
		this.logTimeStamp = logTimeStamp;
		this.userName = userName;
		this.requestorName = requestorName;
		this.actionDone = actionDone;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public ObjectId getId() {
		return id;
	}
	
	public void setLogTimeStamp(Date logTimeStamp) {
		this.logTimeStamp = logTimeStamp;
	}
	
	public Date getLogTimeStamp() {
		return logTimeStamp;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	
	public String getRequestorName() {
		return requestorName;
	}
	
	public void setMedSupBrandName(String medSupBrandName) {
		this.medSupBrandName = medSupBrandName;
	}
	
	public String getMedSupBrandName() {
		return medSupBrandName;
	}
	
	public void setMedSupQty(int medSupQty) {
		this.medSupQty = medSupQty;
	}
	
	public int getMedSupQty() {
		return medSupQty;
	}
	
	public void setActionDone(String actionDone) {
		this.actionDone = actionDone;
	}
	
	public String getActionDone() {
		return actionDone;
	}

	@Override
	public String getCollectionName() {
		return "audittrail";
	}
	
	public AuditTrail clone() {
		return new AuditTrail(this.logTimeStamp, this.userName, this.requestorName, this.actionDone);
	}

}