package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException { //
		// we need to register our driver. This process makes the application aware of what particular driver class is used.
	try {
		Class.forName("org.postgresql.Driver");
	}catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
	
	String url = "jdbc:postgresql://appian-210419.cxwpnpoeuguw.us-east-2.rds.amazonaws.com:5432/postgres";
	String username = "postgres";
	String password = "password";
	
	return DriverManager.getConnection(url,username,password);
	}
//	public static void main(String[] args) {
//		//finally blocks are commonly used to close resources(open connections outside java)
//		//there is a shortcut to create a finally block. "try with resources block" that declares the resource to open and close in the try declartion
//		
//			try(Connection conn=ConnectionUtil.getConnection()){ // built in finally block
//				System.out.println("connection succcesful");
//			}catch(SQLException e) {
//				e.printStackTrace();
//			}
//	}
}
