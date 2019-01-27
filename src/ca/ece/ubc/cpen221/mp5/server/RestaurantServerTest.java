package ca.ece.ubc.cpen221.mp5.server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

/**
 * Test class for RestaurantServer. Example tests for the actual queries are
 * shown in the RestaurantClientMain application.
 */
public class RestaurantServerTest {

	private static final String RSTS_DEF = "data/restaurants.json";
	private static final String RVWS_DEF = "data/reviews.json";
	private static final String USRS_DEF = "data/users.json";
	
	/**
	 * Test creating a restaurant server
	 */
	@Test
	public void testCreateRestaurantServer() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		try {
			RestaurantServer server = new RestaurantServer(4949, db);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception should not be thrown");
		}
	}
	
	/**
	 * Test running a restaurant server
	 */
	@Test
	public void testRunRestaurantServer() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		try {
			RestaurantServer server = new RestaurantServer(4141, db);
			server.serve(); // Warning: Wont stop running!
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception should not be thrown");
		}
	}
	
	/**
	 * Test getting a server port
	 */
	@Test
	public void testGetServerPort() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		try {
			RestaurantServer server = new RestaurantServer(4949, db);
			assertEquals(server.getPort(), 4949);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception should not be thrown");
		}
	}
	
	/**
	 * Test handling a request
	 * 
	 * *Note*: More detailed testing of requests can be found in ClientServerMain
	 */
	@Test
	public void testHandleRequest() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		try {
			RestaurantServer server = new RestaurantServer(4949, db);
			assertEquals(server.handleRequest("BAD_REQUEST"), "ERR: UNKNOWN_REQUEST");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception should not be thrown");
		}
	}
	
}
