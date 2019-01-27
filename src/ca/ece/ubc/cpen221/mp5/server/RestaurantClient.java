package ca.ece.ubc.cpen221.mp5.server;

import java.io.*;
import java.net.Socket;

/**
 * The RestauantClient class represents a client that can connect to a
 * RestaurantServer and send it requests. 
 */
public class RestaurantClient implements YelpClient {
	
	// Internal Representation:
	// 	1. A Socket that composes the socket connection to the server
	//  2. A BufferedReader that represents the input from the socket
	//  3. A PrintWriter that represents the output to the socket
	
	// Representation Invariant:
	//  1. socket != null
	//  2. in != null
	//  3. out != null
	//  4. socket connection exists
	
	// Abstraction Function:
	// [A Socket, BufferedReader, and PrintWriter] -> [A client of the yelp server]
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	/**
	 * Constructor for a RestaurantClient. Creates a new client and connects
	 * it to a server with given host name at a given port.
	 * 
	 * @param hostname name of the host to connect to
	 * @param port port number on the host to connect to
	 * @throws IOException if a connection cannot be made to the given server
	 */
	public RestaurantClient(String hostname, int port) throws IOException {
		socket = new Socket(hostname, port); // Throws an exception if cannot connect to socket
		in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Can also throw exceptions
		out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); // (but shouldn't)
	}
	
	@Override
	public void sendRequest(String requestStr) throws IOException {
		if (requestStr == null) {
			throw new IllegalArgumentException();
		}
		out.print(requestStr+"\n"); // Adds the request to the buffer
		out.flush(); // Pushes the buffered request
	}
	
	@Override
	public String getReply() throws IOException {
		String reply = in.readLine();
		if (reply == null) { // The server has disconnected
			throw new IOException("ERR: Connection unexpectedly terminated");
		}
		return reply;
	}
	
	@Override
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}
	
	@Override
	public int getLocalPort() {
		return socket.getLocalPort();
	}
	
	@Override
	public int getRemotePort() {
		return socket.getPort();
	}
	
}
