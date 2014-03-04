package models.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.MedSupQty;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class TransactionVO implements JongoModel{
		
	@JsonProperty("_id")
	private ObjectId id;
	
	private String employeeName; 
	
	private MedSupQty medSupItems;
	
	private Date timeStamp;
	
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

	public MedSupQty getMedSupItems() {
		return medSupItems;
	}

	public void setMedSupItems(MedSupQty medSupItems) {
		this.medSupItems = medSupItems;
	}

}
