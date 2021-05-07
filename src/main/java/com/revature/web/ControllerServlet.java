package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.controllers.UserController;
import com.revature.models.UserDTO;
import com.revature.services.UserService;

public class ControllerServlet extends HttpServlet {

	private String BaseURL = null; // variable for the Base URL
	private UserController avControl = new UserController(); // object to use from usercontroller

	@Override
	public void init(ServletConfig config) throws ServletException { // initiate servlet
		BaseURL = config.getInitParameter("BaseURL"); 
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setStatus(404); // default if someone sends a malformed request
		final String URL = req.getRequestURI().replace(BaseURL, "");

		System.out.println(URL);
		// a path variable is a way to pass information about the request int he URL
		// itself generally you have a final '/' in the url that takes a variable input
		// i.e avenger/1 will get you the first avenger while avenger/2 gets teh second
		String[] sections = URL.split("/");
		System.out.println(sections);
		
		switch (sections[0]) {
		case "user": // avengers endpoint
			if (req.getMethod().equals("GET")) {
				if (sections.length == 2) {
					int id = Integer.parseInt(sections[1]); // grab id from url
					avControl.getUser(resp, id); // grab avenger based on id
				} else {
					avControl.getAllUsers(resp);
				}
//			} else if (req.getMethod().equals("POST")) {
//				avControl.addAvenger(req, resp);
//			} else if (req.getMethod().equals("PUT") && sections.length == 2) {
//				avControl.putAvenger(req, resp);
//			} else if (req.getMethod().equals("PATCH") && sections.length == 2) {
//				avControl.patchAvenger(req, resp);
//			} else if (req.getMethod().equals("DELETE") && sections.length == 2) {
//				avControl.killAvenger(resp, sections[1]);
			}

			break;
		case "login":
			if (req.getMethod().equals("POST")) {
				
					UserController.login(req,resp);
				}
			break;
		case "logout":
			if (req.getMethod().equals("POST")) {
				UserController.logout(req,resp);
			}

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);

	}

	protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getMethod().equals("PATCH")) {
			doPatch(req, resp);

		} else {
			super.service(req, resp);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
