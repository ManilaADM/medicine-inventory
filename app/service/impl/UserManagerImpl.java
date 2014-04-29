package service.impl;

import models.User;
import service.UserManager;
import dao.JongoDAO;

public class UserManagerImpl implements UserManager {
	static JongoDAO<User> userDao = new JongoDAO<>(User.class);

	@Override
	public Object findUser(String email, String password) {
		// TODO Auto-generated method stub
		return userDao.findUser(email, password);
	}

	@Override
	public User searchOne(String string, String email) {
		// TODO Auto-generated method stub
		return userDao.searchOne(string, email);
	}
}
