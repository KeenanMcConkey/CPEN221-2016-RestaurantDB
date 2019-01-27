
package ca.ece.ubc.cpen221.mp5.staff;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.statlearning.Algorithms;

/* Centroid is an implementation of RestaurantProcessor to be used in the k-means
 * algorithm and contains certain fields that will be compared to a YelpRestaurant.
 */
public class Centroid implements RestaurantProcessor{

	// Representation Invariant:
	// 	1. closest_restaurants != null
	
	// Abstraction Function:
	//  [Internal Set of Restaurants] -> [A Centroid]
	
	private double longitude;
	private double latitude;
	private double price_scale;
	private double mean_rating;
	private Set<YelpRestaurant> closest_restaurants;
	
	public Centroid(){
		closest_restaurants = new HashSet<YelpRestaurant>();
	}
	
	/**
	 * Constructor for a Centroid. Sets the ID to a given ID, and sets the
	 * initial position and initial mean rating/price scale.
	 * 
	 * @param ID
	 *            for this centroid
	 * @param lon
	 *            longitudinal position of the centroid
	 * @param lat
	 *            latitudinal position of the centroid
	 * @param mr
	 *            initial mean rating of the centroid
	 * @param ps
	 *            initial price scale of the centroid
	 */
	public Centroid(double lon,double lat,double mr,double ps) {
		//this.restaurantDatabase = db;
		closest_restaurants = new HashSet<YelpRestaurant>();
		this.longitude = lon;
		this.latitude = lat;
		this.price_scale = ps;
		this.mean_rating = mr;
	}

	/**
	 * Finds the new values of the centroid based on its closest restaurants.
	 * 
	 * @param db
	 *            the restaurant database
	 */
	public void findNewCentroidForCurrentRestaurants(RestaurantDB db){
		double new_mean_rating = 0;
		long new_price_scale = 0;
		double new_longitude = 0;
		double new_latitude = 0;
		for(YelpRestaurant r : closest_restaurants){
			new_mean_rating += r.getMeanRating(db);
			new_price_scale += r.getPrice();
			new_longitude += r.getLongitude();
			new_latitude += r.getLatitude();
		}
		if(closest_restaurants.size() != 0){
		new_mean_rating = (new_mean_rating)/(closest_restaurants.size());
		new_price_scale = (new_price_scale)/(closest_restaurants.size());
		new_longitude = (new_longitude)/(closest_restaurants.size());
		new_latitude = (new_latitude)/(closest_restaurants.size());
		}
		
		this.mean_rating = new_mean_rating;
		this.price_scale = new_price_scale;
		this.longitude = new_longitude;
		this.latitude = new_latitude;
	}
	/** Adds a restaurant to this centroid. The restaurant must not
	 * 	be null.
	 * 
	 * @param r 
	 * 			the restaurant being added to the centroid
	 * @return true if the restaurant was added and false if it was not added.
	 */
	public boolean addRestaurant(YelpRestaurant r){
		if(this.closest_restaurants.add(r)){
			return true;
		}else return false;
	}
	/** Clears the set of restaurants associated with the centroid.
	 * 
	 */
	public void clearRestaurants(){
		this.closest_restaurants.clear();
	}
	
	/** Returns the set of restaurants that is associated with this centroid.
	 * 
	 * @return the set of closest restaurants
	 */
	public Set<YelpRestaurant> getRestaurants(){
		Set<YelpRestaurant> copy = new HashSet<YelpRestaurant>();
		for(YelpRestaurant r : this.closest_restaurants){
			copy.add(r);
		}
		return copy;
	}
	
	/** 
	 * Change the value of this Centroid's latitude.
	 * 
	 * @param newLong
	 * 			the new value that longitude will be set to
	 */
	public void modifyLongitude(double newLong){
		this.longitude = newLong;
	}
	
	/** 
	 * Change the value of this Centroid's longitude.
	 * 
	 * @param newLat
	 * 			the new value that latitude will be set to
	 */
	public void modifyLatitude(double newLat){
		this.latitude = newLat;
	}
	
	/** Change the value of this Centroid's mean_rating
	 * 
	 * @param newRating
	 * 			the new value that mean_rating will be set to
	 */
	public void modifyMeanRating(double newRating){
		this.mean_rating = newRating;
	}
	
	/** Changes the value of this Centroid's price_scale
	 * 
	 * @param newPriceScale
	 * 			the new value that price_scale will be set to
	 */
	public void modifyPriceScale(long newPriceScale){
		this.price_scale = newPriceScale;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public double getPrice() {
		return price_scale;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof RestaurantProcessor)) return false;
		else {
			RestaurantProcessor rdb = (RestaurantProcessor) obj;
			if (this.hashCode() == rdb.hashCode()) return true;
			else return false;
		}
	}
	
	@Override
	public int hashCode(){
		
		Double lon = new Double(this.longitude);
		Double lat = new Double(this.latitude);
		Double ps = new Double(this.price_scale);
		Double mr = new Double(this.mean_rating);
		
		int hash = lon.hashCode() + lat.hashCode() + ps.hashCode() + mr.hashCode();
		
		return hash;
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
		
		double minLong = Algorithms.getMaxLong(db.getRestaurantMap());
		double maxLong = Algorithms.getMinLong(db.getRestaurantMap());
		double maxLat = Algorithms.getMaxLat(db.getRestaurantMap());
		double minLat = Algorithms.getMinLat(db.getRestaurantMap());
		double maxPriceScale = Algorithms.getMaxPriceScale(db.getRestaurantMap());
		double minPriceScale = Algorithms.getMinPriceScale(db.getRestaurantMap());
		double maxMeanRating = Algorithms.getMaxMeanRating(db,db.getRestaurantMap());
		double minMeanRating = Algorithms.getMaxMeanRating(db,db.getRestaurantMap());
		
		return getDistance(Algorithms.scaleValue(maxLong, minLong,this.getLongitude()),Algorithms.scaleValue(maxLat, minLat,this.getLatitude()),Algorithms.scaleValue(maxPriceScale, minPriceScale,this.getPrice()),0
				          ,Algorithms.scaleValue(maxLong, minLong,d.getLongitude()),Algorithms.scaleValue(maxLat, minLat,this.getLatitude()),Algorithms.scaleValue(maxPriceScale, minPriceScale,this.getPrice()),0);
	}

	@Override
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
	
}
