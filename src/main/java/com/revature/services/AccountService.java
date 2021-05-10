package com.revature.services;

import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.AccountDAOImpl;
import com.revature.daos.AccountStatusDAOImpl;
import com.revature.daos.UserDAOImpl;
import com.revature.models.Account;
import com.revature.models.BalanceDTO;
import com.revature.models.Message;
import com.revature.models.TransferDTO;

public class AccountService {

	private static AccountDAOImpl aDao = new AccountDAOImpl();
	private static String s = new String();
	private static UserDAOImpl uDao = new UserDAOImpl();
	private static ObjectMapper om = new ObjectMapper();
	private static Message m = new Message();
	private static PrintWriter out;
	private static BalanceDTO bDTO = new BalanceDTO();
	private static AccountService aService = new AccountService();
	private static AccountStatusDAOImpl atDao = new AccountStatusDAOImpl();

	public boolean withdraw(BalanceDTO bDTO, String s) {
		// TODO Auto-generated method stub

		System.out.println("Line157: accountID:" + bDTO.getAccountId());

		Account acc = aDao.findById(bDTO.accountId);
		if (acc == null) {
			return false;
		}

		if (bDTO.amount < acc.getBalance()) { // check if overdraft
			acc.setBalance(acc.getBalance() - bDTO.amount);
			aDao.updateAccount(acc);
			return true;
		} else {

			return false;
		}

	}

	public boolean deposit(BalanceDTO bDTO, String s) {
		// TODO Auto-generated method stub

		Account acc = aDao.findById(bDTO.accountId);
		if (acc == null) {
			return false;
		}

		acc.setBalance(acc.getBalance() + bDTO.amount);
		aDao.updateAccount(acc);
		return true;

	}

	public boolean transfer(TransferDTO tDTO, String s2) {
		Account accSource = aDao.findById(tDTO.sourceAccountId);
		Account accTarget = aDao.findById(tDTO.targetAccountId);
		if ((accSource == null) || (accTarget == null)) {
			return false;
		}

		if (tDTO.amount < accSource.getBalance()) { // check if overdraft
			accSource.setBalance(accSource.getBalance() + tDTO.amount);
			accTarget.setBalance(accTarget.getBalance() + tDTO.amount);
			aDao.updateAccount(accSource);
			aDao.updateAccount(accTarget);
			return true;
		} else {

			return false;
		}

	}

	public List<Account> findAll() {
		return aDao.allAccounts();
	}

	public Account findById(int id) {
		return aDao.findById(id);
	}

	public List<Account> findByStatusId(int id) {
		return aDao.findByAccountStatus(id);
	}

	public List<Account> findByUserId(int id) {
		return aDao.findByUserId(id);
	}

	public Account submit(Account a) {
		if (a.getAccountId() != 0) {
			return null;
		}
		aDao.addAccount(a);
		List<Account> buffer = aDao.findByUserId(a.getUserId());
		for (int i = 0; i < buffer.size(); i++) { // find user id accounts with account type opposite
			if (atDao.findById(buffer.get(i).getStatus().getStatusId()).getStatusId()==3) {
				return buffer.get(i);
			} else {
				return buffer.get(i++);
			}

		}
		return null;
	}

	public Account update(Account newAccount) {
		// TODO Auto-generated method stub
		if (newAccount.getAccountId() != 0) {
			return null;
		}
		
		if(aDao.updateAccount(newAccount)) {
			return aDao.findById(newAccount.getAccountId());
		}
	
	
	
		return null;
	}
}
