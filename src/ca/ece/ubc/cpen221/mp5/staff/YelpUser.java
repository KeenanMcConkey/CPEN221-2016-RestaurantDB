package ca.ece.ubc.cpen221.mp5.staff;

import java.util.ArrayList;
import java.util.HashMap;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

/**
 * The User class represents a Yelp user.
 * 
 * Used solely for storing data from JSON datasets.
 */
public class YelpUser implements YelpData {
	

	// Representation Invariant:
	//  1. All internal values have been instantiated. Note that we tried
	//     having it so these values were always set by caller upon construction
	//     but this proved difficult.
		
	// Abstraction Function:
	// [ All internal values] -> [A user]
	
	private String url;
	private HashMap<String, Long> votes;
	private long review_count;
	private String type;
	private String user_id;
	private String name;
	private double average_stars;

	/**
	 * Constructor for a User.
	 */
	public YelpUser() {
		this.url = "";
		this.votes = new HashMap<String, Long>();
		this.review_count = 0;
		this.type = "user";
		this.user_id = "";
		this.name = "";
		this.average_stars = 0;
	}

	/**
	 * Returns the url for the user as a string
	 * 
	 * @return the url for the user
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url for the user as a string
	 * 
	 * @param url
	 *            the url for the user to be set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the votes of a user as a map from a string (what the votes was
	 * i.e. "funny") to the number of votes that this string got.
	 * 
	 * @return the votes for a User
	 */
	public HashMap<String, Long> getVotes() {
		return votes;
	}

	/**
	 * Sets the votes for a user using a map from a string (what the votes was
	 * i.e. "funny") to the number of votes that this string got. Requires that
	 * the input not be null.
	 * 
	 * @param votes
	 *            the votes to be set to the User.
	 */
	public void setVotes(HashMap<String, Long> votes) {
		this.votes = votes;
	}

	/**
	 * Returns the amount of reviews done by the user
	 * 
	 * @return the number of reviews made my the user
	 */
	public long getReview_count() {
		return review_count;
	}

	/**
	 * Sets the number of reviews done by the user
	 * 
	 * @param review_count
	 *            the number of reviews to be set as the review count
	 */
	public void setReview_count(long review_count) {
		this.review_count = review_count;
	}

	/**
	 * Returns the type of user.
	 * 
	 * @return the type of user as a string
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of user.
	 * 
	 * @param type
	 *            the type of user to be set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the user's ID as a string.
	 * 
	 * @return the users id
	 */
	public String getID() {
		return user_id;
	}

	/**
	 * Sets the users ID as string.
	 *
	 * @param user_id
	 *            the id to be set to the users id
	 */
	public void setID(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * Returns the users name.
	 * 
	 * @return the users name as a string.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the users name as string.
	 * 
	 * @param name
	 *            the name to be set as the users name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the average stars for the user.
	 * 
	 * @return the average stars for the user.
	 */
	public double getAverage_stars() {
		return average_stars;
	}

	/**
	 * Sets the average stars for the user.
	 * 
	 * @param average_stars
	 *            the average stars to be set for the user
	 */
	public void setAverage_stars(double average_stars) {
		this.average_stars = average_stars;
	}

	@Override
	public String toString() {
		return "{"
				+ "\"url\": " + getUrl() + ", "
				+ "\"votes\": " + getVotes().toString() + ", "
				+ "\"review_count\": " + getReview_count() + ", "
				+ "\"type\": " + getType() + ", "
				+ "\"user_id\": " + getID() + ", "
				+ "\"name\": " + getName() + ", "
				+ "\"average_stars\": " + Double.toString(getAverage_stars())
				+ "}";
	}
}
