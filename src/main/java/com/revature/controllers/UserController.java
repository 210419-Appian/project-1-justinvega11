package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserController {
	
	private UserService uService = new UserService();
	private ObjectMapper om = new ObjectMapper();
	
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

	public void getAllUsers(HttpServletResponse resp) throws IOException{
		List<User> list = uService.findAll();

		// convert jhava object into a json string that can be written to the body of an
		// HTTP response
		String json = om.writeValueAsString(list);
		System.out.println(json);

		PrintWriter pw = resp.getWriter(); //
		pw.print(json);
		resp.setStatus(200); // returns status
		
	}

}
