package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.AccountType;
import com.revature.utils.ConnectionUtil;

public class AccountTypeDAOImpl implements AccountTypeDAO {
	@Override
	public AccountType findById(int id) {
				try(Connection conn= ConnectionUtil.getConnection()){// connecting to datbase
		String sql = "SELECT * FROM bank.accounttype WHERE typeid = ?;"; // store sql statement for database 
		
		PreparedStatement statement = conn.prepareStatement(sql); //giving sql string to prepare
		statement.setInt(1,id ); // assigning name to the first '?'
		ResultSet result = statement.executeQuery(); // executes the query and returns
		
		
		AccountType accountType = new AccountType(); // create one accountStatus
		while(result.next()) { // grab accountStatus info
			
			accountType.setTypeId(result.getInt("typeid"));
			accountType.setType(result.getString("type"));
	
		}
		return accountType;
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return null;
	}
}
