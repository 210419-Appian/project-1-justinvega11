package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.AccountDAOImpl;
import com.revature.daos.RoleDAOImpl;
import com.revature.daos.UserDAOImpl;
import com.revature.models.Account;
import com.revature.models.BalanceDTO;
import com.revature.models.Message;
import com.revature.models.TransferDTO;
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
	private static TransferDTO tDTO = new TransferDTO();
	
	
	
	
	
	
	public static void getAccountById(HttpServletRequest req, HttpServletResponse resp, int id)throws IOException{
		
		Account a = aService.findById(id);
		
		// convert jhava object into a json string that can be written to the body of an
		// HTTP response
		String json = om.writeValueAsString(a);// populates json object

		PrintWriter pw = resp.getWriter(); // intilize object to send response
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
		s = (String) ses.getAttribute("username"); // check privledges
		UserDAOImpl uDao = new UserDAOImpl();
		User u = uDao.findByUsername(s);

		
		if (a == null||u==null) {
			Message m = new Message();
			m.setMessage("Invalid accountID");
			out.print(om.writeValueAsString(m));
			resp.setStatus(400);
		}

		if ((u.getRole().getRoleId() == 1) ||(u.getRole().getRoleId() == 2) || u.getUserId() == a.getUserId()) { // check if admin

			pw.print(json);
		} else {
			// security
			m.setMessage("The requested action is not permitted");
			PrintWriter out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}
	}
	public static void withdraw(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		 // grabs session
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
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		 // grabs session
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

	public static void transfer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
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
		tDTO = om.readValue(body, TransferDTO.class);
		out = resp.getWriter(); // put into body of response
		// code to read input
		// --------------------------------------------------------------
		User u = uDao.findByUsername(s);
		Account accSource = aDao.findById(tDTO.sourceAccountId);
		Account accTarget = aDao.findById(tDTO.targetAccountId);
		if (accSource == null|| accTarget == null) {
			Message m = new Message();
			m.setMessage("Invalid accountID");
			out.print(om.writeValueAsString(m));
			resp.setStatus(400);
		}

		if ((u.getRole().getRoleId() == 1) || u.getUserId() == accSource.getUserId()) { // check if admin

			if (aService.transfer(tDTO, s)) {
				m.setMessage("$" + tDTO.amount + " has been deposited from Account #"+tDTO.getSourceAccountId() +"to Account #" + tDTO.getTargetAccountId());
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
	public void getAllAccounts(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		List<Account> list = aService.findAll();

		// convert jhava object into a json string that can be written to the body of an
		// HTTP response
		String json = om.writeValueAsString(list);// populates json object

		PrintWriter pw = resp.getWriter(); // intilize object to send response

		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
		s = (String) ses.getAttribute("username"); // check privledges
		UserDAOImpl uDao = new UserDAOImpl();
		User u = uDao.findByUsername(s);
		RoleDAOImpl rDao = new RoleDAOImpl();

		if ((u.getRole().getRoleId() == 1) || (u.getRole().getRoleId() == 2)) { // check if admin
			pw.print(json); // sends object to client
			resp.setStatus(200);
		} else {

			m.setMessage("The requested action is not permitted");
			PrintWriter out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}

	}
	public static void getAccountByStatusId(HttpServletRequest req, HttpServletResponse resp, int id) throws IOException {
		List<Account> list = aService.findByStatusId(id);
		// convert jhava object into a json string that can be written to the body of an
		// HTTP response
		
		String json = om.writeValueAsString(list);// populates json object

		PrintWriter pw = resp.getWriter(); // intilize object to send response
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
		s = (String) ses.getAttribute("username"); // check privledges
		UserDAOImpl uDao = new UserDAOImpl();
		User u = uDao.findByUsername(s); // user in the session

		
		if (u==null) {
			Message m = new Message();
			m.setMessage("Invalid accountID");
			out.print(om.writeValueAsString(m));
			resp.setStatus(400);
			return;
		}

		if ((u.getRole().getRoleId() == 1) ||(u.getRole().getRoleId() == 2)) { // check if admin
			System.out.println("---list1---");
			pw.print(json);
			resp.setStatus(200);
			
		} else {
			// security
			m.setMessage("The requested action is not permitted");
			PrintWriter out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}
		
	}
	public static void getAccountByUser(HttpServletRequest req, HttpServletResponse resp, int id) throws IOException {
		List<Account> list = aService.findByUserId(id);
		// convert jhava object into a json string that can be written to the body of an
		// HTTP response
		
		String json = om.writeValueAsString(list);// populates json object

		PrintWriter pw = resp.getWriter(); // intilize object to send response
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
		s = (String) ses.getAttribute("username"); // check privledges
		UserDAOImpl uDao = new UserDAOImpl();
		User u = uDao.findByUsername(s); // user in the session

		
		if (u==null) {
			Message m = new Message();
			m.setMessage("Invalid accountID");
			out.print(om.writeValueAsString(m));
			resp.setStatus(400);
		}

		if ((u.getRole().getRoleId() == 1) ||(u.getRole().getRoleId() == 2) || u.getUserId() == id ) { // check if admin

			pw.print(json);
		} else {
			// security
			m.setMessage("The requested action is not permitted");
			PrintWriter out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}
		
	}
	public static void submitAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
		s = (String) ses.getAttribute("username");
		UserDAOImpl uDao = new UserDAOImpl();
		User u = uDao.findByUsername(s);
		RoleDAOImpl rDao = new RoleDAOImpl();

		

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
			Account newAccount = om.readValue(body, Account.class);
			PrintWriter out = resp.getWriter(); // put into body of response
			// code to read input
			// --------------------------------------------------------------
			
			if (u.getRole().getRoleId() == 1 || u.getRole().getRoleId()==2 || (newAccount.getUserId() == u.getUserId()) ) { // check if admin
				newAccount = aService.submit(newAccount);
			if (newAccount!=null) { //
				out.print(om.writeValueAsString(newAccount));
				resp.setStatus(201);
			} else {

				Message m = new Message();
				m.setMessage("Invalid fields");
				out.print(om.writeValueAsString(m));
				resp.setStatus(400);
			}
		} else {
			// security
			m.setMessage("The requested action is not permitted");
			out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}
	}
	public static void updateAccount(HttpServletRequest req, HttpServletResponse resp, int id) throws IOException{
		// TODO Auto-generated method stub
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
		s = (String) ses.getAttribute("username");
		UserDAOImpl uDao = new UserDAOImpl();
		User u = uDao.findByUsername(s);
		RoleDAOImpl rDao = new RoleDAOImpl();

		

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
			Account newAccount = om.readValue(body, Account.class);
			PrintWriter out = resp.getWriter(); // put into body of response
			// code to read input
			// --------------------------------------------------------------
			
			if (u.getRole().getRoleId() == 1 || u.getRole().getRoleId()==2 || (newAccount.getUserId() == u.getUserId()) ) { // check if admin
				newAccount = aService.update(newAccount);
			if (newAccount!=null) { //
				out.print(om.writeValueAsString(newAccount));
				resp.setStatus(201);
			} else {

				Message m = new Message();
				m.setMessage("Invalid fields");
				out.print(om.writeValueAsString(m));
				resp.setStatus(400);
			}
		} else {
			// security
			m.setMessage("The requested action is not permitted");
			out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}
	}
}
