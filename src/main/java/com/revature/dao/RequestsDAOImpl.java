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
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String date = rs.getDate(3).toString();
				LocalDate submitted = LocalDate.parse(date, formatter);
			requ.setSubmitted(submitted);
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
				r.setResolver(rs.getInt(8));
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
				r.setResolver(rs.getInt(8));
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
	public int submit(Request requ) {
		
		return 1;
	}

	@Override
	public boolean editRequ(Request requ) {
		
		return false;
	}

	@Override
	public boolean deleteRequById(int requ_id) {
		
		return false;
	}


}
