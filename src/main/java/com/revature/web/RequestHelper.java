package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.UserDAOImpl;
import com.revature.models.User;
import com.revature.services.JwtService;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

/*
 * This class will serve as the main driver for all requests coming from the FrontController!
 * NOTE: we will not need a main method as Tomcat will be running our app rather than the JVM.
 */

public class RequestHelper {
	private static UserService userv = new UserServiceImpl(new UserDAOImpl());
	private static JwtService jwtService = new JwtService();
	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static ObjectMapper om = new ObjectMapper();
	
	
	
	

	/****************************
	 * 		POST METHODS		
	 * @throws IOException *
	 ****************************/
	// This method will process a post request, so we can't capture parameters from the request like we would in a GET request
	
	
	
	
	
	// This method will process a post request, so we can't capture parameters from the request like we would in a GET request
	public static void processLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// we need to capture the user input from the request BODY 
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder(); // email=bob&password=pass -> we need to extract bob and pass, but first we transform to string
		
		// transfer everything over to the string builder FROM te buffered reader
		String line = reader.readLine();
		
		while(line!= null) {
			s.append(line);
			line = reader.readLine();  //  req body looks like this: email=bob&password=secret
		}
		
		String body = s.toString(); 
		String [] sepByAmp = body.split("&"); // separate email=bob&password=pass -> [email=bob, password=pass]
		
		List<String> values = new ArrayList<String>();
		
		for (String pair : sepByAmp) { // each element in array looks like this
			values.add(pair.substring(pair.indexOf("=") + 1)); // trim each String element in the array to just value -> [bob, pass]
		}
		
		// capture the actual email and password values
		String email = values.get(0); // bob
		String pwd = values.get(1); // pass
		
		System.out.println("User attempted to login with email " + email);
		
		// call the confirmLogin() method and fetch the actual User object from the DB
		User u = userv.login(email, pwd);
		
