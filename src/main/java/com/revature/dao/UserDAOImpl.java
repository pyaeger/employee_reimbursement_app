package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.util.ConnectionUtil;
import static java.lang.System.out;

public class UserDAOImpl implements UserDAO{
	private static Logger log = Logger.getLogger(UserDAOImpl.class);
	
	
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
	
	
	
	
	@Override
	public int insert(User user) {
		log.info("adding user to database. User info: " + user);
		
		// 1. create a connection using my ConnectionUtil class - try-with-resources
		try (Connection conn = ConnectionUtil.getConnection()) {
			log.info("In DAO layer: making a new user in db...");

			String sql = "insert into users (user_fname, user_lname, user_email, user_pwd, user_role_id) values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getFname());
			stmt.setString(2, user.getLname());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPwd());
			stmt.setString(5, user.getUser_role_id());

			// 3. executing that query
			stmt.executeUpdate();
			log.info("New user has been successfully created.");	
			
			// out.println(u.getUserId());
			
			String sql2 = "select user_id from users where user_email=? and user_pwd=?";
			PreparedStatement stmt2 = conn.prepareStatement(sql2);
			stmt2.setString(1, user.getEmail());
			stmt2.setString(2, user.getPwd());
			
			// stmt2.executeUpdate();
			ResultSet rs=stmt2.executeQuery();
			while (rs.next())
			{
				int targetUserId=rs.getInt(1);
				user.setUser_id(targetUserId);
			}
			log.info("New usersid has been successfully retrieved and assigned to user.");
			

			// 4. close db connection to avoid memory leaks
			conn.close();
		} catch (SQLException e) {
			log.warn("Unable to execute SQL statement", e);
			return 12345;
		}

		// 4. return true if insert and select queries were successful in db
		return 12345;
		
	}
	
	



	/*
	 * @Override public User selectById(int id) {
	 * System.out.println("searching user by id: " + id); Session ses =
	 * HibernateUtil.getSession(); User user = (User)
	 * ses.createNativeQuery("SELECT * FROM store_users WHERE store_user_id = " + id
	 * + "", User.class).getSingleResult();
	 * 
	 * System.out.println("Search complete! Found: " + user);
	 * 
	 * return user; }
	 */


	
	
	

	@Override
	public List<User> selectAll(User user) {
		log.info("In DAO layer: getting all users from DB...");
		System.out.println("getting all users...");
		
		//Session ses = HibernateUtil.getSession();
		//List<User> userList = ses.createQuery("FROM User ORDER BY id", User.class).list();
		//return userList;

	List<User> list = new ArrayList<User>();

	try (Connection conn = ConnectionUtil.getConnection()) {
	
			String sql = "SELECT * FROM users ORDER BY user_id ASC"; 
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			/*
			 * ResultSet starts at 1 position behind the starting point of our data...So, in
			 * order to access the first value, we invoke next() to start....
			 */
			while (rs.next()) {
				//1. get information out of the resultSet for each record
				int user_id = rs.getInt("user_id");
				String fname = rs.getString("user_fname");
				String lname = rs.getString("user_lname");
				String email = rs.getString("user_email");
				String pwd = rs.getString("user_pwd");
				String user_role_id = rs.getString("user_role_id");

				user.setUser_id(user_id);
				user.setFname(fname);
				user.setLname(lname);
				user.setEmail(email);
				user.setPwd(pwd);
				user.setUser_role_id(user_role_id);

				// 2. make an object that matches that record info
				User u2 = new User(user_id, fname, lname, email, pwd, user_role_id);

				//3. add item into our list
				list.add(u2);
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
	System.out.println("User list retrieval complete! Size: " + list.size());
	return list;
}

	
	
	

	


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
