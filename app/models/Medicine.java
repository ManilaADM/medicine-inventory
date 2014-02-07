package models;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class Medicine implements JongoModel {
	
	@JsonProperty("_id")
	private ObjectId id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("stock_quantity")
	private long stockQunatity;
	@JsonProperty("allowed_per_employee")
	private int allowedPerEmployee;
	@JsonProperty("alert_quantity")
	private int alertQuantity;
	@JsonProperty("critical_alert_quantity")
	private int criticalAlertQuantity;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getStockQunatity() {
		return stockQunatity;
	}
	public void setStockQunatity(long qunatity) {
		this.stockQunatity = qunatity;
	}
	public int getAllowedPerEmployee() {
		return allowedPerEmployee;
	}
	public void setAllowedPerEmployee(int allowedPerEmployee) {
		this.allowedPerEmployee = allowedPerEmployee;
	}
	public int getAlertQuantity() {
		return alertQuantity;
	}
	public void setAlertQuantity(int alertQuantity) {
		this.alertQuantity = alertQuantity;
	}
	public int getCriticalAlertQuantity() {
		return criticalAlertQuantity;
	}
	public void setCriticalAlertQuantity(int criticalAlertQuantity) {
		this.criticalAlertQuantity = criticalAlertQuantity;
	}
	@Override
	public String getCollectionName() {
		return "medicine";
	}	
}
