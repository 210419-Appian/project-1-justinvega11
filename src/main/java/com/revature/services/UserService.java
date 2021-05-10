package com.revature.services;

import java.util.List;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;
import com.revature.models.UserDTO;

public class UserService {
	private UserDAO uDao = new UserDAOImpl();
	public List<User> findAll() {
		return uDao.allUsers();
	}
	public User findUser(int id) {
		return uDao.findById(id);
	}
	
	
	
	
	public boolean loginVerification(UserDTO u) {
		
		UserDAOImpl a = new UserDAOImpl(); // create daoimpl class to find user
		User userRequest = a.findByUsername(u.username); // DAOImpl
		
		
		
		System.out.println(userRequest.toString()); // print account information

		
		if ((userRequest.getPassword() != null) && (u.password.equals(userRequest.getPassword()))) { // check if passwords are the same
			return true;
		}
		return false;// if password are not equal return false
	}	
	
	
	
	public boolean register(User a) {
		
		if(uDao.findByUsername(a.getUsername()).getUsername()!= null){
			System.out.println("fbu");
			return false;
		}	
		if(uDao.findByEmail(a.getEmail()).getEmail()!=null) {
			System.out.println("fbe");
			return false;
		}
		if (a.getUserId() == 0) { // 
			return uDao.addUser(a);
		}	
		return false;

	}

}
