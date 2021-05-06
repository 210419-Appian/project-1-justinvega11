package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.AccountStatus;
import com.revature.utils.ConnectionUtil;


public class AccountStatusDAOImpl implements AccountStatusDAO {
	
	@Override
	public AccountStatus findById(int id) {
				try(Connection conn= ConnectionUtil.getConnection()){// connecting to datbase
		String sql = "SELECT * FROM bank.accountstatus WHERE statusid = ?;"; // store sql statement for database 
		
		PreparedStatement statement = conn.prepareStatement(sql); //giving sql string to prepare
		statement.setInt(1,id ); // assigning name to the first '?'
		ResultSet result = statement.executeQuery(); // executes the query and returns
		
		
		AccountStatus accountStatus = new AccountStatus(); // create one accountStatus
		while(result.next()) { // grab accountStatus info
			
			accountStatus.setStatusId(result.getInt("statusid"));
			accountStatus.setStatus(result.getString("status"));
	
		}
		return accountStatus;
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return null;
	}

}
