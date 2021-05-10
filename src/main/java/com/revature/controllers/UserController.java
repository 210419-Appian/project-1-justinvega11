package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.RoleDAOImpl;
import com.revature.daos.UserDAOImpl;
import com.revature.models.Message;
import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.services.UserService;

public class UserController {

	private static UserService uService = new UserService();
	private static ObjectMapper om = new ObjectMapper();
	private static Message m = new Message();
	private static String s = new String();
	private static UserDAOImpl uDao = new UserDAOImpl();
	
	public void getUser(HttpServletRequest req, HttpServletResponse resp, int id) throws IOException {
		// TODO Auto-generated method stub
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
		s = (String) ses.getAttribute("username"); // check privledges
		UserDAOImpl uDao = new UserDAOImpl();
		User u = uDao.findByUsername(s);
		RoleDAOImpl rDao = new RoleDAOImpl();
		User uGet = uService.findUser(id); // grabs avenger based on ID

		// convert jhava object into a json string that can be written to the body of an
		// HTTP response
		String json = om.writeValueAsString(uGet);
		System.out.println(json);

		PrintWriter pw = resp.getWriter();

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

	public void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
		String s = (String) ses.getAttribute("username");

		UserDAOImpl uDao = new UserDAOImpl();
		User u = uDao.findByUsername(s);
	

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
		User newUser = om.readValue(body, User.class);
		PrintWriter out = resp.getWriter(); // put into body of response
		// code to read input
		// --------------------------------------------------------------

		//System.out.println(newUser);
		if ((u.getRole().getRoleId() == 1) || (u.getUserId() == newUser.getUserId())) { // check if admin
			if(uDao.updateUser(newUser)) {
				String json = om.writeValueAsString(uDao.findById(newUser.getUserId()));
				System.out.println(json);
				out.print(json); // sends object to client
				resp.setStatus(200);
			} else {
				Message m = new Message();
				m.setMessage("Invalid fields");
				out.print(om.writeValueAsString(m));
				resp.setStatus(400);
			}
			
		} else {

			m.setMessage("The requested action is not permitted");
	
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}
	}

	public void getAllUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		List<User> list = uService.findAll();

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

	public static void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
		
		UserDTO u = new UserDTO(); // object from postman
		UserService a = new UserService(); // class to call service layer
		UserDAOImpl uDao = new UserDAOImpl(); //  // dao to access user database

		//
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();

		} // whole thing reads from postman 

		String body = new String(sb); // string object holding postman text
		u = om.readValue(body, UserDTO.class); // assigned postman text into object for java into u
		PrintWriter out = resp.getWriter(); // put into body of response 
		
		// 
		// String json = om.writeValueAsString(User);

		if (a.loginVerification(u)) {
			// create session so we remember our user/client int he future
			out.print(om.writeValueAsString(uDao.findByUsername(u.username)));
			HttpSession ses = req.getSession(); // creation of cookie and put into response
			ses.setAttribute("username", u.username); // here we save the username in the seesion for use later.

			resp.setStatus(200);
		} else {


			Message m = new Message();
			m.setMessage("Invalid Credentials");
			out.print(om.writeValueAsString(m));
			resp.setStatus(400);
		}
	}

	public static void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(req.getSession(false)==null) { // edge check for session
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		

		if (ses != null) {
			ses.invalidate();// removes sessions from object

			// abstracted how to print to postman
			m.setMessage("You have successfully logged out " + ses.getAttribute("username"));
			PrintWriter out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(200);
			
			return;
		}

		m.setMessage("There was no user logged into the session");
		PrintWriter out = resp.getWriter();
		out.print(om.writeValueAsString(m));
		resp.setStatus(400);
	}

	public static void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(req.getSession(false)==null) {
			return;
		}
		HttpSession ses = req.getSession(); // grabs session or creates
		
		String s = (String) ses.getAttribute("username"); // read input from postman stored in session
		UserDAOImpl uDao = new UserDAOImpl(); // class to use methods form udaoImpl
		User u = uDao.findByUsername(s); // user class found from session username
		RoleDAOImpl rDao = new RoleDAOImpl(); // used to find employee or admin

		if (u.getRole().getRoleId() == 1) { // check if admin

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
			User newUser = om.readValue(body, User.class);
			PrintWriter out = resp.getWriter(); // put into body of response
			// code to read input
			// --------------------------------------------------------------

			if (uService.register(newUser)) { //
				out.print(om.writeValueAsString(uDao.findByUsername(newUser.getUsername())));
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
			PrintWriter out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(401);
		}
	}

}
