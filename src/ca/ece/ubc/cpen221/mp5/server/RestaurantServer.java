package ca.ece.ubc.cpen221.mp5.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.queries.QueryProcessor;
import ca.ece.ubc.cpen221.mp5.staff.YelpRestaurant;
import ca.ece.ubc.cpen221.mp5.staff.YelpReview;
import ca.ece.ubc.cpen221.mp5.staff.YelpUser;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.*;

/**
 * The RestaurantServer is the server class used by RestaurantDBServer. The 
 * RestaurantServer takes listens to a given port for client connections. When 
 * when a client connects, the server receives requests from the client and
 * sends back replies based on the query type.
 */
public class RestaurantServer implements YelpServer {
	
	// Internal Representation:
	// 	A RestaurantServer is represented by an integer server port, a internal
	//  RestaurantDB, and a server socket for receiving client requests.
	
	// Representation Invariant:
	//	1. server_db != null
	//  2. server_socket != null
	//  3. rwl != null
	
	// Abstraction Function:
	// [A RestaurantDB, Socket, and port number] -> [A server for the yelp database]
	
	// Thread Safety Argument:
	//  Thread safety is achieved through Reader-Writer locks. When a read-only
	//  request is sent to the client, the server only locks writes using a 
	//  readLoc so that multiple clients can read server data at once. If a
	//  client sends a request that will modify the servers data, the server
	//  creates a writeLock to ensure no other clients can access data while
	//  the request takes place.
	
	private int server_port;
	private RestaurantDB server_db;
	private ServerSocket server_socket;
	private Random RAND;
	private final ReentrantReadWriteLock rwl; 
	
	/**
	 * Constructor for a RestaurantServer. Creates a server at the given port
	 * with a given RestaurantDB.
	 * 
	 * @param port the port number for this server
	 * @param db the restaurant database to be used for this server
	 * @throws IOException if an error occurs in creating a server at the socket
	 */
	public RestaurantServer(int port, RestaurantDB db) throws IOException{
		System.out.print("Initializing server...");
		server_port = port;
		server_socket = new ServerSocket(port);
		server_db = db;
		RAND = new Random(3000); // Use the same seed each time
		rwl = new ReentrantReadWriteLock();
		System.out.println(" Done");
	}
	
