import junit.framework.TestCase;


import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import org.mockito.Mockito;

import com.revature.dao.UserDAOImpl;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

public class UserServiceTests extends TestCase {
	
	private UserDAOImpl mockdao;
	private UserService userv;

	List<User> dummyDb;
	
	@Before
	public void setUp() {
		
		
		mockdao = Mockito.mock(UserDAOImpl.class);
				
		userv = new UserServiceImpl(mockdao);
		
		User u1 = new User(1, "John", "Smith", "jsmith01@gmail.com", "jayjay", 7);
		User u2 = new User(2, "Jane", "Doe", "jdoe77@gmail.com", "jayday", 33);
		// User u3 = new User(3, "Gerard", "Departew", "gtgotit89@gmail.com", "gerger", 14);
		User u4 = new User(4, "Jane", "Dreezy", "jdreezy77@gmail.com", "jordan", 47);
		
		List<User> dummyDb = new ArrayList<User>();
		dummyDb.add(u1);
		dummyDb.add(u2);
		// dummyDb.add(u3);
		dummyDb.add(u4);
	
	}

	@Test
	public void testLogin_Success() throws NullPointerException{
		
		User testUser = new User(3, "Gerard", "Departew", "gtgotit89@gmail.com", "gerger", 14);
		User testUser2 = new User(4, "Garth", "Deeper", "gogogadget88@gmail.com", "gasbag", 11);
		List<User> testDb = new ArrayList<>();
		testDb.add(testUser);
		testDb.add(testUser2);

		when(mockdao.selectAll()).thenReturn(testDb);


		assertNotNull(userv.login("gtgotit89@gmail.com", "gerger"));


		


	}


	@Test
	public void testRegister_returnUser(){
		User u5 = new User(5, "test", "test", "test", "test", 47);

		when(mockdao.insert(u5)).thenReturn(1);

		assertEquals(1, userv.register(u5));
	}

	@Test
	public void testRegisterNullUser_returnsNullUser(){
		User u5 = new User(5, "", "", "", "", 0);
		assertEquals(0, userv.register(u5));
	}

	@Test
	public void test_getAllUsers(){
		
		when(mockdao.selectAll()).thenReturn(dummyDb);

		List<User> userList = userv.findAllUsers();

		assertEquals(dummyDb, userList);

	}




	@Test 
	public void test_locateUserByFirstName(){
		

		User testUser = new User(3, "Gerard", "Departew", "gtgotit89@gmail.com", "gerger", 14);
		User testUser2 = new User(4, "Garth", "Deeper", "gogogadget88@gmail.com", "gasbag", 11);
		List<User> testDb = new ArrayList<>();
		testDb.add(testUser);
		testDb.add(testUser2);

		when(mockdao.selectByFirstName("Gerard")).thenReturn(testUser);

		assertEquals("Gerard", testUser.getFname());

	}


	@Test
	public void test_getUserByFirstName_notSamereturn(){
		
		User testUser = new User(3, "Gerard", "Departew", "gtgotit89@gmail.com", "gerger", 14);
		User testUser2 = new User(4, "Garth", "Deeper", "gogogadget88@gmail.com", "gasbag", 11);
		List<User> testDb = new ArrayList<>();
		testDb.add(testUser);
		testDb.add(testUser2);

		when(mockdao.selectByFirstName("Kabba")).thenReturn(null);

		assertNotSame("Kabba", testUser.getFname());
		assertNotSame("Kabba", testUser2.getFname());


	}



	@Test
	public void test_deleteUserByIDsuccessReturnNull(){

		when(mockdao.selectAll()).thenReturn(dummyDb);

		userv.deleteUserById(2);

		assertNull(userv.findUserById(2));

	}



}
