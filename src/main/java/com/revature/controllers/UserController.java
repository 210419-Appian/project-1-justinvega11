package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.services.UserService;

public class UserController {

	private UserService uService = new UserService();
	private static ObjectMapper om = new ObjectMapper();

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

	public static void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		UserDTO u = new UserDTO();
		UserService a = new UserService();
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
		
		System.out.println(u);
		
		RequestDispatcher rd = null;
		PrintWriter out = resp.getWriter(); // put into body of response
		
		if (a.loginVerification(u)) {
			// create session so we remember our user/client int he future
			HttpSession ses = req.getSession(); // creation of cookie and put into response
			ses.setAttribute("username", u.username); // here we save the username in the seesion for use later.
			rd = req.getRequestDispatcher("success"); //
			rd.forward(req, resp);
		} else {
			 rd=req.getRequestDispatcher("Index.html");
			rd.include(req, resp);
			out.println("wrong!"); // might not work
		}
	}

}
