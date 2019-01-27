package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;

import ca.ece.ubc.cpen221.mp5.staff.YelpRestaurant;
import ca.ece.ubc.cpen221.mp5.staff.YelpReview;
import ca.ece.ubc.cpen221.mp5.staff.YelpUser;

/**
 * Interface used by all implementations of a yelp database. A yelp database
 * represents a database used to store data from the Yelp Academic Dataset.
 */
public interface YelpDatabase {

	/**
	 * Returns a copy of the map of restaurants stored in this database
	 * 
	 * @return copied restaurant map
	 */
	public HashMap<String, YelpRestaurant> getRestaurantMap();
	
	/**
	 * Returns a copy of the map of review stored in this database
	 * 
	 * @return copied review map
	 */
	public HashMap<String, YelpReview> getReviewMap();
	

	/**
	 * Returns a copy of the map of users stored in this database
	 * 
	 * @return copied user map
	 */
	public HashMap<String, YelpUser> getUserMap();
	
	/**
	 * Add a new user to this database. Creates a new YelpUser with default
	 * initial fields and a passed user name, and adds that user to the
	 * database.
	 * 
	 * @param userName name of the user to add to the database
	 * @return true if the user is successfully added, false otherwise
	 */
	public boolean addUser(String userName);
	
	/**
	 * Adds a new restaurant to the database.Creates a new YelpRestauant with 
	 * default initial fields and a passed name, longitude, and latitude, and
	 * adds that user to the database.
	 * 
	 * @param restaurantName name of the new restaurant to add
	 * @param longitude longitudinal location of the new restaurant
	 * @param latitude latitudinal location of the new restaurant
	 * @return true if the restaurant is successfully added to the database,
	 * 			false otherwise
	 */
	public boolean addRestaurant(String restaurantName, Double longitude, Double latitude);
	
	/**
	 * Adds a new Review to the database. Creates a new YelpReview with 
	 * default initial fields and a passed name for the review, name for the 
	 * restaurant of the review, and user who wrote the review.
	 * 
	 * @param reviewName name of the review to add
	 * @param userName user the new review is associated with
	 * @param restaurantName restaurant the new review is associated with
	 * @return true if the review is successfuly added to the database,
	 * 			false otherwise
	 */
	public boolean addReview(String reviewName, String userName, String restaurantName);
	
}
