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

	public void getUser(HttpServletResponse resp, int id) throws IOException {
		// TODO Auto-generated method stub
		User u = uService.findUser(id); // grabs avenger based on ID

		// convert jhava object into a json string that can be written to the body of an
		// HTTP response
		String json = om.writeValueAsString(u);
		System.out.println(json);

		PrintWriter pw = resp.getWriter(); //
		pw.print(json);
		resp.setStatus(200); // returns status

	}

	public void getAllUsers(HttpServletResponse resp) throws IOException {
		List<User> list = uService.findAll();

		// convert jhava object into a json string that can be written to the body of an
		// HTTP response
		String json = om.writeValueAsString(list);
		System.out.println(json);

		PrintWriter pw = resp.getWriter(); //
		pw.print(json);
		resp.setStatus(200); // returns status

	}

	public static void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
		UserDTO u = new UserDTO();
		UserService a = new UserService();
		UserDAOImpl uDao = new UserDAOImpl();
		u.username = req.getParameter("username"); // checks request from HTML and storing into DTO object
		u.password = req.getParameter("password");

		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();

		}

		String body = new String(sb);
		u = om.readValue(body, UserDTO.class);
		PrintWriter out = resp.getWriter(); // put into body of response
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
		HttpSession ses = req.getSession(false); // grabs session

		if (ses != null) {

			m.setMessage("You have successfully logged out " + ses.getAttribute("username"));
			PrintWriter out = resp.getWriter();
			out.print(om.writeValueAsString(m));
			resp.setStatus(200);
			ses.invalidate();// removes sessions from object
			return;
		}

		m.setMessage("There was no user logged into the session");
		PrintWriter out = resp.getWriter();
		out.print(om.writeValueAsString(m));
		resp.setStatus(400);
	}

	public static void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession ses = req.getSession(false); // grabs session
		s = (String) ses.getAttribute("username");
		UserDAOImpl uDao = new UserDAOImpl();
		User u = uDao.findByUsername(s);
		RoleDAOImpl rDao = new RoleDAOImpl();

		if (u.getRole().getRoleId() == 1) { // check if admin
			
			//UserService a = new UserService();
			
//			u.setUserId(Integer.parseInt(req.getParameter("userid")));
//			u.setUsername(req.getParameter("username"));  // checks request from HTML and storing into DTO object
//			u.setPassword(req.getParameter("password"));
//			u.setFirstName(req.getParameter("firstname"));
//			u.setLastName(req.getParameter("lastname"));
//			u.setEmail(req.getParameter("email"));
//			u.setRole(rDao.findById(Integer.parseInt(req.getParameter("Role"))));
//			
			
			BufferedReader reader = req.getReader();
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();

			}

			String body = new String(sb);
			User newUser = om.readValue(body, User.class);
			PrintWriter out = resp.getWriter(); // put into body of response
			// String json = om.writeValueAsString(User);
			
			if (uService.register(newUser)) {
				
				out.print(om.writeValueAsString(uDao.findByUsername(newUser.getUsername())));
				
				resp.setStatus(200);
			} else {

				Message m = new Message();
				m.setMessage("Invalid Credentials");
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
