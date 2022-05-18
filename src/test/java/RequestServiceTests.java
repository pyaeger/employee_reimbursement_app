import junit.framework.TestCase;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import org.mockito.Mockito;


import java.io.IOException;

import com.revature.dao.RequestsDAOImpl;
import com.revature.models.Request;
import com.revature.services.RequestsService;
import com.revature.services.RequestsServiceImpl;

public class RequestServiceTests extends TestCase {

    private RequestsDAOImpl mockdao;
    private RequestsService rserv;

    List<Request> dummyDb;

    

    @Before
    public void setUp() throws IOException {

        mockdao = Mockito.mock(RequestsDAOImpl.class);

        rserv = new RequestsServiceImpl(mockdao);

        // BufferedImage trainReceipt = ImageIO.read(new File("trainreceipt.jpg"));
        // BufferedImage dinnerReceipt = ImageIO.read(new File("dinnerreceipt.jpg"));

        // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // ImageIO.write(trainReceipt, "jpg", outputStream);
        // byte[] train = outputStream.toByteArray();

        // ImageIO.write(dinnerReceipt, "jpg", outputStream);
        // byte[] dinner = outputStream.toByteArray();

        



        Request r1 = new Request(1, 48.23, "train ticket to client", 4, 5, 2, 4);
        Request r2 = new Request(2, 89.03, "dinner with client", 3, 5, 1, 4);
        // Request r3 = new Request(3, 102.45, "hotel stay for conference", hotel, 4, 1, 2, 3);

        List<Request> dummyDb = new ArrayList<>();
        dummyDb.add(r1);
        dummyDb.add(r2);
        // dummyDb.add(r3);

;
    }

    @Test
    public void test_submitNewRequest() throws IOException{
        // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // BufferedImage hotelReceipt = ImageIO.read(new File("hotelreceipt.jpg"));
        // ImageIO.write(hotelReceipt, "jpg", outputStream);
        // byte[] hotel = outputStream.toByteArray();

        Request r3 = new Request(3, 102.45, "hotel stay for conference", 4, 2, 2, 3);

        when(mockdao.submit(r3)).thenReturn(1);

        assertEquals(1, rserv.submit(r3));
    }
    @Test
	public void test_submitNewRequestNull() throws IOException{
		Request r5 = new Request();
		assertEquals(0, rserv.submit(r5));
	}

    @Test
	public void test_getAllRequ(){
		
		when(mockdao.findAllRequ()).thenReturn(dummyDb);

		assertEquals(dummyDb, rserv.findAllRequ());

	}

    @Test
	public void test_getRequestByResolver(){


        Request r1 = new Request(1, 48.23, "train ticket to client", 4, 0, 2, 4);
        Request r2 = new Request(2, 89.03, "dinner with client", 3, 0, 1, 4);
        // Request r3 = new Request(3, 102.45, "hotel stay for conference", hotel, 4, 1, 2, 3);

        List<Request> testDb = new ArrayList<>();
        testDb.add(r1);
        testDb.add(r2);
        int r1Resolver = 0;
        // dummyDb.add(r3);

        when(mockdao.findAllRequByResolver(5)).thenReturn(testDb);

        assertEquals(r1Resolver, r1.getResolver());


        
	}

    // @Test
	// public void test_getRequestByAuthor() throws NullPointerException{

	// 	when(mockdao.findAllRequ()).thenReturn(dummyDb);

    //     List<Request> reqList = new ArrayList<>();
    //     reqList.add(dummyDb.get(1));

        

	// 	assertNull(reqList);

	// }
    @Test
	public void test_deleteUserByIDsuccessReturnNull(){

		when(mockdao.findAllRequ()).thenReturn(dummyDb);

		rserv.deleteRequById(2);

		assertNull(rserv.findRequById(2));

	}
}