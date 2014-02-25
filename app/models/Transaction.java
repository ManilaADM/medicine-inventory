package models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class Transaction implements JongoModel{
		
	@JsonProperty("_id")
	private ObjectId objectId;
	
	@JsonProperty("employee")
	private Employee employee;
	
	@JsonProperty("medicine_list")
	private List<MedSupQty> medicineList;
	
	@JsonProperty("time_stamp")
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
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public List<MedSupQty> getItemsRequested() {
		return medicineList;
	}
	
	public void setItemsRequested(List<MedSupQty> itemsRequested) {
		this.medicineList = itemsRequested;
	}
	
	@Override
	public String getCollectionName() {
		return "transactions";
	}
}