		// return the user found and show the object in the browser
		if (u != null) {
			// Utilize JwtService to create a JSON web token with user information inside to send with response
			String jwt = jwtService.createJwt(u);
			resp.addHeader("X-Auth-Token", "Bearer " + jwt); 
			
			// grab the session & add the user to the session
			HttpSession session = req.getSession();
			session.setAttribute("user", u);
			
			// print the logged in user to the screen
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			
			// convert the object with the object mapper
			out.println(om.writeValueAsString(u));
			
			// log it!
			System.out.println("The user with email " + email + " has logged in.");
		} else {
			// if the returned object is null, return No Content status (successfull request, but no user found in DB).
			resp.setStatus(204); 
		}
		
	}
	 
	 
	
	/****************************
	 * 		GET METHODS			
	 * @throws IOException 
	 * @throws ServletException *
	 ****************************/

	
	
	
	
	public static void processError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// if something goes wrong, redirect the user to a custom 404 error page
		req.getRequestDispatcher("error.html").forward(req, resp);
	        /*
		 * Remember that the forward() method does NOT produce a new request,
		 * it just forwards it to a new resource, and we also maintain the URL
		*/
	}
	
	
	public static void processAllUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("in requesthelper. getting users....");
		// 1. set the content type to return JSON to the browser
		resp.setContentType("application/json");
		
		// 2. get a list of all users in the database
		List<User> allUsers = userv.findAllUsers();
		// 3. turn that list of java objects into a JSON string (using Jackson)
		String json = om.writeValueAsString(allUsers);
		
		// 4. use a PrintWriter to write the objects to the response body which will be seen in the browser
		PrintWriter out = resp.getWriter();
		out.println(json);
		
		log.info("leaving requesthelper");
	}	
	
	


	
	
	
	
	public static void processUserBySearchParam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...searching user by param...");
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString(); 
		String [] sepByAmp = body.split("&");
		
		List<String> values = new ArrayList<String>();
		
		for (String pair : sepByAmp) { // each element in array looks like this
			values.add(pair.substring(pair.indexOf("=") + 1)); // trim each String element in the array to just value -> [bob, pass]
		}
		log.info("User attempted to register with information:\n " + body);
		
		// determine what type of search is needed
		if(body.startsWith("user_id")) {
			//1. set the content type to return text to the browser
			resp.setContentType("application/json");
			
			// 2. Get user in the Database by id
			int user_id = Integer.parseInt(values.get(0));
			User user = userv.findUserById(user_id);
			
			// 3. Turn the list of Java Objects into a JSON string (using Jackson Databind Object Mapper).
			String json = om.writeValueAsString(user);
			
			// 4. Use a Print Writer to write the objects to the response body seen in the browser
			PrintWriter out = resp.getWriter();
			out.println(json);
			
		}else if(body.startsWith("fname")) {
			//1. set the content type to return text to the browser
			resp.setContentType("application/json");
			
			// 2. Get user in the Database by firstname
			String fname = values.get(0);
			User user = userv.findUserByFirstName(fname);
			
			// 3. Turn the list of Java Objects into a JSON string (using Jackson Databind Object Mapper).
			String json = om.writeValueAsString(user);
			
			// 4. Use a Print Writer to write the objects to the response body seen in the browser
			PrintWriter out = resp.getWriter();
			
			out.println(json);
		}
	}	
	
	


	 
	
	
	
	
	
	

	public static void processRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processRegistration...");
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString(); 
		String [] sepByAmp = body.split("&"); // separate username=bob&password=pass -> [username=bob, password=pass]
		
		List<String> values = new ArrayList<String>();
		
		for (String pair : sepByAmp) { // each element in array looks like this
			values.add(pair.substring(pair.indexOf("=") + 1)); // trim each String element in the array to just value -> [bob, pass]
		}
		log.info("User attempted to register with information:\n " + body);
		// capture the actual email and password values
		String fname = values.get(0);
		String lname = values.get(1);		
		String email = values.get(2); // bob
		String pwd = values.get(3); // pass

		// by default, all users will be automatically registered as member and not manager //
		
		User u = new User(fname, lname, email, pwd);
		int targetId = userv.register(u);

		if (targetId != 0) {
			PrintWriter pw = resp.getWriter();
			u.setUser_id(targetId);
			log.info("New user: " + u);
			String json = om.writeValueAsString(u);
			pw.println(json);
			System.out.println("JSON:\n" + json);
			
			resp.setContentType("application/json");
			resp.setStatus(200); // SUCCESSFUL!
			log.info("User has successfully been created.");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204); // this means that the connection was successful but no user created!
		}
		log.info("leaving request helper now...");
	}
	
	
	
	public static void processUserUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processUserUpdate...");
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString(); 
		String [] sepByAmp = body.split("&"); // separate email=bob&password=pass -> [email=bob, password=pass]
		
		List<String> values = new ArrayList<String>();
		
		for (String pair : sepByAmp) { // each element in array looks like this
			values.add(pair.substring(pair.indexOf("=") + 1)); // trim each String element in the array to just value -> [bob, pass]
		}
		log.info("User attempted to update with information:\n " + body);
		// capture the actual email and password values
		int user_id = Integer.parseInt(values.get(0)); //id numbers cannot be modifed! 
		String fname = values.get(1); // bob 
		String lname = values.get(2); // pass 
		String email = values.get(3); 
		String pwd = values.get(4); 
		int user_role_id = Integer.parseInt(values.get(5));	
				
		User tempUser = new User();
		tempUser.setUser_id(user_id);
		tempUser.setFname(fname);
		tempUser.setLname(lname); 		
		tempUser.setEmail(email); 
		tempUser.setPwd(pwd); 
		tempUser.setUser_role_id(user_role_id);
		boolean isUpdated = userv.editUser(tempUser);

		if (isUpdated) {
			PrintWriter pw = resp.getWriter();
			log.info("Edit successful! New user info: " + tempUser);
			String json = om.writeValueAsString(tempUser);
			pw.println(json);
			System.out.println("JSON:\n" + json);
			
			resp.setContentType("application/json");
			resp.setStatus(200); // SUCCESSFUL!
			log.info("User has successfully been edited.");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(400); // this means that the connection was successful but no user was updated!
		}
		log.info("leaving request helper now...");
	}	
	

	public static void processUserDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processUserDelete...");
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString(); 
		String [] sepByAmp = body.split("&");
		
		List<String> values = new ArrayList<String>();
		
		for (String pair : sepByAmp) { // each element in array looks like this
			values.add(pair.substring(pair.indexOf("=") + 1)); // trim each String element in the array to just value -> [bob, pass]
		}
		log.info("User attempted to update with information:\n " + body);
		// capture the actual ID value from the body
		int user_id = Integer.parseInt(values.get(0));
		
		boolean isDeleted = userv.deleteUserById(user_id);

		if (isDeleted) {
			PrintWriter pw = resp.getWriter();
			log.info("Delete successful! Removed user by user_id: " + user_id);
			String json = om.writeValueAsString("User ID#" + user_id + " has been successfully removed!");
			pw.println(json);
			System.out.println("JSON:\n" + json);
			
			resp.setContentType("application/json");
			resp.setStatus(200); // SUCCESSFUL!
			log.info("User has successfully been edited.");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(400); // this means that the connection was successful but no user was deleted!
		}
		log.info("leaving request helper now...");
		
	}

	
	
	/*
	 * public static void processAllUsers(HttpServletRequest request,
	 * HttpServletResponse response) throws IOException { // 1. set the content type
	 * to return text to the browser response.setContentType("application/json");
	 * 
	 * // 2. Get a list of all employees in the Database User u = new User();
	 * List<User> allUsers = userv.findAllUsers(u);
	 * 
	 * // 3. Turn the list of Java Objects into a JSON string (using Jackson
	 * Databind Object Mapper). String json = om.writeValueAsString(allUsers);
	 * 
	 * // 4. Use a Print Writer to write the objects to the response body seen in
	 * the browser PrintWriter out = response.getWriter(); out.println(json); }
	 */	
	
	
	/*
	 * public static void processUserBySearchParam(HttpServletRequest request,
	 * HttpServletResponse response) throws IOException {
	 * log.info("inside of request helper...searching user by param...");
	 * BufferedReader reader = request.getReader(); StringBuilder s = new
	 * StringBuilder();
	 * 
	 * // we are just transferring our Reader data to our StringBuilder, line by
	 * line String line = reader.readLine(); while (line != null) { s.append(line);
	 * line = reader.readLine(); }
	 * 
	 * String body = s.toString(); String [] sepByAmp = body.split("&");
	 * 
	 * List<String> values = new ArrayList<String>();
	 * 
	 * for (String pair : sepByAmp) { // each element in array looks like this
	 * values.add(pair.substring(pair.indexOf("=") + 1)); // trim each String
	 * element in the array to just value -> [bob, pass] }
	 * log.info("User attempted to register with information:\n " + body);
	 * 
	 * // determine what type of search is needed
	 * 
	 * //1. set the content type to return text to the browser
	 * response.setContentType("application/json");
	 * 
	 * // 2. Get user in the Database by id int user_id =
	 * Integer.parseInt(values.get(0)); User user = userv.findUserById(user_id);
	 * 
	 * // 3. Turn the list of Java Objects into a JSON string (using Jackson
	 * Databind Object Mapper). String json = om.writeValueAsString(user);
	 * 
	 * // 4. Use a Print Writer to write the objects to the response body seen in
	 * the browser PrintWriter out = response.getWriter(); out.println(json);
	 * 
	 * 
	 * }
	 */	

	
	/*
	 * public static void processUserUpdate(HttpServletRequest request,
	 * HttpServletResponse response) throws IOException {
	 * log.info("inside of request helper...processUserUpdate..."); BufferedReader
	 * reader = request.getReader(); StringBuilder s = new StringBuilder();
	 * 
	 * // we are just transferring our Reader data to our StringBuilder, line by
	 * line String line = reader.readLine(); while (line != null) { s.append(line);
	 * line = reader.readLine(); }
	 * 
	 * String body = s.toString(); String [] sepByAmp = body.split("&"); // separate
	 * username=bob&password=pass -> [username=bob, password=pass]
	 * 
	 * List<String> values = new ArrayList<String>();
	 * 
	 * for (String pair : sepByAmp) { // each element in array looks like this
	 * values.add(pair.substring(pair.indexOf("=") + 1)); // trim each String
	 * element in the array to just value -> [bob, pass] }
	 * log.info("User attempted to register with information:\n " + body); //
	 * capture the actual username and password values int user_id =
	 * Integer.parseInt(values.get(0)); //id numbers cannot be modifed! String email
	 * = values.get(1); // bob String pwd = values.get(2); // pass String fname =
	 * values.get(3); String lname = values.get(4); int user_role_id =
	 * Integer.parseInt(values.get(5)); String roleName = values.get(6); UserRole
	 * role = new UserRole(roleId, roleName);
	 * 
	 * User tempUser = new User(); tempUser.setUser_id(user_id);
	 * tempUser.setEmail(email); tempUser.setPwd(pwd); tempUser.setFname(fname);
	 * tempUser.setLname(lname); tempUser.setUser_role_id(user_role_id); boolean
	 * isUpdated = userv.editUser(tempUser);
	 * 
	 * if (isUpdated) { PrintWriter pw = response.getWriter();
	 * log.info("Edit successful! New user info: " + tempUser); String json =
	 * om.writeValueAsString(tempUser); pw.println(json);
	 * System.out.println("JSON:\n" + json);
	 * 
	 * response.setContentType("application/json"); response.setStatus(200); //
	 * SUCCESSFUL! log.info("User has successfully been edited."); } else {
	 * response.setContentType("application/json"); response.setStatus(400); // this
	 * means that the connection was successful but no user was updated! }
	 * log.info("leaving request helper now..."); }
	 */
	 
	
	
	
	
	
	
	
	
	
	/*
	 * public static UserJwtDTO authenicateUser(HttpServletRequest request) { //some
	 * functionalities should only allow managers (like view all users or deleting
	 * users)! //for that reason, you need to check the user's jwt token first
	 * log.info("Request data:"); Enumeration<String> headerNames =
	 * request.getHeaderNames(); Map<String, String> map = new HashMap<String,
	 * String>();
	 * 
	 * while (headerNames.hasMoreElements()) { String key = (String)
	 * headerNames.nextElement(); String value = request.getHeader(key);
	 * map.put(key, value); } log.info(map);
	 * 
	 * String headerValue = request.getHeader("authorization"); String jwt =
	 * headerValue.split(" ")[1]; //Bearer token
	 * 
	 * UserJwtDTO dto = new UserJwtDTO();
	 * 
	 * try { dto = jwtService.parseJwt(jwt); } catch (JsonProcessingException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); } return
	 * dto; }
	 */
	
	
	

	/*
	 * public static void processUserDelete(HttpServletRequest request,
	 * HttpServletResponse response) throws IOException { //this functionality
	 * should only allow managers to delete users! UserJwtDTO dto =
	 * authenicateUser(request);
	 * 
	 * if(dto != null && dto.getRole().getRoleName().equals("manager")) { //then
	 * allow our normal deletion action here
	 * log.info("inside of request helper...processUserDelete..."); BufferedReader
	 * reader = request.getReader(); StringBuilder s = new StringBuilder();
	 * 
	 * // we are just transferring our Reader data to our StringBuilder, line by
	 * line String line = reader.readLine(); while (line != null) { s.append(line);
	 * line = reader.readLine(); }
	 * 
	 * String body = s.toString(); String [] sepByAmp = body.split("&"); // separate
	 * username=bob&password=pass -> [username=bob, password=pass]
	 * 
	 * List<String> values = new ArrayList<String>();
	 * 
	 * for (String pair : sepByAmp) { // each element in array looks like this
	 * values.add(pair.substring(pair.indexOf("=") + 1)); // trim each String
	 * element in the array to just value -> [bob, pass] }
	 * log.info("Manager attempted to delete user with information:\n " + body); //
	 * capture the actual username and password values int id =
	 * Integer.parseInt(values.get(0)); //id numbers cannot be modifed! String
	 * username = values.get(1); // bob String password = values.get(2); // pass
	 * String firstname = values.get(3); String lastname = values.get(4); int roleId
	 * = Integer.parseInt(values.get(5)); String roleName = values.get(6); UserRole
	 * role = new UserRole(roleId, roleName);
	 * 
	 * User tempUser = new User(); tempUser.setId(id);
	 * tempUser.setUsername(username); tempUser.setPassword(password);
	 * tempUser.setFirstName(firstname); tempUser.setLastName(lastname);
	 * tempUser.setRole(role); boolean isDeleted = userv.deleteUser(tempUser);
	 * 
	 * if (isDeleted) { PrintWriter pw = response.getWriter();
	 * log.info("Edit successful! New user info: " + tempUser); String json =
	 * om.writeValueAsString(tempUser); pw.println(json);
	 * System.out.println("JSON:\n" + json);
	 * 
	 * response.setContentType("application/json"); response.setStatus(200); //
	 * SUCCESSFUL! log.info("User has successfully been edited."); } else {
	 * response.setContentType("application/json"); response.setStatus(400); // this
	 * means that the connection was successful but no user was updated! } }else {
	 * log.info("User is unauthorized to perform this operation.");
	 * response.setContentType("application/json"); response.setStatus(401);
	 * //unauthorized }
	 * 
	 * log.info("leaving request helper now..."); }
	 */

}
