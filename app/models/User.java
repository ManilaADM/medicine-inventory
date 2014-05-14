package models;


import org.bson.types.ObjectId;
import play.db.ebean.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import dao.JongoModel;


public class User implements JongoModel {

	@JsonProperty("_id")
	private ObjectId id;
	
    public String email;
	
    public String name;
	
	public String password;
	
	public String role;
	
	public User () {
		
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    public User(String email, String name, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getCollectionName() {
		return "users";
	}
	
	
}
