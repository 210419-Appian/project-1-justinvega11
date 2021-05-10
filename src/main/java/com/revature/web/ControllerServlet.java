package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.controllers.AccountController;
import com.revature.controllers.UserController;
import com.revature.daos.UserDAOImpl;
import com.revature.models.UserDTO;
import com.revature.services.UserService;

public class ControllerServlet extends HttpServlet {

	private String BaseURL = null; // variable for the Base URL
	private UserController uControl = new UserController(); // object to use from usercontroller
	private AccountController aControl = new AccountController();
	private static int id;

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

		switch (sections[0]) {
		case "user": // avengers endpoint
			if (req.getMethod().equals("GET")) {
				if (sections.length == 2) {
					id = Integer.parseInt(sections[1]); // FINISH
					uControl.getUser(req, resp, id); // FINISH
				} else {
					uControl.getAllUsers(req, resp);
				}
			} else if (req.getMethod().equals("PUT")) {
				uControl.updateUser(req, resp);
			}

			break;
		case "login":
			if (req.getMethod().equals("POST")) {
				uControl.login(req, resp);
			}
			break;
		case "logout":
			if (req.getMethod().equals("POST")) {
				uControl.logout(req, resp);
			}
		case "register":
			if (req.getMethod().equals("POST")) {
				uControl.register(req, resp);
			}break;
		case "accounts":
			if (req.getMethod().equals("POST")) {
				if (sections.length == 2) {
					if (sections[1].equals("withdraw")) {
						aControl.withdraw(req, resp);
					} else if (sections[1].equals("deposit")) {
						aControl.deposit(req, resp);
					} else if (sections[1].equals("transfer")) {
						aControl.transfer(req, resp);
					}
				} else if (sections.length == 1) {
					AccountController.submitAccount(req, resp);
				}
			} else if (req.getMethod().equals("GET")) {
				if (sections.length == 2) {
					id = Integer.parseInt(sections[1]); // grab id from url
					AccountController.getAccountById(req, resp, id); // grab avenger based on id
				} else if (sections.length == 1) {
					aControl.getAllAccounts(req, resp);
				}
				if (sections.length == 3) {
					if (sections[1].equals("status")) {
						id = Integer.parseInt(sections[2]);

						AccountController.getAccountByStatusId(req, resp, id);
					} else if (sections[1].equals("owner")) {
						id = Integer.parseInt(sections[2]);

						AccountController.getAccountByUser(req, resp, id);
					}
				}

			} else if (req.getMethod().equals("PUT")) {
				AccountController.updateAccount(req,resp,id);
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
