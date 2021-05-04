package com.revature.daos;

import com.revature.models.Role;

public interface RoleDAO {
	public Role findById(int id); // return role object given role ID
	
}
