package models;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class MedConsumption implements JongoModel {
	
	@JsonProperty("_id")
	private ObjectId id;
	
	private String medSupBrandName;
	
	private String genericName;
	
	private int takenQty;
	
	private int returnedQty;

	@Override
	public String getCollectionName() {
		return "audittrail";
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getBrandName() {
		return medSupBrandName;
	}

	public void setBrandName(String brandName) {
		this.medSupBrandName = brandName;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public int getTakenQty() {
		return takenQty;
	}

	public void setTakenQty(int takenQty) {
		this.takenQty = takenQty;
	}

	public int getReturnedQty() {
		return returnedQty;
	}

	public void setReturnedQty(int returnedQty) {
		this.returnedQty = returnedQty;
	}
	
	public int getConsumedQty() {
		return (this.takenQty - this.returnedQty);
	}
	
}
