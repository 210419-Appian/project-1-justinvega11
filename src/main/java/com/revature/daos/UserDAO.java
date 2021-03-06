package com.revature.daos;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {
	public User findById(int id); // return user object by Id
	public User findByUsername(String name); // return user object by username
	public boolean updateUser(User update); // replaces user 
	boolean addUser(User a);
	public List<User> allUsers();
	public User findByEmail(String name);
}
