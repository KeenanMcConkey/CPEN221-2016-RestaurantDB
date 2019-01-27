package ca.ece.ubc.cpen221.mp5.staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.statlearning.Algorithms;

/* YelpRestaurant is an implementation of RestaurantProcessor which contains all of
 * the fields from a the JSON object of a restaurant. This representation of the restaurant
 * is meant to be used for processing in algorithms.
 */
public class YelpRestaurant implements RestaurantProcessor {
	
	// Representation Invariant:
	//  1. All internal values have been instantiated. Note that we tried
	//     having it so these values were always set by caller upon construction
	//     but this proved difficult.
	
	// Abstraction Function:
	// [ All internal values] -> [A restaurant]
	
	private double mean_rating;
	private String url;
	private double longitude;
	private ArrayList<String> neighborhoods;
	private String business_id;
	private String name;
	private ArrayList<String> categories;
	private String state;
	private String type;
	private double stars;
	private String city;
	private String full_address;
	private long review_count;
	private String photo_url;
	private ArrayList<String> schools;
	private double latitude;
	private double price;
	private boolean is_open;
	
	
	/**
	 * Construct a YelpRestaurant
	 */
	public YelpRestaurant() {
		this.mean_rating = 0;
		this.url = "";
		this.longitude = 0;
		this.neighborhoods = new ArrayList<String>();
		this.business_id = "";
		this.name = "";
		this.categories = new ArrayList<String>();
		this.state = "";
		this.type = "restaurant";
		this.stars = 0;
		this.city = "";
		this.full_address = "";
		this.review_count = 0;
		this.photo_url = "";
		this.schools = new ArrayList<String>();
		this.latitude = 0;
		this.price = 0;
		this.is_open =  true;
	}

	/** Returns the url of the restaurant as a string.
	 * 
	 * @return the url of the restaurant.
	 */
	public String getUrl() {
		return url;
	}

	/** Sets the url of the restaurant as a string.
	 * 	Requires that the input is not null.
	 * 
	 * @param url
	 * 			the url to be set for the restaurant.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public double getLongitude() {
		return longitude;
	}

	/** Sets the longitude for the restaurant as a double.
	 * 
	 * @param longitude
	 * 				the longitude to be set for the restaurant.
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/** Returns an array list of the neighborhoods as strings.
	 * 
	 * @return an the neighborhoods for the restaurant.
	 */
	public ArrayList<String> getNeighborhoods() {
		return neighborhoods;
	}

	/** Sets the neighborhoods for the restaurant with an array list
	 *  of strings. Requires that neighborhoods is not null.
	 * 
	 * @param neighborhoods
	 * 				the neighborhoods to be set for the restaurant.
	 */
	public void setNeighborhoods(ArrayList<String> neighborhoods) {
		this.neighborhoods = neighborhoods;
	}

	/** Returns the id for the restaurant as a string.
	 * 
	 * @return the business id for the restaurant
	 */
	public String getID() {
		return business_id;
	}

	/** Sets the the business id for the restaurant as string.
	 * 	Requires that the input is not null.
	 * 
	 * @param business_id
	 * 				the id to be set for the restaurant.
	 */
	public void setID(String business_id) {
		this.business_id = business_id;
	}

	/** Returns the name of the restaurant as a string.
	 * 
	 * @return the name of the restaurant.
	 */
	public String getName() {
		return name;
	}

	/** Sets the name of the restaurant as string.
	 * 	Requires that the input is not null.
	 * 
	 * @param name
	 * 			the name to be set to the restaurant.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Returns the categories of the restaurant as an
	 * 	array list of strings.
	 * 
	 * @return the catergories of the restaurant.
	 */
	public ArrayList<String> getCategories() {
		return categories;
	}

	/** Sets the categories of the restaurant as an array 
	 * 	list of strings. Requires that categories is not null.
	 * 
	 * @param categories
	 * 				the catergories to be set to the restaurant.
	 */
	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

	/** Returns the state of the restaurant as string.
	 * 
	 * @return the state of the restaurant.
	 */
	public String getState() {
		return state;
	}

	/** Sets the state of the restaurant as a string.
	 * 	Requires that the input is not null.
	 * 
	 * @param state
	 * 			the state of the restaurant to be set.
	 */
	public void setState(String state) {
		this.state = state;
	}

	/** Returns the type of the restaurant as a string.
	 * 
	 * @return the type of the restaurant.
	 */
	public String getType() {
		return type;
	}

