package models;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class Medicine implements JongoModel {
	
	@JsonProperty("_id")
	private ObjectId id;
	
	@JsonIgnore
	private String objectId;
	
	private String brandName;
	
	private String genericName;
	
	private String description;
	
	private boolean available;
	
	private int count;
	
	private int notificationAlertCount;

	private int dailyQtyLimitPerUser;
	
	private boolean quantifiable;
	
	public ObjectId getId() {
		return id;
	}
	
	public String getIdAsString()
	{
		return id.toString();
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public String getGenericName() {
		return genericName;
	}
	
	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getNotificationAlertCount() {
		return notificationAlertCount;
	}
	
	public void setNotificationAlertCount(int notificationAlertCount) {
		this.notificationAlertCount = notificationAlertCount;
	}
	
	public int getDailyQtyLimitPerUser() {
		return dailyQtyLimitPerUser;
	}
	
	public void setDailyQtyLimitPerUser(int dailyQtyLimitPerUser) {
		this.dailyQtyLimitPerUser = dailyQtyLimitPerUser;
	}
	
	public boolean isQuantifiable() {
		return quantifiable;
	}
	
	public void setQuantifiable(boolean quantifiable) {
		this.quantifiable = quantifiable;
	}	
	
	@Override
	public String getCollectionName() {
		return "medicine";
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}
