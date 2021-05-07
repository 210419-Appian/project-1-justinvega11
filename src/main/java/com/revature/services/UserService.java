package com.revature.services;

import java.util.List;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;
import com.revature.models.UserDTO;



public class UserService {
private UserDAO uDao= new UserDAOImpl();
	
	public List<User> findAll(){
		return uDao.allUsers();
	}

	public User findUser(int id) {
		return uDao.findById(id);
	}
	public boolean loginVerification(UserDTO u){
		
		UserDAOImpl a = new UserDAOImpl();
		
		User userRequest = a.findByUsername(u.username);
		System.out.println(userRequest.toString());
		
		if (u.password.equals(userRequest.getPassword())) {
			return true;
		}
		
		return false;
	}
}
