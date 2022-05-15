package com.revature.models;

public class UserJwtDTO {
	private int user_id;
	private String email;
	//Why not password?
	//// JWTs shouldn't contain sensitive information like passwords
	//private String password;
	private String fname;
	private String lname;
	//no worries to do much here since Role is just a int and String already
	private int user_role_id;
	public UserJwtDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserJwtDTO(int user_id, String email, String fname, String lname, int user_role_id) {
		super();
		this.user_id = user_id;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.user_role_id = user_role_id;
	}
	
	public UserJwtDTO(int user_id, String email, String fname, String lname, String user_role_id) {
		// TODO Auto-generated constructor stub
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	@Override
	public String toString() {
		return "UserJwtDTO [user_id=" + user_id + ", email=" + email + ", fname=" + fname + ", lname=" + lname + ", user_role_id="+ user_role_id + "]";
	}
}