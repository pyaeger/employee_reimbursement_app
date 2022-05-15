package com.revature.models;

//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;


public class User {
	

	private int user_id;
	private String fname;
	private String lname;
	private String email;
	private String pwd;
	private int user_role_id=1;
	
	public User() {
		super();
	}

	public User(String email, String pwd) {
		super();
		this.email = email;
		this.pwd = pwd;
	}
	
	

	public User(String fname, String lname, String email, String pwd) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.pwd = pwd;
	}

	public User(String fname, String lname, String email, String pwd, int user_role_id) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.pwd = pwd;
		this.user_role_id = user_role_id;
	}

	public User(int user_id, String fname, String lname, String email, String pwd, int user_role_id) {
		super();
		this.user_id = user_id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.pwd = pwd;
		this.user_role_id = user_role_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + user_id;
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		//result = prime * result + ((user_role_id == null) ? 0 : user_role_id.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (user_id != other.user_id)
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		} else if (!lname.equals(other.lname))
			return false;
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		/*
		 * if (user_role_id == null) { if (other.user_role_id != null) return false; }
		 * else if (!user_role_id.equals(other.user_role_id)) return false;
		 */
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", pwd="
				+ pwd + ", user_role_id=" + user_role_id + "]";
	}
	
	


	
	
	
	
}
