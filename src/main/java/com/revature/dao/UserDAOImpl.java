package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.util.JdbcUtil;

public class UserDAOImpl implements UserDAO{
	private static Logger log = Logger.getLogger(UserDAOImpl.class);
	
	// connects JDBC to servlets
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.info("Static block has failed me: unable to connect JDBC to servlets. Exception message: " + e);
		}
	}
	

	
	
	
	
	@Override
	public int insert(User user) {
		log.info("adding user to database. User info: " + user);
		int isInserted = 0;
		// 1. create a connection using my ConnectionUtil class - try-with-resources
		try (Connection conn = JdbcUtil.getConnection()) {
			
			// 2. prepare my SQL statement using JDBC's PreparedStatement
			String sql = "insert into users (user_fname, user_lname, user_email, user_pwd, user_role_id) values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getFname());
			stmt.setString(2, user.getLname());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPwd());
			stmt.setInt(5, user.getUser_role_id());

			// 3. executing that query
			isInserted = stmt.executeUpdate();
			log.info("New user has been successfully created.");	
			
			String sql2 = "select user_id from users where user_email=? and user_pwd=?";
			PreparedStatement stmt2 = conn.prepareStatement(sql2); 
			stmt2.setString(1, user.getEmail()); 
			stmt2.setString(2, user.getPwd());
			
			ResultSet rs=stmt2.executeQuery(); //stmt2.executeUpdate(); 
			while (rs.next())
			{
				isInserted = rs.getInt(1);
				//setUser_id(rs.getInt(1));
				log.info("Test ID " + rs.getInt(1));
			}
			user.setUser_id(isInserted);
			log.info("New usersid has been successfully retrieved and passed to user.");
			

			// 4. close db connection to avoid memory leaks
			conn.close();
		} catch (SQLException e) {
			log.warn("Unable to execute SQL statement", e);
			
		}

		// 5. return true if successful in db
		log.info("insert successful!");
		return isInserted; //user.getUser_id();
		
	}
	
	



	
	@Override
	public User selectById(int user_id) {
		log.info("In DAO layer searching user by id: " + user_id);
		
		User user = new User();
		
		try (Connection conn = JdbcUtil.getConnection()) {
		String sql = "SELECT * FROM users WHERE user_id = ?;"; 
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, user_id);
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			user.setUser_id(rs.getInt(1));
			user.setFname(rs.getString(2));
			user.setLname(rs.getString(3));
			user.setEmail(rs.getString(4));
			user.setPwd(rs.getString(5));
			user.setUser_role_id(rs.getInt(6));

		}
		
		log.info("User search by user_id was successful. " + user);
		
		System.out.println("Search by user_id complete! Found: " + user);
		
		} catch (SQLException e) {
			log.warn("Unable to execute SQL statement", e);
		}
		
		log.info("Search complete! Found: " + user);		
		return user;
	}
	 

	
	
	@Override
	public User selectByFirstName(String fname) {
		log.info("searching user by firstName: " + fname);
		User user = new User();
		
		try (Connection conn = JdbcUtil.getConnection()) {
			String sql = "SELECT * FROM users WHERE user_fname = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, fname);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				user.setUser_id(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPwd(rs.getString(5));
				user.setUser_role_id(rs.getInt(6));
			}
			
		} catch (SQLException e) {
			log.warn("Unable to execute SQL statement", e);
		}
		
		log.info("Search complete! Found user by name: " + user);
		
		return user;
	}
	
	
	

	@Override
	public List<User> selectAll() {
		log.info("In DAO layer: getting all users from DB...");
		System.out.println("In DAO layer getting all users...");
		List<User> userList = new ArrayList<>();

		try (Connection conn = JdbcUtil.getConnection()) {
		log.info("Checking for connection..." + conn);
			
			String sql = "SELECT * FROM users ORDER BY user_id ASC"; 		
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			 /* ResultSet starts at 1 position behind the starting point of our data...So, in
			 order to access the first value, we invoke next() to start.... */ 
			
			while (rs.next()) {
				//1. get information out of the resultSet for each record
				int user_id = rs.getInt(1);
				String fname = rs.getString(2);
				String lname = rs.getString(3);
				String email = rs.getString(4);
				String pwd = rs.getString(5);
				int user_role_id = rs.getInt(6);

				// 2. make an object that matches that record info
				User u = new User(user_id, fname, lname, email, pwd, user_role_id);

				//3. add item into our list
				userList.add(u);
			}
			
			int size = 0;
			if (rs != null) 
			{
			  rs.last();    // moves cursor to the last row
			  size = rs.getRow(); // get row id 
			}
			
			log.info("List has been successfully retrieved. Number of users: " + size);
			//4. close the resultSet
			rs.close();
			
			//5. close connection
			conn.close();
		} catch (SQLException e) {
			log.warn("Unable to retrieve users from the database", e);
		}
		log.info("User list retrieval complete! Size: " + userList.size());
		return userList;
	}

	
	
	
	@Override
	public boolean update(User user) {
		log.info("Updating user. User info: " + user);
		
		try (Connection conn = JdbcUtil.getConnection()) {
			// now updating users table
			String sql = "update users set "
					+ "user_fname=?, "
					+ "user_lname=?, "
					+ "user_email=?, "
					+ "user_pwd=?, "
					+ "user_role_id=? "
					+ "where user_id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getFname());
			stmt.setString(2, user.getLname());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPwd());
			stmt.setInt(5, user.getUser_role_id());
			stmt.setInt(6, user.getUser_id());
			stmt.executeUpdate();

			log.info("User update for ID " + user.getUser_id() + " was successful. ");

		} catch (SQLException e) {
			log.warn("Unable to execute SQL statement", e);
			return false;
		}
		
		log.info("update complete");
		
		return true;
	}
	
	
	
	
	@Override
	public boolean deleteById(int user_id) {
		log.info("Deleting user. User info: " + user_id);
		
		try (Connection conn = JdbcUtil.getConnection()) {
			// now removing user from table
			String sql = "delete from users where user_id = ?;";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			
			stmt.executeUpdate();

			log.info("User removal for user_id " + user_id + " was successful. ");

		} catch (SQLException e) {
			log.warn("Unable to execute SQL statement", e);
			return false;
		}
		
		log.info("deletion complete");
		
		return true;
	}	
	
	
	
	
	
	/*
	 * public int insertRole(UserRole role, Session ses, Transaction tx) {
	 * log.info("adding role to database. User role: " + role);
	 * 
	 * int roleId = (int) ses.save(role);
	 * 
	 * tx.commit();
	 * 
	 * log.info("role has been saved as: " + roleId); return roleId; }
	 */
	
	
	
	/*
	 * public UserRole getUserRoleById(int id, Session ses) {
	 * log.info("get role by id. id: " + id);
	 * 
	 * UserRole role = (UserRole) ses.
	 * createNativeQuery("SELECT * FROM store_userRoles WHERE store_userRole_id=" +
	 * id + "", UserRole.class).getSingleResult();
	 * 
	 * return role; }
	 */	

	


/*
 * @Override public boolean update(User user) {
 * System.out.println("Updating user. New user info: " + user); Session ses =
 * HibernateUtil.getSession(); Transaction tx = ses.beginTransaction();
 * ses.clear();
 * 
 * //ses.update(user); String sql = String.
 * format("update store_users set store_user_username='%s', store_user_password='%s', store_user_firstname='%s', store_user_lastname='%s' where store_user_id=%d"
 * , user.getUsername(), user.getPassword(), user.getFirstName(),
 * user.getLastName(), user.getId()); ses.createNativeQuery(sql,
 * User.class).executeUpdate(); tx.commit();
 * 
 * System.out.println("Update complete!");
 * 
 * return true; }
 */
	
	
	

	/*
	 * @Override public boolean delete(User user) {
	 * System.out.println("Deleting user. Removed user info: " + user); Session ses
	 * = HibernateUtil.getSession(); Transaction tx = ses.beginTransaction();
	 * ses.clear(); ses.delete(user);
	 * 
	 * tx.commit();
	 * 
	 * System.out.println("Deletion complete!");
	 * 
	 * return true; }
	 */

}