	@Override
	public void serve() throws IOException {
		System.out.println("Server is running...");
		while(true) {
			// Check for a client connection request. This blocks until a 
			// connection request is received
			Socket socket = server_socket.accept();
			Thread handler = new Thread(new Runnable() {
				public void run() {
					try {
						try {
							handle(socket);
						} finally {
							socket.close();
						}
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			});
			handler.start();
		}
	}
	
	@Override
	public int getPort() {
		return server_port;
	}
	
	/**
	 * Handle the request sent to a given socket. This receives the data 
	 * at the given socket and passes this data to the appropriate method to
	 * generate a reply for the client, then sends the client that reply.
	 * 
	 * @param socket the socket to receive data from 
	 * @throws IOException if an error occurs in receiving data from the socket
	 */
	private void handle(Socket socket) throws IOException {
		System.err.println("Client is connected");
		InputStream inStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream(); // in & out only exist while there is a connection
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream), true);
		
		try { // Process the data line-by-line
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				System.err.println("New Request: "+line);
				String ans = handleRequest(line); // 
				System.err.println("Reply: "+ans);
				out.println(ans); // Send the reply
				out.flush();
			}
		} finally {
			out.close();
			in.close();
		}
	}
	
	/**
	 * Process a request given a line of text. This sends the given data to
	 * the appropriate method for processing by reading reading the request type
	 * sent in the given line, and returns the answer sent back by a processor
	 * method. If a request type cannot be found, this method sends back an
	 * error message.
	 * 
	 * @param line request line sent from client
	 * @return answer to the given request line, or an error message if a
	 * 			request cannot be discerned.
	 */
	public String handleRequest(String line) {
		String arg = null;
		if (line.contains(" ")) {
			 arg = line.substring(line.indexOf(" ") + 1, line.length());
		} // Requests are supposed to have spaces, this stops out-of-bounds exceptions
		else {
			return "ERR: UNKNOWN_REQUEST";
		} // Send to the right processor method
		if (line.startsWith("RANDOMREVIEW")) {
			return getRandomReview(arg);
		} else if (line.startsWith("GETRESTAURANT")) {
			return getRestaurant(arg);
		} else if (line.startsWith("ADDUSER")) {
			return addUser(arg);
		} else if (line.startsWith("ADDRESTAURANT")) {
			return addRestaurant(arg);
		} else if (line.startsWith("ADDREVIEW")) {
			return addReview(arg);
		} else if (line.startsWith("QUERY")) {
			return QueryProcessor.parseQuery(arg);
		}
		else return "ERR: UNKNOWN_REQUEST";
	}
	
	/**
	 * This processor method gets a random review given in JSON formatting
	 * style given an argument line restaurant. This method allows other readers
	 * to send read requests at the same time.
	 * 
	 * @param arg request argument passed by the request handler
	 * @return random review for the restaurant found in the given argument
	 * 			line, or error messages if the restaurant cannot be found,
	 * 			the restaurant has no reviews, or any other errors occur
	 */
	private String getRandomReview(String arg) {
		rwl.readLock().lock(); // Lock for reading
		HashMap<YelpRestaurant, HashSet<YelpReview>> restaurants = server_db.getRestaurantReviews();
		boolean contains = false;
		HashSet<YelpReview> reviews = null;
		// Check if the restaurant exists
		for (Map.Entry<YelpRestaurant, HashSet<YelpReview>> entry : restaurants.entrySet()) {
			if (entry.getKey().getName().equals(arg)) {
				contains = true;
				reviews = entry.getValue();
			}
		}
		if (! contains) { // Should have found a restaurant!
			rwl.readLock().unlock();
			return "ERR: NO_RESTAURANT_FOUND";
		} else if (reviews == null) {
			rwl.readLock().unlock(); // Should not occur! (caused a bug before)
			return "ERR: UNKNOWN_ERROR";
		} else {
			Iterator<YelpReview> reviewsItr = reviews.iterator();
			YelpReview last = null;
			// Check all the reviews, stop checking at a random time
			for (int i = 0; i < RAND.nextInt(reviews.size()); i++) {
				if (reviewsItr.hasNext()) {
					last = reviewsItr.next();
				}
			} if (last == null) { // Couldn't find one
				rwl.readLock().unlock();
				return "ERR: NO_REVIEWS_FOR_RESTAURANT";
			} else {
				rwl.readLock().unlock();
				return last.toString();
			}
		}
	}
	
	/**
	 * This processor method returns the restaurant in JSON formatting style 
	 * for the given argument line restaurant. This method allows other readers
	 * to send read requests at the same time.
	 * 
	 *  @param arg request argument passed by the request handler
	 *  @return restaurant in JSON formatting for the given restaurant, or
	 *  		an error message if the restaurant cannot be found
	 */
	private String getRestaurant(String arg) {
		rwl.readLock().lock(); // Lock for reading
		HashMap<String, YelpRestaurant> reviews = server_db.getRestaurantMap();
		if (reviews.containsKey(arg)) {
			rwl.readLock().unlock();
			return reviews.get(arg).toString(); // Just return the restaurant
		} else {
			rwl.readLock().unlock();
			return "ERR: NO_RESTAURANT_FOUND";  // Couldn't find a restaurant
		}
	}
	
	/**
	 * This processor method adds a new user to the restaurant database given
	 * an argument line for a new user. This method blocks other clients
	 * from accessing data while it adds the new user. The format for
	 * a new user is:
	 * 
	 * 	<code> NEWUSER <_NAME> </code>
	 * 
	 * @param arg request argument passed by the request handler
	 * @return the JSON formatted new user data if the new user is successfully
	 * 			added to the restaurant database, else the appropriate error 
	 * 			message
	 */
	private String addUser(String arg) {
		rwl.writeLock().lock(); // Lock for writing
		if (arg.length() > 0) {
			try {
				boolean added = server_db.addUser(arg);
				rwl.writeLock().unlock();
				return server_db.getUserMap().get(arg).toString();
			} catch (StringIndexOutOfBoundsException e) {
				rwl.writeLock().unlock();
				return "ERR: INVALID_USER_STRING"; // Wasn't formatted correctly
			}									   // (incorrect spacing)
		} else {
			rwl.writeLock().unlock();
			return "ERR: INVALID_USER_STRING";
		}
	}
	
	/**
	 * This processor method adds a new restaurant to the restaurant database given
	 * an argument line for a new restaurant. This method blocks other clients
	 * from accessing data while it adds the new restaurant. The format for
	 * a new restaurant is:
	 * 
	 * 	<code> NEWRESTAURANT <_NAME_> <_LONGITUDE_> <_LATITUDE> </code>
	 * 
	 * @param arg request argument passed by the request handler
	 * @return the JSON formatted new restaurant data if the new restaurant is successfully
	 * 			added to the restaurant database, else the appropriate error 
	 * 			message
	 */
	private String addRestaurant(String arg) {
		rwl.writeLock().lock();
		if (arg.length() > 0) {
			try {
				String name = arg.substring(0, arg.indexOf(" "));
				Double longitude = Double.parseDouble(arg.substring(arg.indexOf(" ") + 1, 
						arg.indexOf(" ", arg.indexOf(" ") + 1)));
				Double latitude = Double.parseDouble(arg.substring(arg.indexOf(" ", arg.indexOf(" ") + 1) + 1,
						arg.length()));
				for (Map.Entry<String, YelpRestaurant> entry : server_db.getRestaurantMap().entrySet()) {
					YelpRestaurant currRestaurant = entry.getValue();
					if (currRestaurant.getLongitude() == longitude && currRestaurant.getLatitude() == latitude) {
						rwl.writeLock().unlock();
						return "ERR: DUPLICATE_RESTATURANT"; // Already is a restaurant at the given location
					}
				}
				server_db.addRestaurant(name, longitude, latitude);
				rwl.writeLock().unlock();
				return server_db.getRestaurantMap().get(name).toString();
			} catch (StringIndexOutOfBoundsException e) {
				rwl.writeLock().unlock(); // Incorrectly formatted
				return "ERR: INVALID_RESTAURANT_STRING";
			}
		} else {
			rwl.writeLock().unlock();
			return "ERR: INVALID_RESTAURANT_STRING";
		}
	}
	
	/**
	 * This processor method adds a new review to the restaurant database given
	 * an argument line for a new review. This method blocks other clients
	 * from accessing data while it adds the new review. The format for
	 * a new review is:
	 * 
	 * 	<code> NEWREVIEW <_REVIEWNAME_> <_USERNAME_> <_LATITUDE> </code>
	 * 
	 * @param arg request argument passed by the request handler
	 * @return the JSON formatted new review data if the new review is successfully
	 * 			added to the restaurant database, else the appropriate error 
	 * 			message
	 */
	private String addReview(String arg) {
		rwl.writeLock().lock();
		if (arg.length() > 0) {
			try {
				String name = arg.substring(0, arg.indexOf(" "));
				String restaurant = arg.substring(arg.indexOf(" ") + 1, 
						arg.indexOf(" ", arg.indexOf(" ") + 1));
				String user = arg.substring(arg.indexOf(" ", arg.indexOf(" ") + 1) + 1,
						arg.length());
				boolean containsRestaurant = false;
				boolean containsUser = false;
				// Check all the restaurants to see if the restaurant exists
				for (Map.Entry<String, YelpRestaurant> entry : server_db.getRestaurantMap().entrySet()) {
					YelpRestaurant currRestaurant = entry.getValue();
					if (currRestaurant.getName().equals(restaurant)) {
						containsRestaurant = true;
					}
				}
				// Check all the users to see if the users exists
				for (Map.Entry<String, YelpUser> entry : server_db.getUserMap().entrySet()) {
					YelpUser currUser = entry.getValue();
					if (currUser.getName().equals(user)) {
						containsUser = true;
					}
				} // Error Messages:
				if (containsRestaurant && containsUser) {
					server_db.addReview(name, user, restaurant);
					rwl.writeLock().unlock();
					return server_db.getReviewMap().get(name).toString();
				} else if (! containsRestaurant) {
					rwl.writeLock().unlock();
					return "ERR: NO_SUCH_RESTAURANT";
				} else if (! containsUser) {
					rwl.writeLock().unlock();
					return "ERR: NO_SUCH_USER";
				} else {
					rwl.writeLock().unlock();
					return "ERR: INAVALID_REVIEW_STRING";
				}
			} catch (StringIndexOutOfBoundsException e) {
				rwl.writeLock().unlock();
				return "ERR: INVALID_REVIEW_STRING";
			}
		} else {
			rwl.writeLock().unlock();
			return "ERR: INVALID_REVIEW_STRING";
			
		}
	}
	
}
