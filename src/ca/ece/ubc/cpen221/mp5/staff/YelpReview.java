package ca.ece.ubc.cpen221.mp5.staff;

import java.util.ArrayList;
import java.util.HashMap;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

/* YelpReview is an implementation of YelpData and it represents
 * a data type containing all of the fields for the JSON object
 * of a review.
 * 
 * Used solely for storing data from JSON datasets.
 */
public class YelpReview implements YelpData {
	
	// Representation Invariant:
	//  1. All internal values have been instantiated. Note that we tried
	//     having it so these values were always set by caller upon construction
	//     but this proved difficult.
	
	// Abstraction Function:
	// [ All internal values] -> [A review]
	
	private String type;
	private String business_id;
	private HashMap<String, Long> votes;
	private String review_id;
	private String text;
	private long stars;
	private String user_id;
	private String date;

	/**
	 * Constructor for a Review.
	 */
	public YelpReview() {
		this.type = "review";
		this.business_id = "";
		this.votes = new HashMap<String, Long>();
		this.review_id = "";
		this.text = "";
		this.stars = 0;
		this.user_id = "";
		this.date = "";
	}

	/**
	 * Returns the Review type as string.
	 * 
	 * @return the review type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the Review type as s string.
	 * 
	 * @param type
	 *            the type to be set for the review.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the business id for the review as s string.
	 * 
	 * @return the business id
	 */
	public String getBusiness_id() {
		return business_id;
	}

	/**
	 * Sets the business id for the review.
	 * 
	 * @param business_id
	 *            the business id to be set for the review
	 */
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	/**
	 * Returns the votes of a review as a map from a string (what the votes was
	 * i.e. "funny") to the number of votes that this string got.
	 * 
	 * @return the votes for a review.
	 */
	public HashMap<String, Long> getVotes() {
		return votes;
	}

	/**
	 * Sets the votes for a review using a map from a string (what the votes was
	 * i.e. "funny") to the number of votes that this string got. Requires that
	 * the input not be null.
	 * 
	 * @param votes
	 *            the votes to be set to the review.
	 */
	public void setVotes(HashMap<String, Long> votes) {
		this.votes = votes;
	}

	/**
	 * Returns the review id of the review as string.
	 * 
	 * @return the review id of the review
	 */
	public String getID() {
		return review_id;
	}

	/**
	 * Sets the review id of the review
	 * 
	 * @param review_id
	 *            the review id to be set for the review.
	 */
	public void setID(String review_id) {
		this.review_id = review_id;
	}

	/**
	 * Returns the text for the review as string.
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text for the review.
	 * 
	 * @param text
	 *            the text to be set for the review.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the stars for the review as a long
	 * 
	 * @return the stars for the review
	 */
	public long getStars() {
		return stars;
	}

	/**
	 * Sets the stars for the review as a long.
	 * 
	 * @param stars
	 *            the stars to be set for the review.
	 */
	public void setStars(long stars) {
		this.stars = stars;
	}

	/**
	 * Returns the user id of the review as a string.
	 * 
	 * @return the user id of the review.
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * Sets the user id for the review as a string.
	 * 
	 * @param user_id
	 *            the id to be set as the user id.
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * Returns the date of the review as a string.
	 * 
	 * @return the date of the review.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date of the review as a string.
	 * 
	 * @param date
	 *            the date of the review to be set.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "{"
				+ "\"type\": " + getType() + ", "
				+ "\"business_id\": " + getBusiness_id() + ", "
				+ "\"votes\": " + getVotes().toString() + ", "
				+ "\"review_id\": " + getID() + ", "
				+ "\"text\": " + getText() + ", "
				+ "\"stars\": " + Long.toString(getStars()) + ", "
				+ "\"user_id\": " + getUser_id() + ", "
				+ "\"date\": " + getDate()
				+ "}";
	}
}
