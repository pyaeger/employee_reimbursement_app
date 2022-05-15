package com.revature.services;

import java.util.List;

import com.revature.models.User;

public interface UserService {

	public User login(String email, String pwd);
	
	public int register(User user);
	
	public User findUserById(int user_id); 
	
	public User findUserByFirstName(String fname);	
	
	public List<User> findAllUsers();
	
	public boolean editUser(User user);
	
	public boolean deleteUserById(int user_id);

}
