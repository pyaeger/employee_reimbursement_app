import junit.framework.TestCase;

import static org.mockito.Mockito.when;

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
	private User u1, u2;
	List<User> dummyDb;
	
	@Before
	public void setUp() {
		
		
		mockdao = Mockito.mock(UserDAOImpl.class)
				
		userv = new UserServiceImpl(mockdao);
		
		u1 = new User(1, "realuser", "passw0rd", "Real", "User");
				
	
		public void testLogin_success() throws Exception {
			
		when(mockdao.selectAll())thenReturn(dummyDb);
		
		assertEquals(u2, userv.login("anotheruser", "password"));
		
		}
		
		
		
	}

}
