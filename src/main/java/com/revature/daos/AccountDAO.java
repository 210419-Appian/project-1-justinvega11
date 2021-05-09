package com.revature.daos;

import java.util.List;

import com.revature.models.Account;

public interface AccountDAO {
	public Account findById(int id); // return account object given ID
	public List<Account> findByUserId(int userId);// returns account by user id
	public Account addAccount(Account a); // add account to db
	public boolean updateAccount(Account update); // account updated for balance
	public List<Account> findByAccountStatus(int status); // return accounts with status note: might need ot change to statusid
	public List<Account> allAccounts();
}