	/** Sets the type of the restaurant as a string.
	 * 	Requires that the input is not null.
	 * 
	 * @param type
	 * 			the type to be set to the restaurant.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/** Returns the stars for the restaurant as double.
	 * 
	 * @return the stars of the restaurant.
	 */
	public double getStars() {
		return stars;
	}

	/** Sets the stars of the restaurant as s double.
	 * 
	 * @param stars
	 * 			the stars to be set for the restaurant.
	 */
	public void setStars(double stars) {
		this.stars = stars;
	}

	/** Returns the city for the restaurant as a string.
	 * 
	 * @return the city of the restaurant.
	 */
	public String getCity() {
		return city;
	}

	/** Sets the city of the restaurant as a string.
	 * 	Requires that the input is not null.
	 * 
	 * @param city
	 * 			the city to be set for the restaurant.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/** Returns the full address for the restaurant as a string.
	 * 	
	 * 
	 * @return the full address of the restaurant.
	 */
	public String getFull_address() {
		return full_address;
	}

	/** Sets the full address for the restaurant as a string.
	 * 	Requires that the string is not null.
	 * 
	 * @param full_address
	 * 				the address to be set for the restaurant.
	 */
	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}

	/** Returns the number of reviews for the restaurant as a long.
	 * 
	 * @return the number of reviews for the restaurant.
	 */
	public long getReview_count() {
		return review_count;
	}
	
	/** Sets the review count for the restaurant as a long.
	 * 
	 * @param review_count
	 * 				the review count to be set for the restaurant.
	 */
	public void setReview_count(long review_count) {
		this.review_count = review_count;
	}

	/** Returns the photo url of the restaurant as a string.
	 * 
	 * @return the photo url of the restaurant.
	 */
	public String getPhoto_url() {
		return photo_url;
	}

	/** Sets the photo url of the restaurant as a string.
	 * 	Requires that the input is not null.
	 * 
	 * @param photo_url
	 * 				the photo url to be set for the restaurant.
	 */
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	/** Returns the schools for the restaurant as an 
	 * 	array list of strings. 
	 * 
	 * @return the schools for the restaurant.
	 */
	public ArrayList<String> getSchools() {
		return schools;
	}

	/** Sets the schools for the restaurant as an
	 * 	array list of strings. Requires that the input 
	 * 	is not null.
	 * 
	 * @param schools
	 * 			the schools to be set for the restaurant.
	 */
	public void setSchools(ArrayList<String> schools) {
		this.schools = schools;
	}
	@Override
	public double getLatitude() {
		return latitude;
	}

	/** Sets the latitude of the restaurant as a double.
	 * 
	 * @param latitude
	 * 				the latitude to be set for the restaurant.
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@Override
	public double getPrice() {
		return price;
	}

	/** Sets the price scale for the restaurant as a double
	 * 
	 * @param price
	 * 			the price scale to be set for the restaurant.
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/** Returns true is the restaurant is open and false if the
	 * 	restaurant is closed.
	 * 
	 * @return true if open and false if closed.
	 */
	public boolean isOpen() {
		return is_open;
	}

	/** Sets if the restaurant is open or closed using a boolean
	 * 	value.
	 * 
	 * @param is_open
	 * 			the boolean value to be set to is_open of the restaurant.
	 */
	public void setOpen(boolean is_open) {
		this.is_open = is_open;
	}
	
	/** Returns the centroid that is most closely similar to this restaurant based on
	 * 	the compare method for RestaurantProcessor.
	 * 
	 * @param db
	 * 			the restaurant database
	 * @param centroids
	 * 			a set of centroids
	 * @return the centroid that is closest to this restaurant.
	 */
	public Centroid findClosestCentroid(RestaurantDB db,Set<Centroid> centroids){
		double min = 0;
		for(Centroid c : centroids){
			if(min == 0){
				min = c.compare(db,this);
			}else{
				if(c.compare(db,this) < min){
					min = c.compare(db,this);
				}
			}
			
		}
		for(Centroid c : centroids){
			if(c.compare(db, this) == min){
				return c;
			}
		}
		return null;
		
	}
	
	/** Takes two points x and y with 4 dimensions and returns the 
	 * 	euclidean distance between them.
	 * 
	 * @param x1
	 * 			first dimension of x
	 * @param x2
	 * 			second dimension of x
	 * @param x3
	 * 			third dimension of x
	 * @param x4
	 * 			fourth dimension of x
	 * @param y1
	 * 			first dimension of y
	 * @param y2
	 * 			second dimension of x
	 * @param y3
	 * 			third dimension of x
	 * @param y4
	 * 			fourth dimension of x
	 * @return the distance between the two points
	 */
	private double getDistance(double x1,double x2,double x3,double x4,double y1,double y2,double y3,double y4){
		double x1y1 = (x1 - y1)*(x1 - y1);
		double x2y2 = (x2 - y2)*(x2 - y2);
		double x3y3 = (x3 - y3)*(x3 - y3);
		double x4y4 = (x4 - y4)*(x4 - y4);
		
		return Math.sqrt(x1y1 + x2y2 + x3y3 + x4y4);
	}
	
	@Override
	public double compare(RestaurantDB db,RestaurantProcessor d) {
		
		double maxLong = Algorithms.getMaxLong(db.getRestaurantMap());
		double minLong = Algorithms.getMinLong(db.getRestaurantMap());
		double maxLat = Algorithms.getMaxLat(db.getRestaurantMap());
		double minLat = Algorithms.getMinLat(db.getRestaurantMap());
		double maxPriceScale = Algorithms.getMaxPriceScale(db.getRestaurantMap());
		double minPriceScale = Algorithms.getMinPriceScale(db.getRestaurantMap());
		double maxMeanRating = Algorithms.getMaxMeanRating(db,db.getRestaurantMap());
		double minMeanRating = Algorithms.getMaxMeanRating(db,db.getRestaurantMap());
		
		return getDistance(Algorithms.scaleValue(maxLong, minLong,this.getLongitude()),Algorithms.scaleValue(maxLat, minLat,this.getLatitude()),Algorithms.scaleValue(maxPriceScale, minPriceScale,this.getPrice()),0
		          ,Algorithms.scaleValue(maxLong, minLong,d.getLongitude()),Algorithms.scaleValue(maxLat, minLat,this.getLatitude()),Algorithms.scaleValue(maxPriceScale, minPriceScale,this.getPrice()),0);
	}
	
	/**
	 * Returns the mean rating for a given database. Requires that the input not
	 * be null.
	 * 
	 * @param db
	 *            the restaurant database
	 * @return the mean rating of this restaurant.
	 */
	public double getMeanRating(RestaurantDB db) {
		HashMap<YelpRestaurant,HashSet<YelpReview>> reviews_map = db.getRestaurantReviews();
		Set<YelpReview> reviews = new HashSet<YelpReview>();
		for(YelpRestaurant r : reviews_map.keySet()){
			if(r.equals(this)){
				reviews = reviews_map.get(r);
			}
		}
		double mean_rating = 0;
		for(YelpReview review : reviews){
			mean_rating += review.getStars();
		}
		this.mean_rating = mean_rating;
		return (double) mean_rating;
	}
	
	@Override
	public int hashCode(){
		Double lon = new Double(this.longitude);
		Double lat = new Double(this.latitude);
		Double ps = new Double(this.price);
		Double mr = new Double(this.mean_rating);
		
		int hash = lon.hashCode() + lat.hashCode() + ps.hashCode() + mr.hashCode() + this.business_id.hashCode();
		return hash;
	}

	@Override
	public String toString() {
		return "{"
				+ "\"open\": " + Boolean.toString(isOpen()) + ", "
				+ "\"url\": " + getUrl() + ", "	
				+ "\"longitude\": " + Double.toString(getLongitude()) + ", "
				+ "\"neighbourhoods\": " + getNeighborhoods().toString() + ", "
				+ "\"business_id\": " + getID() + ", "
				+ "\"name\": " + getName() + ", "	
				+ "\"categories\": " + getCategories().toString() + ", "
				+ "\"state\": " + getState() + ", "
				+ "\"type\": " + getType() + ", "
				+ "\"stars\": " + Double.toString(getStars()) + ", "	
				+ "\"city\": " + getCity() + ", "
				+ "\"full_address\": " + getFull_address() + ", "
				+ "\"review_count\": " + Long.toString(getReview_count()) + ", "
				+ "\"photo_url\": " + getPhoto_url() + ", "
				+ "\"schools\": " + getSchools().toString() + ", "
				+ "\"latitude\": " + getLatitude() + ", "
				+ "\"price\": " + Double.toString(getPrice())
				+ "}";
	}
	
}
