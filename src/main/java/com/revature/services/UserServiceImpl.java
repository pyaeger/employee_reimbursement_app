package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.User;

public class UserServiceImpl implements UserService{
	
	private UserDAO udao;
	private static Logger log = Logger.getLogger(UserServiceImpl.class);
	
	//introducing dependency injection through use of a construction injection
	//so we are not manually injecting what we need for this class; we are handing that control over to the application
	public UserServiceImpl(UserDAOImpl dao) {
		super();
		this.udao = dao; //this is the same as this: private static UserDAO udao = new UserDAOImpl();
	}

	
	
	@Override
	public User login(String email, String pwd) {
		log.info("in service layer. Logging in user with credentials: " + email + ", " + pwd);
		//utilize Streams API
		Optional<User> users = udao.selectAll()
				.stream()
				.filter(u -> (u.getEmail().equals(email) && u.getPwd().equals(pwd))) //filter out to all users that match criteria/condition
				.findFirst(); //returns the element that is left after filtering
		log.info("Continuing log in process in User Service Impl layer: " + users);
		return (users.isPresent() ? users.get() : null);
	}

	
	
	@Override
	public int register(User user) {
		log.info("in service layer. Registering user: " + user);
		return udao.insert(user);
	}

	
	
	@Override
	public User findUserById(int user_id) {
		log.info("in service layer. searching user by user_id: " + user_id);
		return udao.selectById(user_id);
	}

	
	
	@Override
	public User findUserByFirstName(String fname) {
		log.info("in service layer. searching user by first name: " + fname);
		return udao.selectByFirstName(fname);
	}
	 


	@Override
	public List<User> findAllUsers() {
		log.info("in service layer. finding all users...");
		return udao.selectAll();
	}

	@Override
	public boolean editUser(User user) {
		log.info("in service layer. editing user: " + user);
		return udao.update(user);
	}

	@Override
	public boolean deleteUserById(int user_id) {
		log.info("in service layer. removing user by user_id: " + user_id);
		return udao.deleteById(user_id);
	}

}