package ca.ece.ubc.cpen221.mp5.staff;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

/**
 * RestaurantData processors are used for processing data found in the 
 * restaurant database for k means and least square regression functions.
 */
public interface RestaurantProcessor {
	
	/**
	 * Compares this RestaurantData to the given RestaurantData d that is
	 * inputed into the function.
	 * 
	 * @param db
	 *            the database that both the RestaurantData d and this
	 *            RestaurantData are a part of
	 * @param d
	 *            the RestaurantData that you are comparing to
	 * @return a double value representing how similar the two RestaurantData's
	 *         are to each other
	 * @throws IllegalArgumentException
	 *             if (this) database is not the same as the data base of (d)
	 */
	public double compare(RestaurantDB db,RestaurantProcessor d);
	/** 
	 * Return the longitude of this restaurant.
	 * 
	 * @return the longitude value of a specific restaurant
	 */
	public double getLongitude();
	
	/** 
	 * Return the latitude of this restaurant.
	 * 
	 * @return the latitude value of this restaurant
	 */
	public double getLatitude();
	
	/**
	 * Return the mean rating for this restaurant in a inputed restaurant
	 * database. Requires that the input not be null.
	 * 
	 * @param db
	 *            the restaurant database
	 * @return the mean rating value of this restaurant
	 */
	public double getMeanRating(RestaurantDB db);
	
	/** 
	 * Return the price scale for this restaurant.
	 * 
	 * @return the price scale value of this restaurant
	 */
	public double getPrice();
	
}
