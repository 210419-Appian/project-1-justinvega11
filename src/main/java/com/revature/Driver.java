package com.revature;

import com.revature.daos.AccountDAOImpl;
import com.revature.daos.AccountStatusDAOImpl;
import com.revature.daos.AccountTypeDAOImpl;
import com.revature.daos.RoleDAOImpl;
import com.revature.daos.UserDAOImpl;
import com.revature.models.Account;
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
	AccountStatusDAOImpl asDao= new AccountStatusDAOImpl();
	AccountTypeDAOImpl acDao = new AccountTypeDAOImpl();
	AccountDAOImpl aDao = new AccountDAOImpl();
	
	User a = new User("Vegaj12","password2","Justin2","Vega2","justinvega112@gmail.com",rDao.findById(4)); // write junit verify it send, and verify unique by send twice
	Account acc = new Account(2147000000,asDao.findById(1),acDao.findById(1),uDao.findById(1).getUserId());
	//uDao.addUser(a);
	//aDao.addAccount(acc);
	System.out.println(uDao.findById(1));
	
}
}
