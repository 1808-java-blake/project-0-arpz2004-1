package com.revature.daos;

import java.util.List;

import com.revature.beans.User;

public interface UserDao {
	public static final UserDao currentUserDao = new UserSerializer();
	
	void createUser(User u);
	User findByUsername(String username);
	User findByUsernameAndPassword(String username, String password);
	List<User> getAllUsers();
	void updateUser(User u);
	void deleteUser(User u);
}
