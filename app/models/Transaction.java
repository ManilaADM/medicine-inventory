package models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class Transaction implements JongoModel{
		
	@JsonProperty("_id")
	private ObjectId objectId;
	
	private Employee employee;
	
	private List<MedSupQty> itemsRequested;
	
	private Date timeStamp;
	
	public ObjectId getObjectId() {
		return objectId;
	}
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@Override
	public String getCollectionName() {
		return "transactions";
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public List<MedSupQty> getItemsRequested() {
		return itemsRequested;
	}
	public void setItemsRequested(List<MedSupQty> itemsRequested) {
		this.itemsRequested = itemsRequested;
	}
	
}
