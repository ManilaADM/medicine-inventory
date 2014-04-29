package service;

import models.User;

public interface UserManager {

	Object findUser(String email, String password);

	User searchOne(String fieldName, String email);

}
