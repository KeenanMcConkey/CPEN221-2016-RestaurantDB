package ca.ece.ubc.cpen221.mp5.server;

import java.io.IOException;

/**
 * RestaurantClientMain is the application used to send some test requests to a
 * RestaurantServer for testing purposes.
 */
public class RestaurantClientMain {

	// Not an ADT: No Rep Invariants
	
	/**
	 * Run the client and sends some test requests to the server.
	 * 
	 * @param args none needed
	 */
	public static void main(String[] args) {
		try { // Try a RANDOMREVIEW request
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.sendRequest("RANDOMREVIEW Cafe 3");
			System.out.print("RANDOMREVIEW Cafe 3 = ");
			String reply = client.getReply();
			System.out.println(reply);
			client.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		try { // Try a GETRESTAURANT request
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.sendRequest("GETRESTAURANT gclB3ED6uk6viWlolSb_uA");
			System.out.print("GETRESTAURANT gclB3ED6uk6viWlolSb_uA = ");
			String reply = client.getReply();
			System.out.println(reply);
			client.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		try { // Try a ADDUSER request
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.sendRequest("ADDUSER Keenan M.");
			System.out.print("ADDUSER Keenan M. = ");
			String reply = client.getReply();
			System.out.println(reply);
			client.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		try { // Try a ADDRESTAURANT request
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.sendRequest("ADDRESTAURANT TestRest 20 20");
			System.out.print("ADDRESTAURANT TestRest 20 20 = ");
			String reply = client.getReply();
			System.out.println(reply);
			client.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		try { // Try a ADDREVIEW request
			RestaurantClient client = new RestaurantClient("localhost", 4949);
			client.sendRequest("ADDREVIEW TestReview TestRest Keenan M.");
			System.out.print("ADDREVIEW TestReview TestRest Keenan M. = ");
			String reply = client.getReply();
			System.out.println(reply);
			client.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
