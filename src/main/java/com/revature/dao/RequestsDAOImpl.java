package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import com.revature.util.JdbcUtil;
import com.revature.models.Request;
import com.revature.models.User;



public class RequestsDAOImpl implements RequestsDAO {
	private static Logger log = Logger.getLogger(RequestsDAOImpl.class);
	
	// connects JDBC to servlets
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.info("Static block has failed me: unable to connect JDBC to servlets. Exception message: " + e);
		}
	}
	
	
	@Override
	public Request findRequById(int requ_id) {
		
		log.info("In DAO layer searching user by id: " + requ_id);
		
		Request requ = new Request();
		
		try (Connection conn = JdbcUtil.getConnection()) {
		String sql = "SELECT requ_id,amount,submitted,description,author,status_id,type_id FROM requests WHERE requ_id = ?;"; 
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, requ_id);
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			requ.setRequ_id(rs.getInt(1));
			requ.setAmount(rs.getDouble(2));
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//				String date = rs.getDate(3).toString();
//				LocalDate submitted = LocalDate.parse(date, formatter);
			Timestamp subm = rs.getTimestamp(3);
			LocalDate ldat = subm.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			requ.setSubmitted(ldat);
			requ.setDescription(rs.getString(4));
			requ.setAuthor(rs.getInt(5));
			requ.setStatus_id(rs.getInt(6));
			requ.setType_id(rs.getInt(7));

		}
		
		log.info("User search by requ_id was successful. " + requ);
		
		System.out.println("Search by requ_id complete! Found: " + requ);
		
		} catch (SQLException e) {
			log.warn("Unable to execute SQL statement", e);
		}
		
		log.info("Search complete! Found: " + requ);		
		return requ;
		
	}

	
	@Override
	public List<Request> findAllRequByAuthorStatus(int author, int status_id) {
		
		log.info("In DAO layer: getting requests based on author and status from DB...");
		System.out.println("In DAO layer getting specified requests...");
		List<Request> requList = new ArrayList<Request>();

		try (Connection conn = JdbcUtil.getConnection()) {
		log.info("Checking for connection..." + conn);
			
			String sql = "SELECT * FROM requests WHERE author = ? and status_id = ?"; 		
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, author);
			stmt.setInt(2, status_id);
			ResultSet rs = stmt.executeQuery();

			 /* ResultSet starts at 1 position behind the starting point of our data...So, in
			 order to access the first value, we invoke next() to start.... */ 

			while (rs.next()) {
				Request r = new Request();
				//1. get information out of the resultSet for each record
				r.setRequ_id(rs.getInt(1));
				r.setAmount(rs.getDouble(2));
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String date = rs.getDate(3).toString();
					LocalDate submitted = LocalDate.parse(date, formatter);
				r.setSubmitted(submitted);
					if(rs.getDate(4) != null) {
					DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String date2 = rs.getDate(4).toString();
					LocalDate resolved = LocalDate.parse(date2, formatter2);
				r.setResolved(resolved);}				
				r.setDescription(rs.getString(5));
				r.setReceipt(rs.getByte(6));
				r.setAuthor(rs.getInt(7));
				r.setResolver(rs.getString(8));
				r.setStatus_id(rs.getInt(9));
				r.setType_id(rs.getInt(10));

				requList.add(r);
			}
			
			/*
			 * int size = 0; if (rs != null) { rs.last(); // moves cursor to the last row
			 * size = rs.getRow(); // get row id }
			 */
			
			log.info("List has been successfully retrieved.");
			//4. close the resultSet
			rs.close();
			
			//5. close connection
			conn.close();
		} catch (SQLException e) {
			log.warn("Unable to retrieve requests from the database", e);
		}
		log.info("Requests list retrieval complete! Size: ");
		
		return requList;		
	}	
	
	
	
	@Override
	public List<Request> findAllRequByStatus(int status_id) {

		log.info("In DAO layer: getting requests based on status_id from DB...");
		System.out.println("In DAO layer getting specified requests...");
		List<Request> requList = new ArrayList<>();

		try (Connection conn = JdbcUtil.getConnection()) {
		log.info("Checking for connection..." + conn);
			
			String sql = "SELECT * FROM requests WHERE status_id = ?"; 		
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, status_id);
			ResultSet rs = stmt.executeQuery();

			 /* ResultSet starts at 1 position behind the starting point of our data...So, in
			 order to access the first value, we invoke next() to start.... */ 
			
			while (rs.next()) {
				Request r = new Request();
				//1. get information out of the resultSet for each record
				r.setRequ_id(rs.getInt(1));
				r.setAmount(rs.getDouble(2));
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String date = rs.getDate(3).toString();
					LocalDate submitted = LocalDate.parse(date, formatter);
				r.setSubmitted(submitted);
					if(rs.getDate(4) != null) {
					DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String date2 = rs.getDate(4).toString();
					LocalDate resolved = LocalDate.parse(date2, formatter2);
				r.setResolved(resolved);}				
				r.setDescription(rs.getString(5));
				r.setReceipt(rs.getByte(6));
				r.setAuthor(rs.getInt(7));
				r.setResolver(rs.getString(8));
				r.setStatus_id(rs.getInt(9));
				r.setType_id(rs.getInt(10));

				//3. add item into our list
				requList.add(r);
			}
			
			/*
			 * int size = 0; if (rs != null) { rs.last(); // moves cursor to the last row
			 * size = rs.getRow(); // get row id }
			 */
			
			log.info("List has been successfully retrieved.");
			//4. close the resultSet
			rs.close();
			
			//5. close connection
			conn.close();
		} catch (SQLException e) {
			log.warn("Unable to retrieve requests from the database", e);
		}
		log.info("Requests list retrieval complete! Size: ");
		
		return requList;		
	}
	
	
	


	@Override
	public int submit(Request r) {
		log.info("adding request to database. Request info: " + r);
		int isInserted = 0;
		// 1. create a connection using my ConnectionUtil class - try-with-resources
		try (Connection conn = JdbcUtil.getConnection()) {
			
			// 2. prepare my SQL statement using JDBC's PreparedStatement
			String sql = "insert into requests (amount, description, author, status_id, type_id) values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, r.getAmount());
			stmt.setString(2, r.getDescription());
			stmt.setInt(3, r.getAuthor());
			stmt.setInt(4, r.getStatus_id());
			stmt.setInt(5, r.getType_id());
			
			
//			double amount = Double.parseDouble(values.get(0));
//			String description = values.get(1);
//			byte receipt = Byte.parseByte(values.get(2));
//			int author = Integer.parseInt(values.get(3));
//			int status_id = Integer.parseInt(values.get(4));
//			String type_id = values.get(5);

			// 3. executing that query
			isInserted = stmt.executeUpdate();
			log.info("New request has been successfully created.");	
			
			String sql2 = "select requ_id from requests where amount=? and description=?";
			PreparedStatement stmt2 = conn.prepareStatement(sql2); 
			stmt2.setDouble(1, r.getAmount()); 
			stmt2.setString(2, r.getDescription());
			
			ResultSet rs=stmt2.executeQuery(); //stmt2.executeUpdate(); 
			while (rs.next())
			{
				isInserted = rs.getInt(1);
				//setUser_id(rs.getInt(1));
				log.info("Test ID " + rs.getInt(1));
			}
			r.setRequ_id(isInserted);
			log.info("New requ_id has been successfully retrieved and passed to r.");
			

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
	public boolean editRequ(Request requ) {
		log.info("Editing request. Edits: " + requ);
		
		
		try (Connection conn = JdbcUtil.getConnection()) {
			String sql = "SELECT status_id,resolver FROM requests WHERE requ_id = ?;"; 
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, requ.getRequ_id());
			ResultSet rs = stmt.executeQuery();
			
//			if (rs.next()) {
//				requ.setStatus_id(rs.getInt(1));
//				requ.setResolver(rs.getString(2));
//			}
		
			// now making edits to Request
			String sql2 = "update requests where requ_id =? set "
					+ "status_id=?, "
					+ "resolver=?";
			PreparedStatement stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, requ.getRequ_id());
			stmt2.setInt(2, requ.getStatus_id());
			stmt2.setString(3, requ.getResolver());
			stmt2.executeUpdate();

			log.info("Request update for ID " + requ.getRequ_id() + " was successful! ");

		} catch (SQLException e) {
			log.warn("Unable to execute SQL statement", e);
			return false;
		}
		
		log.info("Update complete.");
		
		return true;
	}
	
	
	
	
	
	
	
	@Override
	public Request findAllRequByType(int type_id) {
		
		return null;
	}

	@Override
	public Request findAllRequByAuthor(int author) {
		
		return null;
	}

	@Override
	public Request findAllRequByResolver(int resolver) {
		
		return null;
	}

	@Override
	public List<Request> findAllRequ() {
		
		return null;
	}
	

	



	@Override
	public boolean deleteRequById(int requ_id) {
		
		return false;
	}


}
