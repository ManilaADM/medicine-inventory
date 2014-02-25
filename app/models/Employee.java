package models;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import dao.JongoModel;

public class Employee implements JongoModel {
	
	@JsonProperty("_id")
	private ObjectId id;	
	
	@JsonProperty("empCode")
	private String employeeCode;		
	
	private String firstName;
	
	private String lastName;

	private String gender;
	
	private String team;
	
	private String category;
	
	public Employee() {
	}
	
	public Employee(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getCollectionName() {
		return "employee";
	}
}
