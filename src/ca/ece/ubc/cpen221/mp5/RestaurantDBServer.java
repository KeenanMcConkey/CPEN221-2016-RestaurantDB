package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;

import ca.ece.ubc.cpen221.mp5.server.RestaurantServer;

/**
 * RestaurantDBServer is the application used to run a RestaurantServer. This
 * application creates a RestaurantServer in memory and runs it until the 
 * program is terminated.
 */
public class RestaurantDBServer {
	
	// Not an ADT: No Rep Invariants
	
	private static RestaurantDB internal_db;
	private static int portNum;
	
	private static final String RSTS_DEF = "data/restaurants.json";
	private static final String RVWS_DEF = "data/reviews.json";
	private static final String USRS_DEF = "data/users.json";
	
	/**
	 * Run the server until the program is terminated
	 * 
	 * @param args command line arguments which can be:
	 * 		1. 0 arguments, which starts the server with default data files 
	 * 			and port number
	 * 		2. 1 argument, which starts the server at the port number for
	 * 			the given argument but still uses default data files
	 * 		3. 4 arguments, which starts the server at the given port number
	 * 			using the given data files in the order:
	 * 				[restaurant_data, reviews_data, users_data]
	 * 
	 * @throws IllegalArgumentException if the command line arguments do not 
	 * 		   fit the above format
	 */
	public static void main(String[] args) throws IllegalArgumentException {
		if (args.length == 0) {
			portNum = 4949; // The default files and portNumber
			internal_db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		} else {
			portNum = Integer.parseInt(args[0]);
			if (args.length == 1) { // Set a port number
				internal_db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
			} else if (args.length == 4){ // Set a port number and data files
				internal_db = new RestaurantDB(args[1], args[2], args[3]);
			} else {
				throw new IllegalArgumentException();
			}
		}
		try { // Start the server
			RestaurantServer server = new RestaurantServer(portNum, internal_db);
			server.serve(); // Server will continue running until this program is terminated
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
