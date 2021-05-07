package com.revature.models;

public class Account {
	  private int accountId; // primary key
	  private double balance;  // not null
	  private AccountStatus status; // foreign key 
	  private AccountType type; // foreign key
	  private int userId;
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Account( double balance, AccountStatus status, AccountType type,int userId) {
		super();
		
		this.balance = balance;
		this.status = status;
		this.type = type;
		this.userId= userId;
	}
	
	public Account(int accountId, double balance, AccountStatus status, AccountType type, int userId) {
		super();
		this.accountId = accountId;
		this.balance = balance;
		this.status = status;
		this.type = type;
		this.userId = userId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	public AccountType getType() {
		return type;
	}
	public void setType(AccountType type) {
		this.type = type;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", status=" + status + ", type=" + type
				+ ", userId=" + userId + "]";
	}
	
	
	  
	
	  
	  
}

