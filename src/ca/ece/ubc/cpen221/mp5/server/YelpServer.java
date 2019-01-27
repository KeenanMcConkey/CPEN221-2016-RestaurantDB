package ca.ece.ubc.cpen221.mp5.server;

import java.io.IOException;

/**
 * Interface used by all server implementations of server for a  yelp 
 * database. A server receives requests from a client and handles those 
 * requests by accessing the yelp database.
 */
public interface YelpServer {

	/**
	 * Run the server and server client requests. This runs until the program 
	 * is terminated.
	 * 
	 * @throws IOException if an error occurs in running the restaurant
	 */
	public void serve() throws IOException;
	
	/**
	 * Return the port associated with this server.
	 * 
	 * @return the port number for this server
	 */
	public int getPort();
	
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
	public String handleRequest(String line);
}
