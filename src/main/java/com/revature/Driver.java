package com.revature;

import com.revature.daos.RoleDAOImpl;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;

public class Driver {
//	public static void main(String[] args) {
//		AccountStatusDAOImpl aDao= new AccountStatusDAOImpl();
//		
//		
//		System.out.println(aDao.findById(4));
//	}
	public static void main(String[] args) {
	UserDAOImpl uDao= new UserDAOImpl();
	RoleDAOImpl rDao = new RoleDAOImpl();
	
	User a = new User("Vegaj12","password2","Justin2","Vega2","justinvega112@gmail.com",rDao.findById(4)); // write junit verify it send, and verify unique by send twice
	
	uDao.addUser(a);
	System.out.println(uDao.findById(1));
}
}
