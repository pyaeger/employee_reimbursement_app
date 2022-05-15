package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {
	
	//basic CRUD operations
	//CREATE, READ, UPDATE, AND DELETE
	
	public int insert(User user);
	
	public User selectById(int user_id);
	
	public User selectByFirstName(String fname);
	
	public List<User> selectAll();
	
	public boolean update(User user);
	
	public boolean deleteById(int user_id);
}
