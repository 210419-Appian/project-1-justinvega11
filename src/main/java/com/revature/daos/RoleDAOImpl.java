package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Role;
import com.revature.utils.ConnectionUtil;

public class RoleDAOImpl implements RoleDAO {

	@Override
	public Role findById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {// connecting to datbase
			String sql = "SELECT * FROM bank.Role WHERE roleid = ?;"; // store sql statement for database

			PreparedStatement statement = conn.prepareStatement(sql); // giving sql string to prepare
			statement.setInt(1, id); // assigning name to the first '?'
			ResultSet result = statement.executeQuery(); // executes the query and returns

			Role Role = new Role(); // create one accountStatus
			while (result.next()) { // grab accountStatus info

				Role.setRoleId(result.getInt("roleid"));
				Role.setRole(result.getString("role"));

			}
			return Role;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
