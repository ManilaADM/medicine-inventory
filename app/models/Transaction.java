package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class Transaction implements JongoModel{
		
	@JsonProperty("_id")
	private ObjectId id;
	
	private String employeeName; 
	
	private List<MedSupQty> medSupItems;
	
	private Date timeStamp;
	
	private String visitorName;
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	public String getFormattedTimeStamp() {
		SimpleDateFormat format  = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		return format.format(timeStamp);
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

	@Override
	public String getCollectionName() {
		return "transactions";
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

}
