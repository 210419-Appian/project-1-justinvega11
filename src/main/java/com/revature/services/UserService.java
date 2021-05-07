package com.revature.services;

import java.util.List;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;



public class UserService {
private UserDAO uDao= new UserDAOImpl();
	
	public List<User> findAll(){
		return uDao.allUsers();
	}

	public User findUser(int id) {
		return uDao.findById(id);
	}
}
