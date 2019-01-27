package ca.ece.ubc.cpen221.mp5.server;

import java.io.IOException;

/**
 * Interface used by all client implementations of a client for a yelp 
 * database. A client sends request to a server associated with a yelp
 * database.
 */
public interface YelpClient {

	/**
	 * Send a request to the server that the client was connected to when 
	 * the client was created. 
	 * Precondition: requestStr is not null
	 * 
	 * @param requestStr request to send to the server
	 * @throws IOException if an error occurs in sending the request
	 */
	public void sendRequest(String requestStr) throws IOException;
	
	/**
	 * Receive a reply from the server that the client was connected to when 
	 * the client was created (after the client has sent a request) and 
	 * return it.
	 * 
	 * @return the reply sent by the server at connection
	 * @throws IOException if an error occurs in receiving a reply
	 */
	public String getReply() throws IOException;
	
	/**
	 * Close a client connection. Terminates the connection to the server that 
	 * the client was connected to when the client was created.
	 * 
	 * @throws IOException if an error in closing the connection
	 */
	public void close() throws IOException;
	
	/**
	 * Return the local port of the client.
	 * 
	 * @return local port of the client
	 */
	public int getLocalPort();
	
	/**
	 * Return the remote port that client is connected to
	 * 
	 * @return remote port the client is connected to
	 */
	public int getRemotePort();
	
}
