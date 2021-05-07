package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;

public class AccountService {
	private UserDAO uDao= new UserDAOImpl();
	
	public boolean updateUserInfo(User update) { // update user info, add session info later
		// Pass DTO here(login info
		return uDao.updateUser(update);
	}
}
