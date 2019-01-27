package ca.ece.ubc.cpen221.mp5.server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

/**
 * Test cases for the RestaurantClient. Testing for the actual requests is 
 * done in main method.
 */
public class RestaurantClientTest {

	/**
	 * Test creating a new client
	 */
	@Test
	public void testCreateClient() {
		try { // Server must be running already!
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Unexpected exception thrown!");
		}
	}
	
	/**
	 * Test sending and receiving request
	 */
	@Test
	public void testSendReceiveRequest() {
		try { // Server must be running already!
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.sendRequest("bad request");
			assertEquals(client.getReply(), "ERR: UNKNOWN_REQUEST");
			client.close(); // Tests for requests done in the main method
		} catch (IOException e) {
			e.printStackTrace();
			fail("Unexpected exception thrown!");
		}
	}
	
	/**
	 * Test sending a null request
	 */
	@Test
	public void testTryNullRequest() {
		try {
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.sendRequest(null);
			fail("Should have thrown an error");
		} catch (IllegalArgumentException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
			fail("Unexpected exception thrown!");
		}
	}
	
	/**
	 * Test starting and closing a client
	 */
	@Test
	public void testCloseClient() {
		try { // Server must be running already!
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Unexpected exception thrown!");
		}
	}
	
	/**
	 * Test retrieving the port the client is connected to
	 */
	@Test
	public void testGetRemotePort() {
		try { // Server must be running already!
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			assertEquals(client.getRemotePort(), 4949);
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Unexpected exception thrown!");
		}
	}
	
	/**
	 * Test getting the local port
	 */
	@Test
	public void testGetLocalPort() {
		try { // Server must be running already!
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.getLocalPort(); // Can't assert because this is randomly generated
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Unexpected exception thrown!");
		}
	}
}
