package models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class Transaction implements JongoModel{
		
	@JsonProperty("_id")
	private ObjectId objectId;
	
	private String employeeName; 
	
	private List<MedSupQty> medSupItems;
	
	private Date timeStamp;
	
	private boolean returned;
	
	public ObjectId getObjectId() {
		return objectId;
	}
	
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public List<MedSupQty> getMedSupItems() {
		return medSupItems;
	}
	
	public void setMedSupItems(List<MedSupQty> medSupItems) {
		this.medSupItems = medSupItems;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	@Override
	public String getCollectionName() {
		return "transactions";
	}
}
