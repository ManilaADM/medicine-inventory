package models;


import org.bson.types.ObjectId;
import play.db.ebean.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import dao.JongoModel;


public class User extends Model implements JongoModel {

	@JsonProperty("_id")
	private ObjectId id;
	
	@JsonProperty("email")
    public String email;
	
	@JsonProperty("name")
    public String name;
	
	@JsonProperty("password")
    public String password;
    
    public User(String email, String name, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
    }

    public static Finder<String,User> find = new Finder<String,User>(
        String.class, User.class
    );
    
    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }

	@Override
	public String getCollectionName() {
		// TODO Auto-generated method stub
		return "user";
	}
}
