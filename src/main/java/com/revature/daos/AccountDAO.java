package com.revature.daos;

import com.revature.models.Account;

public interface AccountDAO {
	public Account findById(int id); // return account object given ID
	public boolean addAccount(Account a); // add account to db

}
