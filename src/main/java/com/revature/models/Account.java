package com.revature.models;

public class Account {
	  private int accountId; // primary key
	  private double balance;  // not null
	  private AccountStatus status; // foreign key 
	  private AccountType type; // foreign key
}