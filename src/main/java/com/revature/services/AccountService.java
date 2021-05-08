package com.revature.services;

import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.AccountDAOImpl;
import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.Account;
import com.revature.models.BalanceDTO;
import com.revature.models.Message;
import com.revature.models.User;

public class AccountService {
	
	private static AccountDAOImpl aDao = new AccountDAOImpl();
	private static String s = new String();
	private static UserDAOImpl uDao = new UserDAOImpl();
	private static ObjectMapper om = new ObjectMapper();
	private static Message m = new Message();
	private static PrintWriter out;
	private static BalanceDTO bDTO = new BalanceDTO();
	private static AccountService aService = new AccountService();


	public boolean withdraw(BalanceDTO bDTO, String s) {
		// TODO Auto-generated method stub
		
		System.out.println("Line157: accountID:" + bDTO.getAccountId());


		Account acc = aDao.findById(bDTO.accountId);
		if (acc == null) {
			return false;
		}
		User u = uDao.findByUsername(s);

		if ((u.getRole().getRoleId() == 1) || u.getUserId() == acc.getUserId()) { // check if admin

			if (bDTO.amount < acc.getBalance()) { // check if overdraft
				acc.setBalance(acc.getBalance() - bDTO.amount);
				aDao.updateAccount(acc);
				return true;
			} else {

				return false;
			}
		} 
		return false;
	
	}
	public boolean deposit(BalanceDTO bDTO, String s) {
		// TODO Auto-generated method stub
		
		

		Account acc = aDao.findById(bDTO.accountId);
		if (acc == null) {
			return false;
		}
		User u = uDao.findByUsername(s);

		if ((u.getRole().getRoleId() == 1) || u.getUserId() == acc.getUserId()) { // check if admin

		
				acc.setBalance(acc.getBalance() + bDTO.amount);
				aDao.updateAccount(acc);
				return true;
		} 
		return false;
	
	}
}
