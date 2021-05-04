package com.revature.daos;

import com.revature.models.AccountStatus;

public interface AccountStatusDAO {
	public AccountStatus findById(int id); // return accountstatus byid
}
