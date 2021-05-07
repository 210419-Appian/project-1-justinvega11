package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.utils.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

	private static AccountStatusDAO asDAO = new AccountStatusDAOImpl();
	private static AccountTypeDAO atDAO = new AccountTypeDAOImpl();
	@Override
	public Account findById(int id) { // return account object given ID
		try (Connection conn = ConnectionUtil.getConnection()) {// connecting to datbase
			String sql = "SELECT * FROM bank.Account WHERE accountid = ?;"; // store sql statement for database

			PreparedStatement statement = conn.prepareStatement(sql); // giving sql string to prepare
			statement.setInt(1, id); // assigning name to the first '?'
			ResultSet result = statement.executeQuery(); // executes the query and returns

			Account account = new Account(); // create one accountStatus

			while (result.next()) { // grab accountStatus info

				account.setAccountId(result.getInt("accountid"));
				account.setBalance(result.getDouble("balance"));

				int aStatus = result.getInt("status"); // get status id and populate accountstatus
				if (aStatus != 0) {
					account.setStatus(asDAO.findById(aStatus));
				}

				int aType = result.getInt("type"); // gets type id and popualte Accounttype
				if (aType != 0) {
					account.setType(atDAO.findById(aType));
				}
				account.setUserId(id);

			}
			return account;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Account> findByUserId(int userId) {// returns account by user id
		try (Connection conn = ConnectionUtil.getConnection()) {// connecting to datbase
			String sql = "SELECT * FROM bank.Account WHERE userId = ?;"; // store sql statement for database

			PreparedStatement statement = conn.prepareStatement(sql); // giving sql string to prepare
			statement.setInt(1, userId); // assigning name to the first '?'
			ResultSet result = statement.executeQuery(); // executes the query and returns

			List<Account> list = new ArrayList<>(); // create one accountStatus

			while (result.next()) { // grab accountStatus info
				Account a = new Account(result.getInt("accountId"), result.getDouble("balance"), null, null,
						result.getInt("userId"));
				int aStatus = result.getInt("status"); // get status id and populate accountstatus
				if (aStatus != 0) {
					a.setStatus(asDAO.findById(aStatus));
				}
				int aType = result.getInt("type"); // gets type id and popualte Accounttype
				if (aType != 0) {
					a.setType(atDAO.findById(aType));
				}
				list.add(a);

			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean addAccount(Account a) { // add account to db

		try (Connection conn = ConnectionUtil.getConnection()) {

			// There is no chance for sql injection with just an integer so this is safe.
			String sql = "INSERT INTO bank.Account (AccountId,balance,status,type,userId)"
					+ "	VALUES (?, ?, ?, ?, ?);";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0;
			statement.setInt(++index, a.getAccountId());
			statement.setDouble(++index, a.getBalance());
			statement.setInt(++index, a.getStatus().getStatusId());
			statement.setInt(++index, a.getType().getTypeId());
			statement.setInt(++index, a.getUserId());

			statement.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	@Override
	public boolean updateAccount(Account a) { // return account object given ID

		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "UPDATE bank.Account " + "Set AccountId = ?," + "balance = ?," + "status = ?," + "type = ?,"
					+ "userId = ?" + ";";

			PreparedStatement statement = conn.prepareStatement(sql);

			int index = 0; // inputs for sql statement from paremeter
			statement.setInt(++index, a.getAccountId());
			statement.setDouble(++index, a.getBalance());
			statement.setInt(++index, a.getStatus().getStatusId());
			statement.setInt(++index, a.getType().getTypeId());
			statement.setInt(++index, a.getUserId());

			statement.execute();
			return true;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return false;
	}
	@Override
	public List<Account> findByAccountStatus(int status) { // return accounts with status note: might need ot change
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM bank.Account WHERE status = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql); // giving sql string to prepare
			statement.setInt(1, status); // assigning name to the first '?'
			ResultSet result = statement.executeQuery(); // executes the query and returns
			

			List<Account> list = new ArrayList<>(); // create one accountStatus

			while (result.next()) { // grab accountStatus info
				Account a = new Account(result.getInt("accountId"), result.getDouble("balance"), null, null,
						result.getInt("userId"));
				int aStatus = result.getInt("status"); // get status id and populate accountstatus
				if (aStatus != 0) {
					a.setStatus(asDAO.findById(aStatus));
				}
				int aType = result.getInt("type"); // gets type id and popualte Accounttype
				if (aType != 0) {
					a.setType(atDAO.findById(aType));
				}
				list.add(a);

			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
}
