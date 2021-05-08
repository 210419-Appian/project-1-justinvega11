package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.AccountDAOImpl;
import com.revature.daos.UserDAOImpl;
import com.revature.models.Account;
import com.revature.models.BalanceDTO;
import com.revature.models.Message;
import com.revature.models.User;
import com.revature.services.AccountService;

public class AccountController {
	private static AccountDAOImpl aDao = new AccountDAOImpl();
	private static String s = new String();
	private static UserDAOImpl uDao = new UserDAOImpl();
	private static ObjectMapper om = new ObjectMapper();
	private static Message m = new Message();
	private static PrintWriter out;
	private static BalanceDTO bDTO = new BalanceDTO();
	private static AccountService aService = new AccountService();

	public static void withdraw(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession ses = req.getSession(false); // grabs session
		s = (String) ses.getAttribute("username");

		// code to read
		// input-----------------------------------------------------------------
		BufferedReader reader = req.getReader(); // read input of request from post
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		String body = new String(sb);
		bDTO = om.readValue(body, BalanceDTO.class);
		out = resp.getWriter(); // put into body of response
		// code to read input
		// --------------------------------------------------------------
		User u = uDao.findByUsername(s);
		Account acc = aDao.findById(bDTO.accountId);
		if (acc == null) {
			Message m = new Message();
			m.setMessage("Invalid accountID");
			out.print(om.writeValueAsString(m));
			resp.setStatus(400);
		}

		if ((u.getRole().getRoleId() == 1) || u.getUserId() == acc.getUserId()) { // check if admin

			if (aService.withdraw(bDTO, s)) {
				m.setMessage("$" + bDTO.amount + " has been withdrawn to Account #" + bDTO.accountId);
				out.print(om.writeValueAsString(m));
				resp.setStatus(201);
			} else {

				Message m = new Message();
				m.setMessage("Invalid funds");
				out.print(om.writeValueAsString(m));
				resp.setStatus(400);
			}
		} else {
			// security
			m.setMessage("The requested action is not permitted");
			PrintWriter out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}

	}
	public static void deposit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession ses = req.getSession(false); // grabs session
		s = (String) ses.getAttribute("username");

		// code to read
		// input-----------------------------------------------------------------
		BufferedReader reader = req.getReader(); // read input of request from post
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		String body = new String(sb);
		bDTO = om.readValue(body, BalanceDTO.class);
		out = resp.getWriter(); // put into body of response
		// code to read input
		// --------------------------------------------------------------
		User u = uDao.findByUsername(s);
		Account acc = aDao.findById(bDTO.accountId);
		if (acc == null) {
			Message m = new Message();
			m.setMessage("Invalid accountID");
			out.print(om.writeValueAsString(m));
			resp.setStatus(400);
		}

		if ((u.getRole().getRoleId() == 1) || u.getUserId() == acc.getUserId()) { // check if admin

			if (aService.deposit(bDTO, s)) {
				m.setMessage("$" + bDTO.amount + " has been deposited to Account #" + bDTO.accountId);
				out.print(om.writeValueAsString(m));
				resp.setStatus(201);
			} else {

				Message m = new Message();
				m.setMessage("Invalid funds");
				out.print(om.writeValueAsString(m));
				resp.setStatus(400);
			}
		} else {
			// security
			m.setMessage("The requested action is not permitted");
			PrintWriter out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}

	}

}
