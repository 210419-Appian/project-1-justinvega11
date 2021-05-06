package com.revature;

import com.revature.daos.AccountTypeDAOImpl;
import com.revature.daos.RoleDAOImpl;

public class Driver {
//	public static void main(String[] args) {
//		AccountStatusDAOImpl aDao= new AccountStatusDAOImpl();
//		
//		
//		System.out.println(aDao.findById(4));
//	}
	public static void main(String[] args) {
	RoleDAOImpl aDao= new RoleDAOImpl();
	
	
	System.out.println(aDao.findById(3));
}
}
