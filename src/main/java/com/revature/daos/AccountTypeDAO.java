package com.revature.daos;

import com.revature.models.AccountType;

public interface AccountTypeDAO {
	public AccountType findById(int id); // return account trype given id
}
