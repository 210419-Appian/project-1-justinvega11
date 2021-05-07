package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDAOImpl implements UserDAO{
	private static RoleDAO rDAO = new RoleDAOImpl();
	@Override
	public User findById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {// connecting to datbase
			String sql = "SELECT * FROM bank.user WHERE userid = ?;"; // store sql statement for database

			PreparedStatement statement = conn.prepareStatement(sql); // giving sql string to prepare
			statement.setInt(1, id); // assigning name to the first '?'
			ResultSet result = statement.executeQuery(); // executes the query and returns

			User user = new User(); // create one accountStatus

			while (result.next()) { // grab accountStatus info

				user.setUserId(result.getInt("userid"));
				user.setUsername(result.getString("username"));
				user.setPassword(result.getString("password"));
				user.setFirstName(result.getString("firstname"));
				user.setLastName(result.getString("lastname"));
				user.setEmail(result.getString("email"));

				

				int uRole = result.getInt("role"); // gets role id for user
				if (uRole != 0) {
					user.setRole(rDAO.findById(uRole));
				}
				

			}
			return user;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User findByUsername(String name) {
		try (Connection conn = ConnectionUtil.getConnection()) {// connecting to datbase
			String sql = "SELECT * FROM bank.user WHERE username = ?;"; // store sql statement for database

			PreparedStatement statement = conn.prepareStatement(sql); // giving sql string to prepare
			statement.setString(1, name); // assigning name to the first '?'
			ResultSet result = statement.executeQuery(); // executes the query and returns

			User user = new User(); // create one user

			while (result.next()) { // grab accountStatus info

				user.setUserId(result.getInt("userid"));
				user.setUsername(result.getString("username"));
				user.setPassword(result.getString("password"));
				user.setFirstName(result.getString("firstname"));
				user.setLastName(result.getString("lastname"));
				user.setEmail(result.getString("email"));

				

				int uRole = result.getInt("role"); // gets role id for user 
				if (uRole != 0) {
					user.setRole(rDAO.findById(uRole));
				}
				

			}
			return user;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateUser(User a) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "UPDATE user " + "Set userId = ?," + "username = ?," + "password = ?," + "firstName = ?,"
					+ "lastName = ?,"+"email = ?," + "role = ?"+";";
			

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0; // inputs for sql statement from paremeter
			statement.setInt(++index, a.getUserId());
			statement.setString(++index, a.getUsername());
			statement.setString(++index, a.getPassword());
			statement.setString(++index, a.getFirstName());
			statement.setString(++index, a.getLastName());
			statement.setString(++index, a.getEmail());
			statement.setInt(++index, a.getRole().getRoleId());

			statement.execute();
			return true;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return false;
	}
	@Override
	public boolean addUser(User a) { // add account to db

		try (Connection conn = ConnectionUtil.getConnection()) {

			// There is no chance for sql injection with just an integer so this is safe.
			String sql = "INSERT INTO bank.User (username,password,firstname,lastname,email,role)"
					+ "	VALUES (?, ?, ?, ?, ?, ?);";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			
			statement.setString(++index, a.getUsername());
			statement.setString(++index, a.getPassword());
			statement.setString(++index, a.getFirstName());
			statement.setString(++index, a.getLastName());
			statement.setString(++index, a.getEmail());
			statement.setInt(++index, a.getRole().getRoleId());

			statement.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

}
