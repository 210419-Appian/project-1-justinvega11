package com.revature.models;

public class UserDTO {
	//DTO stands for data transfer object. They are temporary objects that are used to store information
	//comming form outside your application, if that information doe snot perfectly fit any perfectly existing object ype in your application.
	public String username;
	public String password;
	public UserDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
